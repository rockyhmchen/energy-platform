package com.zendo.backend.endpoint.payload;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Energy summary API response payload
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnergySummary {
    private BigDecimal totalProductionKwh;
    private BigDecimal totalConsumptionKwh;
    private BigDecimal netBalanceKwh;
    private Weather weather;
    private Correlation correlation;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private BigDecimal temperatureCelsius;
        private BigDecimal cloudCoverDecimal;
        private BigDecimal windSpeedInSecond;
        private BigDecimal solarIrradianceWm2;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Correlation {
        private BigDecimal solarIrradianceVsSolarProduction;
        private BigDecimal temperatureVsEnergyConsumption;
    }
}
