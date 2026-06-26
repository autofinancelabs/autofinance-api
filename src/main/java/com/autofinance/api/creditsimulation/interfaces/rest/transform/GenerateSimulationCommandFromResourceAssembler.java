package com.autofinance.api.creditsimulation.interfaces.rest.transform;

import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Capitalization;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Cost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.CostBasis;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.CostTiming;
import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.CostResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.GenerateSimulationResource;

import java.util.List;
import java.util.UUID;

/**
 * Builds a {@link GenerateSimulationCommand} from the request resource and the current dealership
 * (tenant, from the header). Translates wire Strings into domain enums — an unknown value raises
 * {@code IllegalArgumentException}, mapped to 400 by the global handler.
 */
public final class GenerateSimulationCommandFromResourceAssembler {

    private GenerateSimulationCommandFromResourceAssembler() {
    }

    public static GenerateSimulationCommand toCommandFromResource(UUID dealershipId, GenerateSimulationResource r) {
        List<GraceType> gracePlan = r.gracePlan().stream().map(GraceType::valueOf).toList();
        List<Cost> costs = r.costs().stream().map(GenerateSimulationCommandFromResourceAssembler::toCost).toList();
        Capitalization capitalization = (r.capitalization() == null || r.capitalization().isBlank())
                ? null
                : Capitalization.valueOf(r.capitalization());

        return new GenerateSimulationCommand(
                dealershipId,
                r.clientId(),
                r.vehicleOfferId(),
                r.salePrice(),
                Currency.valueOf(r.currency()),
                r.rateValue(),
                RateType.valueOf(r.rateType()),
                capitalization,
                r.initialPercentage(),
                r.balloonPercentage(),
                r.numberOfInstallments(),
                r.frequencyDays(),
                r.daysPerYear(),
                gracePlan,
                costs,
                r.costOfCapitalAnnual()
        );
    }

    private static Cost toCost(CostResource c) {
        return new Cost(c.name(), c.value(), CostBasis.valueOf(c.basis()), CostTiming.valueOf(c.timing()), c.embedded());
    }
}
