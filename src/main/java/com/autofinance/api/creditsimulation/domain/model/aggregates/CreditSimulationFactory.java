package com.autofinance.api.creditsimulation.domain.model.aggregates;

import com.autofinance.api.creditsimulation.domain.exceptions.InvalidSimulationConfigurationException;
import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ClientId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Costs;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceConfiguration;
import com.autofinance.api.shared.domain.model.valueobjects.Money;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Percentage;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Rate;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Term;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.VehicleOfferId;
import com.autofinance.api.creditsimulation.domain.services.IndicatorsCalculator;
import com.autofinance.api.creditsimulation.domain.services.ScheduleCalculator;
import com.autofinance.api.creditsimulation.domain.services.SummaryCalculator;

import java.math.BigDecimal;

/**
 * Domain factory for the {@link CreditSimulation} aggregate: assembles a valid simulation from raw
 * inputs, enforces the cross-field configuration invariants, and triggers the initial calculation.
 * Co-located with the aggregate it creates.
 */
public class CreditSimulationFactory {

    private final ScheduleCalculator scheduleCalculator = new ScheduleCalculator();
    private final IndicatorsCalculator indicatorsCalculator = new IndicatorsCalculator();
    private final SummaryCalculator summaryCalculator = new SummaryCalculator();

    public CreditSimulation create(GenerateSimulationCommand command) {
        Money salePrice = new Money(command.salePrice(), command.currency());
        Rate rate = new Rate(command.rateValue(), command.rateType(), command.capitalization(), command.ratePeriod());
        Percentage initialPercentage = new Percentage(command.initialPercentage());
        Percentage balloonPercentage = new Percentage(command.balloonPercentage());
        Term term = Term.of(command.numberOfInstallments(), command.frequencyDays(), command.daysPerYear());
        GraceConfiguration grace = new GraceConfiguration(command.gracePlan());
        Costs costs = new Costs(command.costs());
        Rate costOfCapital = Rate.effective(command.costOfCapitalAnnual());

        if (initialPercentage.value().add(balloonPercentage.value()).compareTo(BigDecimal.ONE) >= 0) {
            throw new InvalidSimulationConfigurationException(
                    "initial percentage + balloon percentage must be < 1");
        }
        if (grace.size() != term.numberOfInstallments()) {
            throw new InvalidSimulationConfigurationException(
                    "grace plan length must equal the number of installments");
        }
        if (grace.totalCount() + grace.partialCount() >= term.numberOfInstallments()) {
            throw new InvalidSimulationConfigurationException(
                    "grace periods must be fewer than the number of installments");
        }

        CreditSimulation simulation = new CreditSimulation(
                SimulationId.generate(), command.dealershipId(),
                new ClientId(command.clientId()), new VehicleOfferId(command.vehicleOfferId()),
                salePrice, rate, initialPercentage, balloonPercentage, term, grace, costs, costOfCapital);

        if (!simulation.getLoanAmount().isPositive()) {
            throw new InvalidSimulationConfigurationException("loan amount must be > 0");
        }

        simulation.generate(scheduleCalculator, indicatorsCalculator, summaryCalculator);
        return simulation;
    }
}
