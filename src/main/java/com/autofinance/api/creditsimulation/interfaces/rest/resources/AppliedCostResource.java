package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import java.math.BigDecimal;

/** A cost applied in a schedule row: its name and amount. */
public record AppliedCostResource(String name, BigDecimal amount) {
}
