package com.autofinance.api.shared.domain.model.valueobjects;

import com.autofinance.api.shared.domain.exceptions.CurrencyMismatchException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @Test
    void addsAndSubtractsSameCurrency() {
        Money a = Money.of(new BigDecimal("100.00"), Currency.PEN);
        Money b = Money.of(new BigDecimal("25.50"), Currency.PEN);
        assertThat(a.add(b).amount().doubleValue()).isEqualTo(125.50);
        assertThat(a.subtract(b).amount().doubleValue()).isEqualTo(74.50);
        assertThat(a.multiply(new BigDecimal("2")).amount().doubleValue()).isEqualTo(200.00);
    }

    @Test
    void rejectsMixingCurrencies() {
        Money pen = Money.of(new BigDecimal("100"), Currency.PEN);
        Money usd = Money.of(new BigDecimal("100"), Currency.USD);
        assertThatThrownBy(() -> pen.add(usd)).isInstanceOf(CurrencyMismatchException.class);
    }

    @Test
    void reportsPositive() {
        assertThat(Money.of(new BigDecimal("1"), Currency.PEN).isPositive()).isTrue();
        assertThat(Money.zero(Currency.PEN).isPositive()).isFalse();
    }
}
