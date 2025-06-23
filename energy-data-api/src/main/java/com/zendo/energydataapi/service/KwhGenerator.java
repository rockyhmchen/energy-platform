package com.zendo.energydataapi.service;

import java.time.LocalDateTime;

/**
 * The KwhGenerator interface provides methods for generating kilowatt-hour (kWh) values for
 * different energy sources at a given timestamp.
 */
public interface KwhGenerator {

    /**
     * Calculates the solar energy production in kWh at the specified timestamp.
     *
     * @param timestamp the timestamp for which to calculate solar production
     * @return the solar energy production in kWh
     */
    double solarProduction(LocalDateTime timestamp);

    /**
     * Calculates the wind energy production in kWh at the specified timestamp.
     *
     * @param timestamp the timestamp for which to calculate wind production
     * @return the wind energy production in kWh
     */
    double windProduction(LocalDateTime timestamp);

    /**
     * Calculates the total energy consumption in kWh at the specified timestamp.
     *
     * @param timestamp the timestamp for which to calculate total consumption
     * @return the total energy consumption in kWh
     */
    double totalConsumption(LocalDateTime timestamp);
}
