package com.autofinance.api.creditsimulation.domain.exceptions;

import java.math.BigDecimal;

/** Raised when a percentage is not within the range [0, 1). */
public class PercentageOutOfRangeException extends RuntimeException {
    public PercentageOutOfRangeException(BigDecimal value) {
        super("Percentage must be in [0, 1) but was %s".formatted(value));
    }
}
