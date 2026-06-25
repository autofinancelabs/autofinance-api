package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import java.math.BigDecimal;

/** A rate: its value, type (NOMINAL/EFFECTIVE) and capitalization (null for EFFECTIVE). */
public record RateResource(BigDecimal value, String type, String capitalization) {
}
