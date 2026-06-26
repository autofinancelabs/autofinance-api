package com.autofinance.api.vehicleoffers.interfaces.rest.resources;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/** A monetary amount with its currency code. */
public record MoneyResource(BigDecimal amount, @Schema(implementation = Currency.class) String currency) {
}
