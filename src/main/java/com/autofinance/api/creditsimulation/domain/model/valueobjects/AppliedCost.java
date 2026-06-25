package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import java.math.BigDecimal;

/**
 * A cost applied in a specific schedule row: its name and the amount charged that period.
 * Persisted inside the {@code schedule} jsonb column (serialized by Jackson), so it carries no JPA
 * mapping annotations.
 */
public record AppliedCost(
        String name,
        BigDecimal amount
) {
    public AppliedCost() {
        this("", BigDecimal.ZERO);
    }
}
