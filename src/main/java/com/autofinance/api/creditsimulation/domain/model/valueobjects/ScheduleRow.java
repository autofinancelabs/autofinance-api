package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.services.FinancialMath;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;

import java.math.BigDecimal;
import java.util.List;

/**
 * One immutable row of the payment schedule: the deferred-balloon block, the regular-installment
 * block, and the per-period applied costs (a flexible breakdown, since costs are user-defined).
 * The persistence mapping of {@code appliedCosts} is deferred to the persistence slice.
 */
@Embeddable
public record ScheduleRow(
        @Column(name = "period") int period,
        @Enumerated(EnumType.STRING) @Column(name = "grace_type") GraceType graceType,
        // deferred balloon block
        @Column(name = "opening_balance_balloon") BigDecimal openingBalanceBalloon,
        @Column(name = "interest_balloon") BigDecimal interestBalloon,
        @Column(name = "balloon_credit_life_insurance") BigDecimal balloonCreditLifeInsurance,
        @Column(name = "closing_balance_balloon") BigDecimal closingBalanceBalloon,
        // regular installment block
        @Column(name = "opening_balance") BigDecimal openingBalance,
        @Column(name = "interest") BigDecimal interest,
        @Column(name = "installment") BigDecimal installment,
        @Column(name = "amortization") BigDecimal amortization,
        @Column(name = "closing_balance") BigDecimal closingBalance,
        @Column(name = "cash_flow") BigDecimal cashFlow,
        // flexible per-period cost breakdown (mapping deferred)
        @Transient List<AppliedCost> appliedCosts
) {
    public ScheduleRow {
        appliedCosts = appliedCosts == null ? List.of() : List.copyOf(appliedCosts);
    }

    public ScheduleRow() {
        this(0, GraceType.NONE,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, List.of());
    }

    /** Σ of the applied costs this period. */
    public BigDecimal costTotal() {
        return appliedCosts.stream()
                .map(AppliedCost::amount)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b, FinancialMath.MC));
    }

    /** Amount of a named cost this period, or zero if not present. */
    public BigDecimal costNamed(String name) {
        return appliedCosts.stream()
                .filter(c -> c.name().equals(name))
                .map(AppliedCost::amount)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }
}
