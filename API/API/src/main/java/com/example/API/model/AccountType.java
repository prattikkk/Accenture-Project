package com.example.API.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing the types of bank accounts.
 */
public enum AccountType {
    SAVINGS,
    CURRENT;

    /**
     * Converts a string to an AccountType enum, case-insensitively.
     * Throws an IllegalArgumentException if the input doesn't match any valid type.
     *
     * @param value The string representation of the account type.
     * @return Corresponding AccountType enum.
     */
    @JsonCreator
    public static AccountType fromString(String value) {
        try {
            return AccountType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid account type: '" + value + "'. Allowed values: SAVINGS, CURRENT.");
        }
    }

    /**
     * Converts the enum to its string representation for JSON serialization.
     *
     * @return The name of the enum.
     */
    @JsonValue
    public String toValue() {
        return this.name();
    }
}
