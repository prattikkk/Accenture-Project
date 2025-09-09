package com.example.API.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    DEBIT,
    CREDIT;

    @JsonCreator
    public static TransactionType fromValue(String value) {
        try {
            return TransactionType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                "Invalid transaction type: " + value + ". Allowed values: DEBIT, CREDIT."
            );
        }
    }

    @JsonValue
    public String toValue() {
        return this.name(); // Ensures enum is serialized as "DEBIT" or "CREDIT"
    }
}
