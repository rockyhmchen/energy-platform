package com.zendo.backend.service.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import com.zendo.backend.entity.EnergyDetail;
import com.zendo.backend.repository.EnergyDetailRepository;
import com.zendo.backend.service.CorrelationCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link CorrelationCalculator} interface, providing methods to calculate
 * correlations between various energy-related metrics.
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CorrelationCalculatorImpl implements CorrelationCalculator {

    private final EnergyDetailRepository energyDetailRepository;

    /**
     * Calculates the correlation between solar irradiance and solar production for the last 24 hours.
     *
     * @param now the current timestamp
     * @return the correlation between solar irradiance and solar production as a {@link BigDecimal}
     * @throws IllegalArgumentException if the input timestamp is null
     */
    @Override
    public BigDecimal solarIrradianceVsSolarProduction(final OffsetDateTime now) {
        if (Objects.isNull(now)) {
            throw new IllegalArgumentException("Input param 'now' must not be null");
        }

        var energyDetails = energyDetailRepository.findByTimestampBetween(now.minusHours(24), now);
        return solarIrradianceVsSolarProduction(energyDetails);
    }

    /**
     * Calculates the correlation between solar irradiance and solar production based on the provided energy detail records.
     *
     * @param energyDetails the list of energy detail records
     * @return the correlation between solar irradiance and solar production as a {@link BigDecimal}
     * @throws IllegalArgumentException if the input list is null
     */
    public BigDecimal solarIrradianceVsSolarProduction(final List<EnergyDetail> energyDetails) {
        if (Objects.isNull(energyDetails)) {
            throw new IllegalArgumentException("Input param 'energyDetails' must not be null");
        }

        var solarIrradiances = energyDetails.stream()
                .mapToDouble(energyDetail -> energyDetail.getSolarIrradianceWm2().doubleValue())
                .toArray();
        var solarProductions = energyDetails.stream()
                .mapToDouble(energyDetail -> energyDetail.getSolarProductionKwh().doubleValue())
                .toArray();

        var pearsonsCorrelation = new PearsonsCorrelation();
        var correlation = pearsonsCorrelation.correlation(solarIrradiances, solarProductions);

        return BigDecimal.valueOf(correlation);
    }

    /**
     * Calculates the correlation between temperature and energy consumption for the last 24 hours.
     *
     * @param now the current timestamp
     * @return the correlation between temperature and energy consumption as a {@link BigDecimal}
     * @throws IllegalArgumentException if the input timestamp is null
     */
    @Override
    public BigDecimal temperatureVsEnergyConsumption(final OffsetDateTime now) {
        if (Objects.isNull(now)) {
            throw new IllegalArgumentException("Input param 'current' must not be null");
        }

        var energyDetails = energyDetailRepository.findByTimestampBetween(now.minusHours(24), now);
        return temperatureVsEnergyConsumption(energyDetails);
    }

    /**
     * Calculates the correlation between temperature and energy consumption based on the provided energy detail records.
     *
     * @param energyDetails the list of energy detail records
     * @return the correlation between temperature and energy consumption as a {@link BigDecimal}
     * @throws IllegalArgumentException if the input list is null
     */
    public BigDecimal temperatureVsEnergyConsumption(final List<EnergyDetail> energyDetails) {
        if (Objects.isNull(energyDetails)) {
            throw new IllegalArgumentException("Input param 'energyDetails' must not be null");
        }

        var temperature = energyDetails.stream()
                .mapToDouble(energyDetail -> energyDetail.getTemperatureCelsius().doubleValue())
                .toArray();
        var consumption = energyDetails.stream()
                .mapToDouble(energyDetail -> energyDetail.getConsumptionKwh().doubleValue())
                .toArray();

        var pearsonsCorrelation = new PearsonsCorrelation();
        var correlation = pearsonsCorrelation.correlation(temperature, consumption);

        return BigDecimal.valueOf(correlation);
    }
}
