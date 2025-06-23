package com.zendo.backend.utility;

import java.math.BigDecimal;

/**
 * The SolarIrradianceCalculator class provides a method for estimating solar irradiance.
 */
public final class SolarIrradianceCalculator {

    /**
     * Estimates the solar irradiance based on the given un-index value.
     *
     * @param unIndex the un-index value used for estimation
     * @return the estimated solar irradiance
     */
    public static BigDecimal estimate(final BigDecimal unIndex) {
        return unIndex.multiply(new BigDecimal(25));
    }
}
