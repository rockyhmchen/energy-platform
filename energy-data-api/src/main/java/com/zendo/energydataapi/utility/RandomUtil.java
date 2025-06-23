package com.zendo.energydataapi.utility;

/**
 * Utility class for generating random numbers.
 */
public final class RandomUtil {

    /**
     * Generates a random double value between 1.0 and 10.0 (inclusive) with one decimal place.
     *
     * @return a random double value
     */
    public static double generateDouble() {
        return (int) ((Math.random() * 90) + 10) / 10.0;
    }
}
