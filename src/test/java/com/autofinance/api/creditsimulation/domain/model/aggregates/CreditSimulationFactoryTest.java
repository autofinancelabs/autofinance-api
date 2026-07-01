package com.autofinance.api.creditsimulation.domain.model.aggregates;

import com.autofinance.api.creditsimulation.GoldenDatasets;
import com.autofinance.api.creditsimulation.domain.exceptions.InvalidSimulationConfigurationException;
import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationState;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

class CreditSimulationFactoryTest {

    private final CreditSimulationFactory factory = new CreditSimulationFactory();

    private static GenerateSimulationCommand command(String initialPct, String balloonPct,
                                                     int n, List<GraceType> gracePlan) {
        return new GenerateSimulationCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                new BigDecimal("15000"), Currency.PEN,
                new BigDecimal("0.09"), RateType.EFFECTIVE, null, null,
                new BigDecimal(initialPct), new BigDecimal(balloonPct),
                n, 30, 360,
                gracePlan,
                List.of(),
                new BigDecimal("0.12"));
    }

    @Test
    void buildsAndGeneratesValidSimulation() {
        CreditSimulation sim = factory.create(GoldenDatasets.d2());
        assertThat(sim.getState()).isEqualTo(SimulationState.GENERATED);
        assertThat(sim.getSchedule()).isNotEmpty();
        assertThat(sim.getId()).isNotNull();
    }

    @Test
    void rejectsInitialPlusBalloonNotBelowOne() {
        assertThatThrownBy(() -> factory.create(
                command("0.60", "0.50", 3, Collections.nCopies(3, GraceType.NONE))))
                .isInstanceOf(InvalidSimulationConfigurationException.class);
    }

    @Test
    void rejectsGracePlanLengthMismatch() {
        assertThatThrownBy(() -> factory.create(
                command("0.20", "0.00", 3, Collections.nCopies(2, GraceType.NONE))))
                .isInstanceOf(InvalidSimulationConfigurationException.class);
    }

    @Test
    void rejectsAllPeriodsInGrace() {
        assertThatThrownBy(() -> factory.create(
                command("0.20", "0.00", 3, Collections.nCopies(3, GraceType.TOTAL))))
                .isInstanceOf(InvalidSimulationConfigurationException.class);
    }

    @Test
    void handlesZeroRateAsEqualPrincipalPayments() {
        GenerateSimulationCommand cmd = new GenerateSimulationCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                new BigDecimal("12000"), Currency.PEN,
                BigDecimal.ZERO, RateType.EFFECTIVE, null, null,
                BigDecimal.ZERO, BigDecimal.ZERO,
                12, 30, 360,
                Collections.nCopies(12, GraceType.NONE),
                List.of(),
                new BigDecimal("0.10"));

        CreditSimulation sim = factory.create(cmd);

        assertThat(sim.getState()).isEqualTo(SimulationState.GENERATED);
        // 0% loan of 12,000 over 12 → equal payments of 1,000, balance to 0.
        assertThat(sim.getSchedule().get(0).installment().doubleValue()).isCloseTo(1000.00, within(0.01));
        assertThat(sim.getSchedule().get(11).closingBalance().doubleValue()).isCloseTo(0.0, within(0.01));
    }
}
