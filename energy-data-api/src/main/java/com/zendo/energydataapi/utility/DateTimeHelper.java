package com.zendo.energydataapi.utility;

import java.time.LocalDateTime;

/**
 * The DateTimeHelper interface provides a method for retrieving the current date and time.
 */
public interface DateTimeHelper {

    /**
     * Returns the current date and time.
     *
     * @return the current date and time as a {@link LocalDateTime} object
     */
    LocalDateTime now();
}
