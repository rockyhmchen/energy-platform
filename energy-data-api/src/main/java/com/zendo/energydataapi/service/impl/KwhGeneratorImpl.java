package com.zendo.energydataapi.service.impl;

import java.time.LocalDateTime;

import com.zendo.energydataapi.service.KwhGenerator;
import com.zendo.energydataapi.utility.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Implementation of the KwhGenerator interface, providing methods to generate kilowatt-hour (kWh) values
 * for solar production, wind production, and total consumption at a given timestamp.
 *
 * The generated values are cached to improve performance by reducing redundant computations.
 *
 * @see KwhGenerator
 */
@Component
@Slf4j
public class KwhGeneratorImpl implements KwhGenerator {

    /**
     * Generates a random solar production value in kWh at the given timestamp.
     * The result is cached to avoid redundant computations for the same timestamp.
     *
     * @param timestamp the timestamp for which to generate the solar production value
     * @return a random solar production value in kWh
     */
    @Cacheable("solarProduction")
    @Override
    public double solarProduction(final LocalDateTime timestamp) {
        logger.debug("Generate the solar production at {}", timestamp);

        return RandomUtil.generateDouble();
    }

    /**
     * Generates a random wind production value in kWh at the given timestamp.
     * The result is cached to avoid redundant computations for the same timestamp.
     *
     * @param timestamp the timestamp for which to generate the wind production value
     * @return a random wind production value in kWh
     */
    @Cacheable("windProduction")
    @Override
    public double windProduction(final LocalDateTime timestamp) {
        logger.debug("Generate the wind production at {}", timestamp);

        return RandomUtil.generateDouble();
    }

    /**
     * Generates a random total consumption value in kWh at the given timestamp.
     * The result is cached to avoid redundant computations for the same timestamp.
     *
     * @param timestamp the timestamp for which to generate the total consumption value
     * @return a random total consumption value in kWh
     */
    @Cacheable("totalConsumption")
    @Override
    public double totalConsumption(final LocalDateTime timestamp) {
        logger.debug("Generate the total consumption at {}", timestamp);

        return RandomUtil.generateDouble();
    }
}
