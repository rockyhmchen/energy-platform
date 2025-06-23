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
 * Represents a consumption record, which captures the total energy consumption at a specific point in time.
 * This class extends the {@link Auditable} class to inherit auditing functionality.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "consumptions")
public class Consumption extends Auditable {

    @Id
    @UuidGenerator
    private UUID id;

    /**
     * The timestamp of the consumption record
     */
    private OffsetDateTime timestamp;

    /**
     * The total consumption in kWh.
     */
    private BigDecimal totalConsumptionKwh;
}
