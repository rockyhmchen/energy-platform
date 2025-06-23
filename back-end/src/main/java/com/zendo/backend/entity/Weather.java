package com.zendo.backend.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

/**
 * Represents a weather record with various meteorological data.
 * <p>
 * This class extends the {@link Auditable} class, inheriting auditing functionality for tracking creation and modification history.
 *
 * @see Auditable
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "weather")
public class Weather extends Auditable {

    @Id
    @UuidGenerator
    private UUID id;

    /**
     * The timestamp of the weather record, representing the date and time of the measurement.
     */
    private OffsetDateTime timestamp;
    private BigDecimal temperatureCelsius;
    private BigDecimal cloudCoverDecimal;
    private BigDecimal windSpeedInSecond;
    private BigDecimal uvIndex;
    private BigDecimal atmosphericPressureHpa;
    private BigDecimal averageVisibility;
    private OffsetDateTime sunriseTime;
    private OffsetDateTime sunsetTime;
    private BigDecimal solarIrradianceWm2;
}
