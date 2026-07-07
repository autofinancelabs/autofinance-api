package com.autofinance.api.creditsimulation.domain.model.commands;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.Cost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Use-case request to edit an existing simulation. Same shape as {@link RequestSimulationCommand} plus the
 * target {@code simulationId}: it carries NO sale price / currency — those are resolved from the (possibly
 * changed) referenced vehicle offer by the command service (ACL) before the aggregate is reconfigured.
 */
public record UpdateSimulationCommand(
        UUID simulationId,
        UUID dealershipId,
        UUID clientId,
        UUID vehicleOfferId,
        BigDecimal rateValue,
        RateType rateType,
        Integer capitalization,
        Integer ratePeriod,
        BigDecimal initialPercentage,
        BigDecimal balloonPercentage,
        int numberOfInstallments,
        int frequencyDays,
        int daysPerYear,
        List<GraceType> gracePlan,
        List<Cost> costs,
        BigDecimal costOfCapitalAnnual
) {
}
