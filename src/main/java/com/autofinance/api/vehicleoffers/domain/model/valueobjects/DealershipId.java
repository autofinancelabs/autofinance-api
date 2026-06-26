package com.autofinance.api.vehicleoffers.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

/** Tenant identifier (dealership). Referenced by id; the {@code Dealership} lives in the IAM context. */
@Embeddable
public record DealershipId(@Column(name = "dealership_id") UUID value) {
    public DealershipId {
        if (value == null) {
            throw new IllegalArgumentException("DealershipId value cannot be null");
        }
    }

    public DealershipId() {
        this(new UUID(0L, 0L));
    }
}
