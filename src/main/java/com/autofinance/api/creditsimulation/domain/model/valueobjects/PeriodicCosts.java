package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.services.FinancialMath;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

/**
 * Per-period costs that accompany the installment. {@code creditLifeInsuranceRate} (TSD) is a
 * rate applied to the outstanding balance; the rest are fixed amounts per period.
 */
@Embeddable
public record PeriodicCosts(
        @Column(name = "credit_life_insurance_rate") BigDecimal creditLifeInsuranceRate,
        @Column(name = "all_risk_insurance") BigDecimal allRiskInsurance,
        @Column(name = "gps") BigDecimal gps,
        @Column(name = "shipping_fees") BigDecimal shippingFees,
        @Column(name = "admin_fees") BigDecimal adminFees
) {
    public PeriodicCosts {
        requireNonNegative(creditLifeInsuranceRate, "creditLifeInsuranceRate");
        requireNonNegative(allRiskInsurance, "allRiskInsurance");
        requireNonNegative(gps, "gps");
        requireNonNegative(shippingFees, "shippingFees");
        requireNonNegative(adminFees, "adminFees");
    }

    public PeriodicCosts() {
        this(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    /** Fixed per-period costs, excluding the balance-dependent credit-life insurance. */
    public BigDecimal fixedPerPeriod() {
        return allRiskInsurance.add(gps, FinancialMath.MC)
                .add(shippingFees, FinancialMath.MC)
                .add(adminFees, FinancialMath.MC);
    }

    private static void requireNonNegative(BigDecimal value, String name) {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException(name + " must be >= 0");
        }
    }
}
