package com.zendo.backend.utility;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * The TypeConvertorUtility class provides utility methods for converting between different data types.
 *
 */
@Slf4j
public final class TypeConvertorUtility {

    /**
     * Converts an epoch time (in seconds) to an OffsetDateTime.
     *
     * @param epoch the epoch time to be converted
     * @return the OffsetDateTime representation of the epoch time, or null if the input is null
     */
    public static OffsetDateTime epochToOffsetDateTime(final Number epoch) {
        if (Objects.isNull(epoch)) {
            logger.warn("The epoch to be converted to OffsetDateTime is null. Return null");
            return null;
        }

        return OffsetDateTime.of(LocalDateTime.ofEpochSecond(epoch.longValue(), 0, ZoneOffset.UTC), ZoneOffset.UTC);
    }
}
