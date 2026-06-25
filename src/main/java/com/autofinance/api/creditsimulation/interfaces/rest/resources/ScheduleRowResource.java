package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

/** One row of the payment schedule (balloon block + regular block + applied costs). */
public record ScheduleRowResource(
        int period,
        String graceType,
        BigDecimal openingBalanceBalloon,
        BigDecimal interestBalloon,
        BigDecimal balloonCreditLifeInsurance,
        BigDecimal closingBalanceBalloon,
        BigDecimal openingBalance,
        BigDecimal interest,
        BigDecimal installment,
        BigDecimal amortization,
        BigDecimal closingBalance,
        BigDecimal cashFlow,
        List<AppliedCostResource> appliedCosts
) {
}
