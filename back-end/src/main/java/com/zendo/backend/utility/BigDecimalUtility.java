package com.zendo.backend.utility;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides utility methods for performing mathematical operations on a list of elements
 * using a mapping function to extract BigDecimal values.
 */
@Slf4j
public final class BigDecimalUtility {

    /**
     * Calculates the sum of BigDecimal values extracted from a list of elements using a mapping function.
     *
     * @param <E>    the type of elements in the list
     * @param list   the list of elements to process
     * @param mapper a function that maps each element to a BigDecimal value
     * @return the sum of the BigDecimal values
     */
    public static <E> BigDecimal sum(final List<E> list, final Function<? super E, BigDecimal> mapper) {
        return list.stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculates the average of BigDecimal values extracted from a list of elements using a mapping function.
     *
     * @param <E>    the type of elements in the list
     * @param list   the list of elements to process
     * @param mapper a function that maps each element to a BigDecimal value
     * @return the average of the BigDecimal values
     * @throws ArithmeticException if the list is empty
     */
    public static <E> BigDecimal average(final List<E> list, final Function<? super E, BigDecimal> mapper) {
        var sum = sum(list, mapper);
        return sum.divide(BigDecimal.valueOf(list.size()), MathContext.DECIMAL128);
    }

    /**
     * Converts a Number to a BigDecimal.
     *
     * @param number the Number to be converted
     * @return the BigDecimal representation of the Number, or null if the input is null
     */
    public static BigDecimal numberToBigDecimal(final Number number) {
        if (Objects.isNull(number)) {
            logger.warn("The number to be converted to BigDecimal is null. Return null");
            return null;
        }

        return doubleToDecimal(number.doubleValue());
    }

    /**
     * Converts a double to a BigDecimal.
     *
     * @param value the value to be converted
     * @return the BigDecimal number
     */
    public static BigDecimal doubleToDecimal(final double value) {
        if (Double.isNaN(value)) {
            return BigDecimal.ZERO;
        }
        
        return BigDecimal.valueOf(value);
    }
}
