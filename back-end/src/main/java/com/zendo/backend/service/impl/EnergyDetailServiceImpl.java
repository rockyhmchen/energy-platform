package com.zendo.backend.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.zendo.backend.entity.Consumption;
import com.zendo.backend.entity.EnergyDetail;
import com.zendo.backend.entity.EnergySourceType;
import com.zendo.backend.entity.Production;
import com.zendo.backend.entity.ProductionDetail;
import com.zendo.backend.entity.Weather;
import com.zendo.backend.repository.ConsumptionRepository;
import com.zendo.backend.repository.EnergyDetailRepository;
import com.zendo.backend.repository.ProductionRepository;
import com.zendo.backend.repository.WeatherRepository;
import com.zendo.backend.service.CorrelationCalculator;
import com.zendo.backend.service.EnergyDetailService;
import com.zendo.backend.utility.DateTimeHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.zendo.backend.utility.BigDecimalUtility.average;
import static com.zendo.backend.utility.BigDecimalUtility.sum;

/**
 * The EnergyDetailServiceImpl class provides an implementation of the EnergyDetailService interface.
 * It encapsulates the business logic for managing energy detail records, including retrieving and updating data.
 * <p>
 * This implementation relies on various repositories for data access and utilizes helper classes for date/time calculations and correlation analysis.
 *
 * @see EnergyDetailService
 * @see EnergyDetailRepository
 * @see WeatherRepository
 * @see ProductionRepository
 * @see ConsumptionRepository
 * @see DateTimeHelper
 * @see CorrelationCalculator
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EnergyDetailServiceImpl implements EnergyDetailService {

    private final EnergyDetailRepository energyDetailRepository;
    private final WeatherRepository weatherRepository;
    private final ProductionRepository productionRepository;
    private final ConsumptionRepository consumptionRepository;
    private final DateTimeHelper dateTimeHelper;
    private final CorrelationCalculator correlationCalculator;

    /**
     * Retrieves all energy detail records from the database.
     *
     * @return a list of all energy detail records
     */
    @Override
    public List<EnergyDetail> getAll() {
        return energyDetailRepository.findAll();
    }

    /**
     * Retrieves energy detail records for the last 24 hours based on the provided timestamp.
     *
     * @param now the current timestamp
     * @return a list of energy detail records for the last 24 hours
     */
    @Override
    public List<EnergyDetail> getLast24HoursData(final OffsetDateTime now) {
        return energyDetailRepository.findByTimestampBetween(now.minusHours(24), now);
    }

    /**
     * Updates energy detail records for the last 24 hours based on the provided collecting timestamp.
     * If a record exists for a particular timestamp, it is patched with new data; otherwise, a new record is created.
     *
     * @param collectingAt the timestamp to use for updating energy detail records
     * @return a list of updated energy detail records for the last 24 hours
     * @throws IllegalArgumentException if the collectingAt parameter is null
     */
    @Override
    @Transactional
    public List<EnergyDetail> updateLast24HoursData(final OffsetDateTime collectingAt) {
        if (Objects.isNull(collectingAt)) {
            throw new IllegalArgumentException("Input param 'collectingAt' must not be null");
        }

        var last24HourEnergyDetails = new LinkedList<EnergyDetail>();
        var last24HourTimes = dateTimeHelper.listLast24HoursTimesPerHour(collectingAt);
        for (var time : last24HourTimes) {
            var found = energyDetailRepository.findByTimestamp(time);
            if (found.isPresent()) {
                var patched = patch(found.get());
                last24HourEnergyDetails.addLast(patched);
            }
            else {
                var created = create(time);
                last24HourEnergyDetails.addLast(created);
            }
        }

        return last24HourEnergyDetails;
    }

    /**
     * Patches an existing energy detail record with new data.
     *
     * @param toBePatched the energy detail record to be patched
     * @return the patched energy detail record
     */
    private EnergyDetail patch(final EnergyDetail toBePatched) {
        var consumptions = consumptionRepository.findByTimestampBetween(toBePatched.getTimestamp(),
                                                                        toBePatched.getTimestamp().minusHours(1));
        var productions = productionRepository.findByTimestampBetween(toBePatched.getTimestamp(),
                                                                      toBePatched.getTimestamp().minusHours(1));
        var weathers = weatherRepository.findByTimestampBetween(toBePatched.getTimestamp(),
                                                                toBePatched.getTimestamp().minusHours(1));

        toBePatched.setConsumptionKwh(sum(consumptions, Consumption::getTotalConsumptionKwh));
        toBePatched.setProductionKwh(sum(productions, Production::getTotalProductionKwh));

        var solarProductions = getSolarProductions(productions);
        toBePatched.setSolarProductionKwh(sum(solarProductions, ProductionDetail::getProductionKwh));

        toBePatched.setTemperatureCelsius(average(weathers, Weather::getTemperatureCelsius));
        toBePatched.setCloudCoverDecimal(average(weathers, Weather::getCloudCoverDecimal));
        toBePatched.setWindSpeedInSecond(average(weathers, Weather::getWindSpeedInSecond));
        toBePatched.setUvIndex(average(weathers, Weather::getUvIndex));
        toBePatched.setSolarIrradianceWm2(average(weathers, Weather::getSolarIrradianceWm2));
        toBePatched.setNetBalanceKwh(toBePatched.getProductionKwh().subtract(toBePatched.getConsumptionKwh()));

        var temperatureVsEnergyConsumption = correlationCalculator.temperatureVsEnergyConsumption(toBePatched.getTimestamp());
        toBePatched.setTemperatureVsEnergyConsumption(temperatureVsEnergyConsumption);

        var solarIrradianceVsSolarProduction = correlationCalculator.solarIrradianceVsSolarProduction(toBePatched.getTimestamp());
        toBePatched.setSolarIrradianceVsSolarProduction(solarIrradianceVsSolarProduction);

        return energyDetailRepository.save(toBePatched);
    }

    /**
     * Creates a new energy detail record for the specified timestamp.
     *
     * @param timestamp the timestamp for which to create the energy detail record
     * @return the created energy detail record
     */
    private EnergyDetail create(final OffsetDateTime timestamp) {
        var consumptions = consumptionRepository.findByTimestampBetween(timestamp, timestamp.minusHours(1));
        var productions = productionRepository.findByTimestampBetween(timestamp, timestamp.minusHours(1));
        var weathers = weatherRepository.findByTimestampBetween(timestamp, timestamp.minusHours(1));

        var energyDetail = new EnergyDetail();
        energyDetail.setTimestamp(timestamp);
        energyDetail.setConsumptionKwh(sum(consumptions, Consumption::getTotalConsumptionKwh));
        energyDetail.setProductionKwh(sum(productions, Production::getTotalProductionKwh));

        var solarProductions = getSolarProductions(productions);
        energyDetail.setSolarProductionKwh(sum(solarProductions, ProductionDetail::getProductionKwh));

        energyDetail.setTemperatureCelsius(average(weathers, Weather::getTemperatureCelsius));
        energyDetail.setCloudCoverDecimal(average(weathers, Weather::getCloudCoverDecimal));
        energyDetail.setWindSpeedInSecond(average(weathers, Weather::getWindSpeedInSecond));
        energyDetail.setUvIndex(average(weathers, Weather::getUvIndex));
        energyDetail.setSolarIrradianceWm2(average(weathers, Weather::getSolarIrradianceWm2));
        energyDetail.setNetBalanceKwh(energyDetail.getProductionKwh().subtract(energyDetail.getConsumptionKwh()));

        var temperatureVsEnergyConsumption = correlationCalculator.temperatureVsEnergyConsumption(timestamp);
        energyDetail.setTemperatureVsEnergyConsumption(temperatureVsEnergyConsumption);

        var solarIrradianceVsSolarProduction = correlationCalculator.solarIrradianceVsSolarProduction(timestamp);
        energyDetail.setSolarIrradianceVsSolarProduction(solarIrradianceVsSolarProduction);

        return energyDetailRepository.save(energyDetail);
    }

    /**
     * Retrieves solar production details from a list of production records.
     *
     * @param productions the list of production records
     * @return a list of solar production details
     */
    private List<ProductionDetail> getSolarProductions(final List<Production> productions) {
        var solarProductions = new ArrayList<ProductionDetail>();
        for (var production : productions) {
            solarProductions.addAll(production.getDetails()
                                            .stream()
                                            .filter(detail -> detail.getSourceType().equals(EnergySourceType.SOLAR))
                                            .toList());
        }

        return solarProductions;
    }
}
