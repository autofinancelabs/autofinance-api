package com.autofinance.api.creditsimulation.interfaces.rest.transform;

import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.AppliedCost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Cost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Indicators;
import com.autofinance.api.shared.domain.model.valueobjects.Money;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Rate;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ScheduleRow;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationSummary;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Term;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.AppliedCostResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.CostResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.IndicatorsResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.MoneyResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.RateResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.ScheduleRowResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.SimulationResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.SummaryResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.TermResource;

/** Builds the response resource tree from a {@link CreditSimulation} aggregate. */
public final class SimulationResourceFromEntityAssembler {

    private SimulationResourceFromEntityAssembler() {
    }

    public static SimulationResource toResourceFromEntity(CreditSimulation s) {
        return new SimulationResource(
                s.getId().value(),
                s.getClientId().value(),
                s.getVehicleOfferId().value(),
                money(s.getSalePrice()),
                rate(s.getRate()),
                s.getInitialPercentage().value(),
                s.getBalloonPercentage().value(),
                term(s.getTerm()),
                s.getGrace().stream().map(Enum::name).toList(),
                s.getCosts().stream().map(SimulationResourceFromEntityAssembler::cost).toList(),
                rate(s.getCostOfCapital()),
                money(s.getLoanAmount()),
                money(s.getFinancedBalance()),
                indicators(s.getIndicators()),
                s.getSchedule().stream().map(SimulationResourceFromEntityAssembler::row).toList(),
                summary(s.getSummary()),
                s.getState().name(),
                s.getCreatedAt() == null ? null : s.getCreatedAt().toInstant().toString()
        );
    }

    private static MoneyResource money(Money m) {
        return new MoneyResource(m.amount(), m.currency().name());
    }

    private static RateResource rate(Rate r) {
        return new RateResource(r.value(), r.type().name(), r.capitalization(), r.ratePeriod());
    }

    private static TermResource term(Term t) {
        return new TermResource(t.numberOfInstallments(), t.frequencyDays(), t.installmentsPerYear(), t.daysPerYear());
    }

    private static CostResource cost(Cost c) {
        return new CostResource(c.name(), c.value(), c.basis().name(), c.timing().name(), c.embedded());
    }

    private static IndicatorsResource indicators(Indicators i) {
        return new IndicatorsResource(i.npv(), i.periodicIrr(), i.tcea(), i.effectiveAnnualRate(),
                i.periodicRate(), i.periodicCostOfCapital());
    }

    private static ScheduleRowResource row(ScheduleRow r) {
        return new ScheduleRowResource(
                r.period(),
                r.graceType().name(),
                r.openingBalanceBalloon(),
                r.interestBalloon(),
                r.balloonCreditLifeInsurance(),
                r.closingBalanceBalloon(),
                r.openingBalance(),
                r.interest(),
                r.installment(),
                r.amortization(),
                r.closingBalance(),
                r.cashFlow(),
                r.appliedCosts().stream().map(SimulationResourceFromEntityAssembler::appliedCost).toList()
        );
    }

    private static AppliedCostResource appliedCost(AppliedCost a) {
        return new AppliedCostResource(a.name(), a.amount());
    }

    private static SummaryResource summary(SimulationSummary s) {
        return new SummaryResource(s.totalInterest(), s.totalAmortization(), s.totalLoanInstallments(),
                s.totalToPay(), s.totalsPerCost());
    }
}
