package com.autofinance.api.vehicleoffers.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/** The financed good: make, model and year. Immutable; no identity of its own. */
@Embeddable
public record Vehicle(
        @Column(name = "vehicle_make") String make,
        @Column(name = "vehicle_model") String model,
        @Column(name = "vehicle_year") int year
) {
    public Vehicle {
        if (make == null || make.isBlank()) {
            throw new IllegalArgumentException("Vehicle make cannot be null or blank");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Vehicle model cannot be null or blank");
        }
        if (year <= 0) {
            throw new IllegalArgumentException("Vehicle year must be positive but was " + year);
        }
    }
}
