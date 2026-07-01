package com.autofinance.api.creditsimulation.domain.model.commands;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.Cost;
import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Raw inputs to generate a credit-simulation quote. Carries primitives/enums (not value objects)
 * so the application/REST boundary can map flat input directly; the factory builds the VOs.
 * Costs are a flexible list, so any entity's cost structure can be expressed.
 */
public record GenerateSimulationCommand(
        UUID dealershipId,
        UUID clientId,
        UUID vehicleOfferId,
        BigDecimal salePrice,
        Currency currency,
        BigDecimal rateValue,
        RateType rateType,
        Integer capitalization,
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
