package com.autofinance.api.creditsimulation;

import com.autofinance.api.creditsimulation.domain.factories.CreditSimulationFactory;
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
     * D1 exercises the full balloon + grace + embedded-desgravamen + costs path. Its source (the IB
     * compra-inteligente Excel) splits the cuotón using an extra desgravamen-on-cuotón column that our
     * clean model does not replicate (documented drift), so D1's source-specific intermediate values
     * (VP 3959.01, cuota 379.16, IRR 0.0158617) are NOT reproduced cent-for-cent. We assert what the
     * clean model guarantees — exact loan/rates, a balanced 37-row schedule, the nominal balloon
     * settlement, and sane indicators. D2/D3 are the precise arithmetic validators.
     */
    @Nested
    class D1PlanThirtySixCompraInteligente {

        private final CreditSimulation sim = factory.create(GoldenDatasets.d1());

        @Test
        void derivesLoanAndState() {
            assertThat(sim.getLoanAmount().amount().doubleValue()).isCloseTo(12975.00, within(0.01));
            assertThat(sim.getFinancedBalance().amount().doubleValue())
                    .isPositive()
                    .isLessThan(sim.getLoanAmount().amount().doubleValue());
            assertThat(sim.getState()).isEqualTo(SimulationState.GENERATED);
        }

        @Test
        void convertsRatesExactly() {
            assertThat(sim.getIndicators().effectiveAnnualRate().doubleValue()).isCloseTo(0.16179795, within(1e-4));
            assertThat(sim.getIndicators().periodicRate().doubleValue()).isCloseTo(0.012575815, within(1e-5));
        }

        @Test
        void buildsBalancedScheduleWithNominalSettlement() {
            assertThat(sim.getSchedule()).hasSize(37);
            assertThat(row(sim, 36).closingBalance().doubleValue()).isCloseTo(0.0, within(0.1));
            assertThat(row(sim, 7).installment().doubleValue()).isPositive();
            // settlement pays the nominal balloon (6400) + fixed period costs (31).
            assertThat(row(sim, 37).cashFlow().doubleValue()).isCloseTo(6431.00, within(0.5));
        }

        @Test
        void producesSaneIndicators() {
            assertThat(sim.getIndicators().npv().doubleValue()).isPositive();
            assertThat(sim.getIndicators().periodicIrr().doubleValue()).isBetween(0.014, 0.017);
            // TCEA exceeds the compensatory TEA because of insurance and costs.
            assertThat(sim.getIndicators().tcea().doubleValue())
                    .isGreaterThan(sim.getIndicators().effectiveAnnualRate().doubleValue());
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
