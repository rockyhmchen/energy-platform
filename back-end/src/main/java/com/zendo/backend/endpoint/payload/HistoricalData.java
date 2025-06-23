package com.zendo.backend.endpoint.payload;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Historical data API response payload
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalData {
    private OffsetDateTime timestamp;
    private BigDecimal productionKwh;
    private BigDecimal consumptionKwh;
    private BigDecimal netBalanceKwh;
    private BigDecimal solarProduction;
    private BigDecimal solarIrradianceWm2;
    private BigDecimal temperatureCelsius;
}
