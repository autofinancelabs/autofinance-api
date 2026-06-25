package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

/**
 * One immutable row of the payment schedule. Holds the regular-installment block and the
 * informational balloon ({@code cuotón}) block, plus per-period costs and the period cash flow.
 */
@Embeddable
public record ScheduleRow(
        @Column(name = "period") int period,
        @Enumerated(EnumType.STRING) @Column(name = "grace_type") GraceType graceType,
        // balloon (cuotón) block — informational
        @Column(name = "opening_balance_cuoton") BigDecimal openingBalanceCuoton,
        @Column(name = "interest_cuoton") BigDecimal interestCuoton,
        @Column(name = "closing_balance_cuoton") BigDecimal closingBalanceCuoton,
        // regular installment block
        @Column(name = "opening_balance") BigDecimal openingBalance,
        @Column(name = "interest") BigDecimal interest,
        @Column(name = "installment") BigDecimal installment,
        @Column(name = "amortization") BigDecimal amortization,
        // periodic costs
        @Column(name = "credit_life_insurance") BigDecimal creditLifeInsurance,
        @Column(name = "all_risk_insurance") BigDecimal allRiskInsurance,
        @Column(name = "gps") BigDecimal gps,
        @Column(name = "shipping_fees") BigDecimal shippingFees,
        @Column(name = "admin_fees") BigDecimal adminFees,
        @Column(name = "closing_balance") BigDecimal closingBalance,
        @Column(name = "cash_flow") BigDecimal cashFlow
) {
    public ScheduleRow() {
        this(0, GraceType.NONE,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
