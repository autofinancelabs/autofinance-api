package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import java.math.BigDecimal;

/** A monetary amount with its currency code. */
public record MoneyResource(BigDecimal amount, String currency) {
}
