package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.exceptions.PercentageOutOfRangeException;
import com.autofinance.api.creditsimulation.domain.services.FinancialMath;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

/** A fraction in [0, 1) (e.g. 0.20 = 20%). */
@Embeddable
public record Percentage(@Column(name = "value") BigDecimal value) {
    public Percentage {
        if (value == null) {
            throw new IllegalArgumentException("Percentage value cannot be null");
        }
        if (value.signum() < 0 || value.compareTo(BigDecimal.ONE) >= 0) {
            throw new PercentageOutOfRangeException(value);
        }
    }

    public Percentage() {
        this(BigDecimal.ZERO);
    }

    /** Applies this percentage to a base amount: {@code base * value}. */
    public BigDecimal of(BigDecimal base) {
        return base.multiply(value, FinancialMath.MC);
    }
}
