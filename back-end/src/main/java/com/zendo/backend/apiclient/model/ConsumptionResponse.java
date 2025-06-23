package com.zendo.backend.apiclient.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Consumption data response payload
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumptionResponse {

    /**
     * The timestamp of the consumption record
     */
    private OffsetDateTime timestamp;

    /**
     * The total consumption in kWh.
     */
    private Number totalConsumptionKwh;
}
