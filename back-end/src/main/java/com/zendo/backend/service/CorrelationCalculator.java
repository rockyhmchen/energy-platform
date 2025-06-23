package com.zendo.backend.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.zendo.backend.entity.EnergyDetail;

public interface CorrelationCalculator {

    BigDecimal solarIrradianceVsSolarProduction(final OffsetDateTime now);

    BigDecimal solarIrradianceVsSolarProduction(final List<EnergyDetail> energyDetails);

    BigDecimal temperatureVsEnergyConsumption(final OffsetDateTime now);

    BigDecimal temperatureVsEnergyConsumption(final List<EnergyDetail> energyDetails);
}
