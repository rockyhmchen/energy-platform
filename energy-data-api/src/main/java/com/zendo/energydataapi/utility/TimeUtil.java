package com.zendo.energydataapi.utility;

import java.time.LocalDateTime;

/**
 * Utility class for time-related operations.
 */
public final class TimeUtil {

    /**
     * Rounds down the given LocalDateTime to the nearest x-minute interval.
     *
     * @param time the LocalDateTime to be rounded down
     * @param xMinute the interval of the minute
     * @return a new LocalDateTime rounded down to the nearest x-minute interval
     */
    public static LocalDateTime getEveryXMinuteTime(final LocalDateTime time, final int xMinute) {
        if (xMinute > 59) {
            throw new IllegalArgumentException("xMinute should be less than 60");
        }

        var minute = time.getMinute();
        var difference = minute % xMinute;
        var newMinute = minute - difference;

        return time.withMinute(newMinute)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * Rounds down the given LocalDateTime to the nearest x-hour interval.
     *
     * @param time the LocalDateTime to be rounded down
     * @param xHour the interval of the hour
     * @return a new LocalDateTime rounded down to the nearest x-hour interval
     */
    public static LocalDateTime getEveryXHourTime(final LocalDateTime time, final int xHour) {
        if (xHour > 23) {
            throw new IllegalArgumentException("xHour should be less than 24");
        }

        var hour = time.getHour();
        var difference = hour % xHour;
        var newHour = hour - difference;

        return time.withHour(newHour)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }
}
