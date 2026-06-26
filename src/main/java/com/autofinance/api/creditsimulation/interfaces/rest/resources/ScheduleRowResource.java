package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

/** One row of the payment schedule (balloon block + regular block + applied costs). */
public record ScheduleRowResource(
        int period,
        @Schema(implementation = GraceType.class) String graceType,
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
