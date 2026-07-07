package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.exceptions.MissingCapitalizationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

class RateTest {

    @Test
    void nominalWithoutCapitalizationIsRejected() {
        assertThatThrownBy(() -> new Rate(new BigDecimal("0.15"), RateType.NOMINAL, null, null))
                .isInstanceOf(MissingCapitalizationException.class);
    }

    @Test
    void convertsNominalDailyToEffective() {
        // D1: TNA 15% daily cap (1 day), 360-day year.
        Rate rate = Rate.nominal(new BigDecimal("0.15"), 1);
        assertThat(rate.toEffectiveAnnual(360).doubleValue()).isCloseTo(0.16179795, within(1e-6));
        assertThat(rate.toPeriodicRate(30, 360).doubleValue()).isCloseTo(0.012575815, within(1e-6));
    }

    @Test
    void supportsAnArbitraryCapitalizationInDays() {
        // TNA 15% capitalized every 15 days (m = 360/15 = 24).
        Rate rate = Rate.nominal(new BigDecimal("0.15"), 15);
        double expected = Math.pow(1 + 0.15 / 24.0, 24) - 1;
        assertThat(rate.toEffectiveAnnual(360).doubleValue()).isCloseTo(expected, within(1e-6));
    }

    @Test
    void convertsEffectiveAnnualToPeriodic() {
        // D2: TEA 9%, 30-day periods.
        Rate rate = Rate.effective(new BigDecimal("0.09"));
        assertThat(rate.toEffectiveAnnual(360).doubleValue()).isEqualTo(0.09);
        assertThat(rate.toPeriodicRate(30, 360).doubleValue()).isCloseTo(0.0072073, within(1e-6));
    }

    @Test
    void supportsEffectiveRateGivenForASubAnnualPeriod() {
        // An effective monthly rate (TEM, 30 days) compounds up to the annual effective rate (TEA).
        Rate monthly = Rate.effective(new BigDecimal("0.0072073"), 30);
        assertThat(monthly.toEffectiveAnnual(360).doubleValue()).isCloseTo(0.09, within(1e-5));

        // An effective semiannual rate (TES, 180 days) likewise.
        Rate semiannual = Rate.effective(new BigDecimal("0.044030651"), 180);
        assertThat(semiannual.toEffectiveAnnual(360).doubleValue()).isCloseTo(0.09, within(1e-6));
    }

    @Test
    void supportsANominalRateQuotedForASubAnnualPeriod() {
        // A nominal rate quoted per 30 days (TNM 1.2%), capitalized daily (1 day).
        // TEA = (1 + value·C/R)^(D/C) - 1, with C=1, R=30, D=360.
        Rate nominalMonthly = Rate.nominal(new BigDecimal("0.012"), 1, 30);
        double expected = Math.pow(1 + 0.012 * 1.0 / 30.0, 360.0 / 1.0) - 1;
        assertThat(nominalMonthly.toEffectiveAnnual(360).doubleValue()).isCloseTo(expected, within(1e-6));
    }

    @Test
    void aNominalRateWithAnnualPeriodMatchesTheAnnualQuote() {
        // ratePeriod null (annual) must equal an explicit 360-day period and the plain nominal formula.
        Rate annualImplicit = Rate.nominal(new BigDecimal("0.15"), 30);
        Rate annualExplicit = Rate.nominal(new BigDecimal("0.15"), 30, 360);
        double tea = annualImplicit.toEffectiveAnnual(360).doubleValue();
        assertThat(tea).isCloseTo(Math.pow(1 + 0.15 / 12.0, 12) - 1, within(1e-9));
        assertThat(annualExplicit.toEffectiveAnnual(360).doubleValue()).isCloseTo(tea, within(1e-9));
    }

    @Test
    void supportsANonDivisorPeriodViaFractionalExponent() {
        // Effective rate quoted for a 100-day period (m = 360/100 = 3.6, not an integer).
        Rate effective100 = Rate.effective(new BigDecimal("0.05"), 100);
        double expectedEff = Math.pow(1.05, 360.0 / 100.0) - 1;
        assertThat(effective100.toEffectiveAnnual(360).doubleValue()).isCloseTo(expectedEff, within(1e-6));

        // Nominal rate capitalized every 100 days (m = 3.6).
        Rate nominal100 = Rate.nominal(new BigDecimal("0.15"), 100);
        double m = 360.0 / 100.0;
        double expectedNom = Math.pow(1 + 0.15 / m, m) - 1;
        assertThat(nominal100.toEffectiveAnnual(360).doubleValue()).isCloseTo(expectedNom, within(1e-6));
    }
}
