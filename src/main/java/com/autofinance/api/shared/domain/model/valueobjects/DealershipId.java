package com.autofinance.api.shared.domain.model.valueobjects;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

/**
 * The dealership (tenant) identifier. Used as the IAM {@code Dealership} aggregate's {@code @EmbeddedId}
 * ({@code @Column(name = "id")}) and as a by-id reference in other contexts' domain events. Generated as a
 * time-ordered UUIDv7.
 */
@Embeddable
public record DealershipId(@Column(name = "id") UUID value) {

    private static final NoArgGenerator UUID_V7 = Generators.timeBasedEpochGenerator();

    public DealershipId {
        if (value == null) {
            throw new IllegalArgumentException("DealershipId value cannot be null");
        }
    }

    public DealershipId() {
        this(UUID_V7.generate());
    }

    public static DealershipId generate() {
        return new DealershipId(UUID_V7.generate());
    }
}
