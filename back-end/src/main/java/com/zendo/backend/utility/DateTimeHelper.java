package com.zendo.backend.utility;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * The DateTimeHelper interface provides a method for retrieving the current date and time.
 */
public interface DateTimeHelper {

    /**
     * Returns the current date and time.
     *
     * @return the current date and time as a {@link java.time.OffsetDateTime} object
     */
    OffsetDateTime now();

    /**
     * Generates a list of OffsetDateTime objects representing the last 24 hours, with one entry per hour.
     *
     * @param now the current date and time
     * @return a list of OffsetDateTime objects, where each object represents a point in time in the last 24 hours
     */
    List<OffsetDateTime> listLast24HoursTimesPerHour(OffsetDateTime now);
}
