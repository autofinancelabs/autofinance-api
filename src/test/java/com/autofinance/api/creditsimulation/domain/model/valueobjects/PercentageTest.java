package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.exceptions.PercentageOutOfRangeException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentageTest {

    @Test
    void acceptsRangeZeroInclusiveToOneExclusive() {
        assertThat(new Percentage(new BigDecimal("0")).value().doubleValue()).isZero();
        assertThat(new Percentage(new BigDecimal("0.999")).value().doubleValue()).isEqualTo(0.999);
    }

    @Test
    void rejectsOutOfRange() {
        assertThatThrownBy(() -> new Percentage(new BigDecimal("1.0")))
                .isInstanceOf(PercentageOutOfRangeException.class);
        assertThatThrownBy(() -> new Percentage(new BigDecimal("1.5")))
                .isInstanceOf(PercentageOutOfRangeException.class);
        assertThatThrownBy(() -> new Percentage(new BigDecimal("-0.1")))
                .isInstanceOf(PercentageOutOfRangeException.class);
    }

    @Test
    void appliesToBase() {
        assertThat(new Percentage(new BigDecimal("0.20")).of(new BigDecimal("16000")).doubleValue())
                .isEqualTo(3200.0);
    }
}
