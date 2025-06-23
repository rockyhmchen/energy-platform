package com.zendo.backend.entity;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Enumerates the different types of energy sources.
 */
@Getter
@RequiredArgsConstructor
@ToString
public enum EnergySourceType {

    /**
     * Solar energy source type.
     */
    SOLAR("solar"),

    /**
     * Wind energy source type.
     */
    WIND("wind");

    private final String textValue;

    /**
     * Retrieves the EnergySourceType enum value corresponding to the given text value.
     *
     * @param textValue the text value to match against the enum values
     * @return the EnergySourceType enum value matching the given text value
     * @throws IllegalArgumentException if no enum value matches the given text value
     */
    public static EnergySourceType fromTextValue(final String textValue) {
        return Arrays.stream(EnergySourceType.values())
                .filter(item -> item.getTextValue().equalsIgnoreCase(textValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown type %s", textValue)));
    }
}
