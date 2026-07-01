package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

class CostsTest {

    private final Costs costs = Costs.none();
    private static final BigDecimal SALE_PRICE = new BigDecimal("65000");
    private static final BigDecimal BALANCE = new BigDecimal("10000");

    @Test
    void fixedReturnsTheValueRegardlessOfBalanceOrPrice() {
        Cost c = Cost.fixedPeriodic("gps", new BigDecimal("20.00"));
        assertThat(costs.amountFor(c, BALANCE, SALE_PRICE, 30, 360).doubleValue()).isEqualTo(20.00);
    }

    @Test
    void onBalanceMultipliesTheBalanceByTheMonthlyRateScaledToThePeriod() {
        // Monthly TSD 0.000490; at a 30-day period the factor is 1 → 10000 × 0.000490 = 4.90.
        Cost c = Cost.onBalance("desgravamen", new BigDecimal("0.000490"), false);
        assertThat(costs.amountFor(c, BALANCE, SALE_PRICE, 30, 360).doubleValue()).isCloseTo(4.90, within(1e-9));
    }

    @Test
    void onSalePriceMultipliesThePriceByTheAnnualRateScaledToThePeriod() {
        // Annual TSR 0.001; at a 30/360 period the factor is 1/12 → 65000 × 0.001 / 12 = 5.4167.
        Cost c = Cost.onSalePrice("riesgo", new BigDecimal("0.001"));
        assertThat(costs.amountFor(c, BALANCE, SALE_PRICE, 30, 360).doubleValue()).isCloseTo(5.4167, within(1e-3));
    }

    @Test
    void ratesScaleWithThePaymentFrequency() {
        // ON_BALANCE (monthly base 30): a 15-day period halves the rate.
        Cost balance = Cost.onBalance("desgravamen", new BigDecimal("0.000490"), false);
        assertThat(costs.amountFor(balance, BALANCE, SALE_PRICE, 15, 360).doubleValue())
                .isCloseTo(2.45, within(1e-9));
        // ON_SALE_PRICE (annual base 360): a 90-day period quarters the annual rate.
        Cost price = Cost.onSalePrice("riesgo", new BigDecimal("0.012"));
        assertThat(costs.amountFor(price, BALANCE, SALE_PRICE, 90, 360).doubleValue())
                .isCloseTo(195.0, within(1e-6)); // 65000 × 0.012 × 90/360
    }

    @Test
    void embeddedMustBeAPeriodicOnBalanceRate() {
        assertThatThrownBy(() -> new Cost("x", BigDecimal.ONE, CostBasis.FIXED, CostTiming.PERIODIC, true))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void embeddedRateSumsOnlyEmbeddedCosts() {
        Costs c = new Costs(List.of(
                Cost.onBalance("desgravamen", new BigDecimal("0.000490"), true),
                Cost.onBalance("otroSeguro", new BigDecimal("0.000010"), true),
                Cost.fixedPeriodic("gps", new BigDecimal("20"))));
        assertThat(c.embeddedRate(30).doubleValue()).isCloseTo(0.000500, within(1e-12));
    }

    @Test
    void initialTotalSumsOnlyInitialCosts() {
        Costs c = new Costs(List.of(
                Cost.initial("notario", new BigDecimal("100")),
                Cost.initial("registral", new BigDecimal("50")),
                Cost.fixedPeriodic("gps", new BigDecimal("20"))));
        assertThat(c.initialTotal().doubleValue()).isEqualTo(150.0);
    }
}
