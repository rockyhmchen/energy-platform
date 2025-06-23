package com.zendo.backend.apiclient.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Production data response payload
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductionResponse {

    /**
     * The timestamp of the production data point.
     */
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
