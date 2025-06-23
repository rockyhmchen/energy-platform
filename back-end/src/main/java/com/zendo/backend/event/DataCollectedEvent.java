package com.zendo.backend.event;

import java.time.Clock;
import java.time.OffsetDateTime;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Represents an event that is triggered when data is collected.
 * This event contains information about the type of data collected and the timestamp when it was collected.
 */
@Getter
public class DataCollectedEvent extends ApplicationEvent {

    private final IncomingDataType incomingDataType;
    private final OffsetDateTime collectingAt;

    public DataCollectedEvent(final Object source, final IncomingDataType incomingDataType, final OffsetDateTime collectingAt) {
        super(source, Clock.systemUTC());
        this.incomingDataType = incomingDataType;
        this.collectingAt = collectingAt;
    }
}
