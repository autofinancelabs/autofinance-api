package com.autofinance.api.shared.domain.exceptions;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;

/** Raised when arithmetic is attempted across two different currencies. */
public class CurrencyMismatchException extends RuntimeException {
    public CurrencyMismatchException(Currency a, Currency b) {
        super("Cannot operate on amounts of different currencies: %s and %s".formatted(a, b));
    }
}
