package com.zendo.backend.endpoint;

import com.zendo.backend.endpoint.payload.EnergySummary;
import com.zendo.backend.entity.EnergyDetail;
import com.zendo.backend.service.CorrelationCalculator;
import com.zendo.backend.service.EnergyDetailService;
import com.zendo.backend.service.WeatherApiService;
import com.zendo.backend.utility.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zendo.backend.utility.BigDecimalUtility.sum;

/**
 * The EnergySummaryEndpoint class provides a REST endpoint for retrieving an energy summary.
 * The energy summary includes total production, total consumption, net balance, current weather, and correlation data.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/energy-summary")
public class EnergySummaryEndpoint {

    private final EnergyDetailService energyDetailService;
    private final WeatherApiService weatherApiService;
    private final CorrelationCalculator correlationCalculator;
    private final DateTimeHelper dateTimeHelper;

    /**
     * Retrieves an energy summary, including total production, total consumption, net balance, current weather, and correlation data.
     *
     * @return a {@link ResponseEntity} containing an {@link EnergySummary} object
     */
    @GetMapping
    public ResponseEntity<EnergySummary> get() {
        var allEnergyDetail = energyDetailService.getAll();
        var totalConsumption = sum(allEnergyDetail, EnergyDetail::getConsumptionKwh);
        var totalProduction = sum(allEnergyDetail, EnergyDetail::getProductionKwh);
        var netBalance = totalProduction.subtract(totalConsumption);

        var now = dateTimeHelper.now();
        var currentWeather = weatherApiService.getCurrentWeather(now);
        var weatherResponse = EnergySummary.Weather.builder()
                .temperatureCelsius(currentWeather.getTemperatureCelsius())
                .cloudCoverDecimal(currentWeather.getCloudCoverDecimal())
                .windSpeedInSecond(currentWeather.getWindSpeedInSecond())
                .solarIrradianceWm2(currentWeather.getSolarIrradianceWm2())
                .build();

        var solarIrradianceVsSolarProduction = correlationCalculator.solarIrradianceVsSolarProduction(allEnergyDetail);
        var temperatureVsEnergyConsumption = correlationCalculator.temperatureVsEnergyConsumption(allEnergyDetail);
        var correlationResponse = EnergySummary.Correlation.builder()
                .solarIrradianceVsSolarProduction(solarIrradianceVsSolarProduction)
                .temperatureVsEnergyConsumption(temperatureVsEnergyConsumption)
                .build();

        var summary = EnergySummary.builder()
                .totalConsumptionKwh(totalConsumption)
                .totalProductionKwh(totalProduction)
                .netBalanceKwh(netBalance)
                .weather(weatherResponse)
                .correlation(correlationResponse)
                .build();

        return ResponseEntity.ok(summary);
    }
}
