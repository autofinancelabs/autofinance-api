package com.autofinance.api.creditsimulation.domain.services;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class FinancialMathTest {

    @Test
    void integerExponentMatchesBigDecimalPow() {
        BigDecimal base = new BigDecimal("1.0125");
        assertThat(FinancialMath.pow(base, 3))
                .isEqualByComparingTo(base.pow(3, FinancialMath.MC));
    }

    @Test
    void negativeIntegerExponentIsReciprocal() {
        assertThat(FinancialMath.pow(new BigDecimal("2"), -1).doubleValue()).isCloseTo(0.5, within(1e-12));
        assertThat(FinancialMath.pow(new BigDecimal("4"), -2).doubleValue()).isCloseTo(0.0625, within(1e-12));
    }

    @Test
    void fractionalExponentUsesDoubleHop() {
        // sqrt(1.09)
        assertThat(FinancialMath.pow(new BigDecimal("1.09"), new BigDecimal("0.5")).doubleValue())
                .isCloseTo(Math.sqrt(1.09), within(1e-9));
        // (1.09)^(1/12) ~ TEM for TEA 9%
        BigDecimal oneTwelfth = BigDecimal.ONE.divide(new BigDecimal("12"), FinancialMath.MC);
        assertThat(FinancialMath.pow(new BigDecimal("1.09"), oneTwelfth).doubleValue())
                .isCloseTo(1.0072073, within(1e-6));
    }

    @Test
    void bigDecimalExponentIntegerFastPath() {
        assertThat(FinancialMath.pow(new BigDecimal("1.05"), new BigDecimal("2"))
                .doubleValue()).isCloseTo(1.1025, within(1e-9));
    }
}
