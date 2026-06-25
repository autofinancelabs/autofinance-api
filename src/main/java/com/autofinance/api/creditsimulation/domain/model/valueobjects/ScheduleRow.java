package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.services.FinancialMath;

import java.math.BigDecimal;
import java.util.List;

/**
 * One immutable row of the payment schedule: the deferred-balloon block, the regular-installment
 * block, and the per-period applied costs (a flexible breakdown, since costs are user-defined).
 * Persisted inside the aggregate's {@code schedule} jsonb column (serialized as a plain record by
 * Jackson), so it carries no JPA mapping annotations.
 */
public record ScheduleRow(
        int period,
        GraceType graceType,
        // deferred balloon block
        BigDecimal openingBalanceBalloon,
        BigDecimal interestBalloon,
        BigDecimal balloonCreditLifeInsurance,
        BigDecimal closingBalanceBalloon,
        // regular installment block
        BigDecimal openingBalance,
        BigDecimal interest,
        BigDecimal installment,
        BigDecimal amortization,
        BigDecimal closingBalance,
        BigDecimal cashFlow,
        // flexible per-period cost breakdown
        List<AppliedCost> appliedCosts
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
