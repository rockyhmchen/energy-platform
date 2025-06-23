package com.zendo.energydataapi.service;

import com.zendo.energydataapi.model.Production;

/**
 * The ProductionService interface provides a method for generating a Production object.
 */
public interface ProductionService {

    /**
     * Generates a new Production object.
     *
     * @return a newly generated Production object
     */
    Production generate();
}
