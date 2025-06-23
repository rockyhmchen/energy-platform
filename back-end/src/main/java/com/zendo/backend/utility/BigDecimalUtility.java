package com.zendo.backend.utility;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.function.Function;

/**
 * Provides utility methods for performing mathematical operations on a list of elements
 * using a mapping function to extract BigDecimal values.
 */
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
}
