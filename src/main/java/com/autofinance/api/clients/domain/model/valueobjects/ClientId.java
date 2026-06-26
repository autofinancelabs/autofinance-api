package com.autofinance.api.clients.domain.model.valueobjects;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

/** Identity of a {@code Client} aggregate. Generated as a time-ordered UUIDv7. */
@Embeddable
public record ClientId(@Column(name = "id") UUID value) {

    private static final NoArgGenerator UUID_V7 = Generators.timeBasedEpochGenerator();

    public ClientId {
        if (value == null) {
            throw new IllegalArgumentException("ClientId value cannot be null");
        }
    }

    public ClientId() {
        this(UUID_V7.generate());
    }

    public static ClientId generate() {
        return new ClientId(UUID_V7.generate());
    }
}
