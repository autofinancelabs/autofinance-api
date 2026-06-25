package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

/** By-id reference to a VehicleOffer aggregate (other bounded context). */
@Embeddable
public record VehicleOfferId(@Column(name = "vehicle_offer_id") UUID value) {
    public VehicleOfferId {
        if (value == null) {
            throw new IllegalArgumentException("VehicleOfferId value cannot be null");
        }
    }

    public VehicleOfferId() {
        this(new UUID(0L, 0L));
    }
}
