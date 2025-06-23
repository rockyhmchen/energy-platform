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
 * Represents a consumption record with a timestamp and total consumption in kWh.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Consumption {

    /**
     * The timestamp of the consumption record in ISO 8601 format with timezone.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimePattern.ISO_8601_DATETIME_WITH_TIMEZONE)
    private OffsetDateTime timestamp;

    /**
     * The total consumption in kWh.
     */
    private Number totalConsumptionKwh;
}
