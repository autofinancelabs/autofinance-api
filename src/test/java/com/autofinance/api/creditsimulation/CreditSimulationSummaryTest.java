package com.autofinance.api.creditsimulation;

import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulationFactory;
import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Capitalization;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Cost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Currency;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationSummary;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/** Validates the accumulated-totals summary against the guides' "Resultados acumulados". */
class CreditSimulationSummaryTest {

    private final CreditSimulationFactory factory = new CreditSimulationFactory();

    @Test
    void d3MatchesTheSourceAccumulatedTotals() {
        // docs/guides/metodo-frances.md §16 "Resultados financieros".
        SimulationSummary s = factory.create(GoldenDatasets.d3()).getSummary();

        assertThat(s.totalInterest().doubleValue()).isCloseTo(13025.03, within(1.0));
        assertThat(s.totalAmortization().doubleValue()).isCloseTo(53316.39, within(1.0));
        assertThat(s.totalLoanInstallments().doubleValue()).isCloseTo(65205.03, within(1.0));
        assertThat(s.totalToPay().doubleValue()).isCloseTo(69943.26, within(2.0));

        assertThat(s.totalsPerCost().get("portes").doubleValue()).isCloseTo(1200.00, within(0.01));
        assertThat(s.totalsPerCost().get("gastosAdm").doubleValue()).isCloseTo(2400.00, within(0.01));
        assertThat(s.totalsPerCost().get("riesgo").doubleValue()).isCloseTo(325.00, within(0.5));
        assertThat(s.totalsPerCost().get("desgravamen").doubleValue()).isCloseTo(813.24, within(1.0));
    }

    @Test
    void d1AccumulatedTotalsWithinBand() {
        // docs/guides/metodo-frances-compra-inteligente-balloon.md §7.9 (D1 drifts → band).
        SimulationSummary s = factory.create(GoldenDatasets.d1()).getSummary();

        assertThat(s.totalsPerCost().get("gps").doubleValue()).isCloseTo(740.00, within(0.5));   // 20 × 37
        assertThat(s.totalsPerCost().get("portes").doubleValue()).isCloseTo(129.50, within(0.5)); // 3.50 × 37
        assertThat(s.totalsPerCost().get("riesgo").doubleValue()).isCloseTo(148.00, within(0.5)); // 4 × 37
        assertThat(s.totalsPerCost().get("desgravamen").doubleValue()).isCloseTo(102.72, within(2.0));
        assertThat(s.totalToPay().doubleValue()).isPositive();
    }

    @Test
    void supportsAnAdHocCostEndToEnd() {
        // A custom cost not in the standard set flows input → row → cash flow → totals.
        GenerateSimulationCommand command = new GenerateSimulationCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                new BigDecimal("15000"), Currency.PEN,
                new BigDecimal("0.09"), RateType.EFFECTIVE, (Capitalization) null,
                new BigDecimal("0.20"), new BigDecimal("0.00"),
                3, 30, 360,
                Collections.nCopies(3, GraceType.NONE),
                List.of(Cost.fixedPeriodic("seguroExtra", new BigDecimal("10.00"))),
                new BigDecimal("0.12"));

        CreditSimulation sim = factory.create(command);

        assertThat(sim.getSchedule().get(0).costNamed("seguroExtra").doubleValue()).isEqualTo(10.00);
        assertThat(sim.getSummary().totalsPerCost().get("seguroExtra").doubleValue()).isCloseTo(30.00, within(0.01));
        // the extra cost is included in each period's cash flow.
        assertThat(sim.getSchedule().get(0).cashFlow().doubleValue())
                .isGreaterThan(sim.getSchedule().get(0).installment().doubleValue());
    }
}
