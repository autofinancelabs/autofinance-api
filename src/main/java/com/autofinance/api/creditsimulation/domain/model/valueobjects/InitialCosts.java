package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.services.FinancialMath;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

/** One-off costs financed into the loan (notary, registry, appraisal, fees). */
@Embeddable
public record InitialCosts(
        @Column(name = "notary_cost") BigDecimal notary,
        @Column(name = "registry_cost") BigDecimal registry,
        @Column(name = "appraisal_cost") BigDecimal appraisal,
        @Column(name = "fees_cost") BigDecimal fees
) {
    public InitialCosts {
        requireNonNegative(notary, "notary");
        requireNonNegative(registry, "registry");
        requireNonNegative(appraisal, "appraisal");
        requireNonNegative(fees, "fees");
    }

    public InitialCosts() {
        this(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public BigDecimal total() {
        return notary.add(registry, FinancialMath.MC)
                .add(appraisal, FinancialMath.MC)
                .add(fees, FinancialMath.MC);
    }

    private static void requireNonNegative(BigDecimal value, String name) {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException(name + " cost must be >= 0");
        }
    }
}
