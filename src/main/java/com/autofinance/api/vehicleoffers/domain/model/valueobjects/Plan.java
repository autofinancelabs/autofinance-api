package com.autofinance.api.vehicleoffers.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * A standard payment plan configuration (e.g. "Plan 36" = 36 installments). Optional on a
 * {@code VehicleOffer}: when no plan applies the aggregate holds {@code null}. When present, both
 * fields are required.
 */
@Embeddable
public record Plan(
        @Column(name = "plan_name") String name,
        @Column(name = "plan_installments") Integer installments
) {
    public Plan {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Plan name cannot be null or blank");
        }
        if (installments == null || installments <= 0) {
            throw new IllegalArgumentException("Plan installments must be positive but was " + installments);
        }
    }

    /** Builds a plan from optional inputs; returns {@code null} when neither field is provided. */
    public static Plan of(String name, Integer installments) {
        if (name == null && installments == null) {
            return null;
        }
        return new Plan(name, installments);
    }
}
