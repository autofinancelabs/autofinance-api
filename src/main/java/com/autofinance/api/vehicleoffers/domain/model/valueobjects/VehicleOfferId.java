package com.autofinance.api.vehicleoffers.domain.model.valueobjects;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

/** Identity of a {@code VehicleOffer} aggregate. Generated as a time-ordered UUIDv7. */
@Embeddable
public record VehicleOfferId(@Column(name = "id") UUID value) {

    private static final NoArgGenerator UUID_V7 = Generators.timeBasedEpochGenerator();

    public VehicleOfferId {
        if (value == null) {
            throw new IllegalArgumentException("VehicleOfferId value cannot be null");
        }
    }

    public VehicleOfferId() {
        this(UUID_V7.generate());
    }

    public static VehicleOfferId generate() {
        return new VehicleOfferId(UUID_V7.generate());
    }
}
