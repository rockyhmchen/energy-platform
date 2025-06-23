package com.zendo.backend.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

/**
 * Represents a production entity, encapsulating information about a specific production record.
 * This class extends the Auditable class, inheriting auditing functionality for tracking creation and modification details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "productions")
@ToString(exclude = {"details"})
public class Production extends Auditable {

    @Id
    @UuidGenerator
    private UUID id;

    /**
     * The timestamp of the production record
     */
    private OffsetDateTime timestamp;

    /**
     * The total production in kWh.
     */
    private BigDecimal totalProductionKwh;

    @OneToMany
    private List<ProductionDetail> details;
}
