package com.autofinance.api.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/** Peru tax identifier of the dealership: exactly 11 numeric digits. Unique per registry. */
@Embeddable
public record Ruc(@Column(name = "ruc") String value) {
    public Ruc {
        if (value == null || !value.matches("\\d{11}")) {
            throw new IllegalArgumentException("RUC must be exactly 11 digits but was " + value);
        }
    }
}
