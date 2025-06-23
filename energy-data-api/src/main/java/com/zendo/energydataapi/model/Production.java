package com.zendo.energydataapi.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zendo.energydataapi.constant.DateTimePattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a production data point with a timestamp and details about the production.
 * <p>
 * This class is used to store and serialize production data, including the total production and
 * breakdown by source (solar and wind).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Production {

    /**
     * The timestamp of the production data point.
     * <p>
     * The timestamp is stored in ISO 8601 format with timezone information.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimePattern.ISO_8601_DATETIME_WITH_TIMEZONE)
    private OffsetDateTime timestamp;

    /**
     * The detailed production data, including breakdown by source (solar and wind).
     */
    private Detail detail;

    /**
     * The total production in kWh.
     */
    private Number totalProductionKwh;

    /**
     * Represents the detailed production data, including breakdown by source.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Detail {

        /**
         * The solar production in kWh.
         */
        private Number solarProductionKwh;

        /**
         * The wind production in kWh.
         */
        private Number windProductionKwh;

    }
}
