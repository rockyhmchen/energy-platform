package com.zendo.backend.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

/**
 * Represents a detailed production record, encapsulating information about a specific energy source's contribution to a production.
 * This class extends the Auditable class, inheriting auditing functionality for tracking creation and modification details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "production_details")
@ToString(exclude = {"parent"})
public class ProductionDetail extends Auditable {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "production_id")
    private Production parent;

    /**
     * The type of the energy source
     */
    private EnergySourceType sourceType;

    /**
     * The production in kWh.
     */
    private BigDecimal productionKwh;

}
