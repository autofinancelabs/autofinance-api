package com.autofinance.api.creditsimulation.domain.model.commands;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.Capitalization;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Cost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Use-case request to generate a simulation. Unlike {@link GenerateSimulationCommand} (the factory input)
 * it carries NO sale price / currency — those are authoritative from the referenced vehicle offer and are
 * resolved by the command service (ACL) before the factory runs.
 */
public record RequestSimulationCommand(
        UUID dealershipId,
        UUID clientId,
        UUID vehicleOfferId,
        BigDecimal rateValue,
        RateType rateType,
        Capitalization capitalization,
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
