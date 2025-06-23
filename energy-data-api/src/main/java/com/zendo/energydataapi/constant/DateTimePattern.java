package com.zendo.energydataapi.constant;

/**
 * Provides a collection of date and time patterns for formatting and parsing date and time values.
 * <p>
 * These patterns can be used with date and time formatting APIs, such as {@link java.time.format.DateTimeFormatter}.
 */
public final class DateTimePattern {

    /**
     * The ISO 8601 date and time pattern with timezone, in the format "yyyy-MM-dd'T'HH:mm:ss.SSSZ".
     * <p>
     * This pattern represents a date and time with millisecond precision and timezone offset.
     */
    public static final String ISO_8601_DATETIME_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
}
