package com.autofinance.api.creditsimulation;

import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulationFactory;
import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ScheduleRow;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationState;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Validates the engine against the three golden datasets (docs/report/datos-de-prueba.md).
 * Tolerances follow §3 of that doc: sources round intermediate rates, so we assert within
 * documented bands rather than cent-for-cent (especially D1's drifting cuotón block).
 */
class CreditSimulationGoldenDatasetTest {

    private final CreditSimulationFactory factory = new CreditSimulationFactory();

    private static ScheduleRow row(CreditSimulation sim, int period) {
        return sim.getSchedule().stream()
                .filter(r -> r.period() == period)
                .findFirst()
                .orElseThrow(() -> new AssertionError("no row for period " + period));
    }

    /**
     * D1 exercises the full balloon + grace + embedded-credit-life-insurance + costs path. The balloon
     * also capitalizes its credit-life insurance (it grows at {@code j = i + TSD}), so its present value
     * is {@code balloon / (1 + j)^(n+1)} — which reproduces the source values exactly.
     */
    @Nested
    class D1PlanThirtySixCompraInteligente {

        private final CreditSimulation sim = factory.create(GoldenDatasets.d1());

        @Test
        void derivesLoanAndFinancedBalance() {
            assertThat(sim.getLoanAmount().amount().doubleValue()).isCloseTo(12975.00, within(0.01));
            assertThat(sim.getFinancedBalance().amount().doubleValue()).isCloseTo(9015.99, within(0.5));
            assertThat(sim.getState()).isEqualTo(SimulationState.GENERATED);
        }

        @Test
        void convertsRates() {
            assertThat(sim.getIndicators().effectiveAnnualRate().doubleValue()).isCloseTo(0.16179795, within(1e-5));
            assertThat(sim.getIndicators().periodicRate().doubleValue()).isCloseTo(0.012575815, within(1e-6));
        }

        @Test
        void buildsScheduleWithSettlement() {
            assertThat(sim.getSchedule()).hasSize(37);
            assertThat(row(sim, 7).installment().doubleValue()).isCloseTo(379.16, within(0.5));
            assertThat(row(sim, 36).closingBalance().doubleValue()).isCloseTo(0.0, within(0.1));
            assertThat(row(sim, 37).cashFlow().doubleValue()).isCloseTo(6431.00, within(0.5));
        }

        @Test
        void computesIndicators() {
            assertThat(sim.getIndicators().periodicIrr().doubleValue()).isCloseTo(0.015861749, within(3e-5));
            assertThat(sim.getIndicators().tcea().doubleValue()).isCloseTo(0.207856, within(1e-4));
            assertThat(sim.getIndicators().npv().doubleValue()).isCloseTo(4436.18, within(2.0));
        }
    }

    @Nested
    class D2SimpleFrench {

        private final CreditSimulation sim = factory.create(GoldenDatasets.d2());

        @Test
        void computesCuotaAndIndicators() {
            assertThat(sim.getSchedule()).hasSize(3);
            assertThat(row(sim, 1).installment().doubleValue()).isCloseTo(4057.80, within(2.0));
            assertThat(row(sim, 3).closingBalance().doubleValue()).isCloseTo(0.0, within(0.05));
            assertThat(sim.getIndicators().tcea().doubleValue()).isCloseTo(0.09, within(1e-3));
            assertThat(sim.getIndicators().npv().doubleValue()).isPositive().isCloseTo(54.03, within(3.0));
        }
    }

    @Nested
    class D3SixtyMonthNegativeVan {

        private final CreditSimulation sim = factory.create(GoldenDatasets.d3());

        @Test
        void hasGraceAndLongSchedule() {
            assertThat(sim.getSchedule()).hasSize(60);
            assertThat(row(sim, 1).graceType()).isEqualTo(GraceType.TOTAL);
            assertThat(row(sim, 3).graceType()).isEqualTo(GraceType.TOTAL);
            assertThat(row(sim, 1).installment().doubleValue()).isZero();
            assertThat(row(sim, 4).installment().doubleValue()).isCloseTo(1143.95, within(1.0));
            assertThat(row(sim, 60).closingBalance().doubleValue()).isCloseTo(0.0, within(0.1));
        }

        @Test
        void computesNegativeVan() {
            assertThat(sim.getIndicators().tcea().doubleValue()).isCloseTo(0.122243, within(3e-3));
            assertThat(sim.getIndicators().periodicIrr().doubleValue()).isCloseTo(0.009657104, within(1e-4));
            assertThat(sim.getIndicators().npv().doubleValue()).isNegative().isCloseTo(-9420.70, within(15.0));
        }
    }
}
