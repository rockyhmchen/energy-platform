package com.zendo.energydataapi.service;

import com.zendo.energydataapi.model.Consumption;

/**
 * Provides a service for generating consumption records.
 */
public interface ConsumptionService {

    /**
     * Generates a new consumption record with a random or default timestamp and total consumption in kWh.
     *
     * @return a new Consumption object representing the generated consumption record.
     */
    Consumption generate();
}
