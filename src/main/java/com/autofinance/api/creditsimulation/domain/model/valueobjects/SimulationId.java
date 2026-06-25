package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

/** Identity of a {@code CreditSimulation} aggregate. Generated as a time-ordered UUIDv7. */
@Embeddable
public record SimulationId(@Column(name = "id") UUID value) {

    private static final NoArgGenerator UUID_V7 = Generators.timeBasedEpochGenerator();

    public SimulationId {
        if (value == null) {
            throw new IllegalArgumentException("SimulationId value cannot be null");
        }
    }

    public SimulationId() {
        this(UUID_V7.generate());
    }

    public static SimulationId generate() {
        return new SimulationId(UUID_V7.generate());
    }
}
