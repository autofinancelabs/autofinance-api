package com.autofinance.api.shared.domain.model.valueobjects;

import com.autofinance.api.shared.domain.exceptions.CurrencyMismatchException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/** A monetary amount in a single currency. Operations across currencies are rejected. */
@Embeddable
public record Money(
        @Column(name = "amount") BigDecimal amount,
        @Enumerated(EnumType.STRING) @Column(name = "currency") Currency currency
) {

    /** Working precision for monetary arithmetic; round only for display. */
    private static final MathContext MC = new MathContext(34, RoundingMode.HALF_UP);

    public Money {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("Money requires an amount and a currency");
        }
    }

    public Money() {
        this(BigDecimal.ZERO, Currency.PEN);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount, MC), currency);
    }

    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount, MC), currency);
    }

    public Money multiply(BigDecimal factor) {
        return new Money(amount.multiply(factor, MC), currency);
    }

    public boolean isPositive() {
        return amount.signum() > 0;
    }

    public void requireSameCurrency(Money other) {
        if (currency != other.currency) {
            throw new CurrencyMismatchException(currency, other.currency);
        }
    }
}
