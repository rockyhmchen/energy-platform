package com.zendo.backend.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Enumerates the types of incoming data.
 * <p>
 * This enum defines the different categories of data that can be received.
 */
@Getter
@RequiredArgsConstructor
@ToString
public enum IncomingDataType {

    CONSUMPTION, PRODUCTION, WEATHER
}
