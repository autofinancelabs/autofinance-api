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
        assertThatThrownBy(() -> new Rate(new BigDecimal("0.15"), RateType.NOMINAL, null))
                .isInstanceOf(MissingCapitalizationException.class);
    }

    @Test
    void convertsNominalDailyToEffective() {
        // D1: TNA 15% daily cap, 360-day year.
        Rate rate = Rate.nominal(new BigDecimal("0.15"), Capitalization.DAILY);
        assertThat(rate.toEffectiveAnnual(360).doubleValue()).isCloseTo(0.16179795, within(1e-6));
        assertThat(rate.toPeriodicRate(30, 360).doubleValue()).isCloseTo(0.012575815, within(1e-6));
    }

    @Test
    void convertsEffectiveAnnualToPeriodic() {
        // D2: TEA 9%, 30-day periods.
        Rate rate = Rate.effective(new BigDecimal("0.09"));
        assertThat(rate.toEffectiveAnnual(360).doubleValue()).isEqualTo(0.09);
        assertThat(rate.toPeriodicRate(30, 360).doubleValue()).isCloseTo(0.0072073, within(1e-6));
    }
}
