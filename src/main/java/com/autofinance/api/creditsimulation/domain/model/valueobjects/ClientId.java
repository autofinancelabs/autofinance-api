package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

/** By-id reference to a Client aggregate (other bounded context). */
@Embeddable
public record ClientId(@Column(name = "client_id") UUID value) {
    public ClientId {
        if (value == null) {
            throw new IllegalArgumentException("ClientId value cannot be null");
        }
    }

    public ClientId() {
        this(new UUID(0L, 0L));
    }
}
