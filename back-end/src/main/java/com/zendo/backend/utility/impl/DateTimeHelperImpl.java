package com.zendo.backend.utility.impl;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.zendo.backend.utility.DateTimeHelper;
import org.springframework.stereotype.Component;

@Component
public class DateTimeHelperImpl implements DateTimeHelper {

    @Override
    public OffsetDateTime now() {
        return OffsetDateTime.now(Clock.systemUTC());
    }

    @Override
    public List<OffsetDateTime> listLast24HoursTimesPerHour(final OffsetDateTime now) {
        if (Objects.isNull(now)) {
            throw new IllegalArgumentException("Input param 'now' must not be null");
        }

        var last24HoursTimes = new LinkedList<OffsetDateTime>();
        for (int hour = 23; hour >= 1; hour--) {
            var toBeAdded = now.minusHours(hour)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
            last24HoursTimes.addLast(toBeAdded);
        }
        last24HoursTimes.addLast(now.withMinute(0)
                                         .withSecond(0)
                                         .withNano(0));

        return last24HoursTimes;
    }
}
