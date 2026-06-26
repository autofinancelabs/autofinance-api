package com.autofinance.api.iam.domain.model.valueobjects;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

/** Identity of a {@code User} aggregate. Generated as a time-ordered UUIDv7. */
@Embeddable
public record UserId(@Column(name = "id") UUID value) {

    private static final NoArgGenerator UUID_V7 = Generators.timeBasedEpochGenerator();

    public UserId {
        if (value == null) {
            throw new IllegalArgumentException("UserId value cannot be null");
        }
    }

    public UserId() {
        this(UUID_V7.generate());
    }

    public static UserId generate() {
        return new UserId(UUID_V7.generate());
    }
}
