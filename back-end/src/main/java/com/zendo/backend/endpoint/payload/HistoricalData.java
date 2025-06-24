package com.zendo.backend.endpoint.payload;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zendo.backend.constant.DateTimePattern;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimePattern.ISO_8601_DATETIME_WITH_TIMEZONE)
    private OffsetDateTime timestamp;
    private BigDecimal productionKwh;
    private BigDecimal consumptionKwh;
    private BigDecimal netBalanceKwh;
    private BigDecimal solarProductionKwh;
    private BigDecimal solarIrradianceWm2;
    private BigDecimal temperatureCelsius;
    private BigDecimal cloudCoverDecimal;
    private BigDecimal windSpeedInSecond;
    private BigDecimal solarIrradianceVsSolarProduction;
    private BigDecimal temperatureVsEnergyConsumption;
}
