package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

/** A cost applied in a specific schedule row: its name and the amount charged that period. */
@Embeddable
public record AppliedCost(
        @Column(name = "name") String name,
        @Column(name = "amount") BigDecimal amount
) {
    public AppliedCost() {
        this("", BigDecimal.ZERO);
    }
}
