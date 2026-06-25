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
        assertThat(costs.amountFor(c, BALANCE, SALE_PRICE).doubleValue()).isEqualTo(20.00);
    }

    @Test
    void onBalanceMultipliesTheOutstandingBalance() {
        Cost c = Cost.onBalance("desgravamen", new BigDecimal("0.000490"), false);
        assertThat(costs.amountFor(c, BALANCE, SALE_PRICE).doubleValue()).isCloseTo(4.90, within(1e-9));
    }

    @Test
    void onSalePriceMultipliesTheSalePrice() {
        Cost c = Cost.onSalePrice("riesgo", new BigDecimal("0.00008333"));
        assertThat(costs.amountFor(c, BALANCE, SALE_PRICE).doubleValue()).isCloseTo(5.41645, within(1e-4));
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
        assertThat(c.embeddedRate().doubleValue()).isCloseTo(0.000500, within(1e-12));
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
