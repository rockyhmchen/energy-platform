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
 * Represents detailed information about energy production and consumption at a specific point in time.
 * This class extends the Auditable class to inherit auditing functionality.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "energy_details")
public class EnergyDetail extends Auditable {

    /**
     * The unique identifier for this energy detail record.
     */
    @Id
    @UuidGenerator
    private UUID id;

    /**
     * The timestamp when the energy details were generated.
     */
    private OffsetDateTime timestamp;
    private BigDecimal productionKwh;
    private BigDecimal solarProductionKwh;
    private BigDecimal consumptionKwh;
    private BigDecimal netBalanceKwh;
    private BigDecimal temperatureCelsius;
    private BigDecimal cloudCoverDecimal;
    private BigDecimal windSpeedInSecond;
    private BigDecimal unIndex;
    private BigDecimal solarIrradianceWm2;
    private BigDecimal solarIrradianceVsSolarProduction;
    private BigDecimal temperatureVsEnergyConsumption;
}
