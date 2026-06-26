package com.autofinance.api.creditsimulation.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

import java.math.BigDecimal;

/** Raised when a percentage is not within the range [0, 1). */
public class PercentageOutOfRangeException extends DomainException {
    public PercentageOutOfRangeException(BigDecimal value) {
        super(SimulationErrorCode.PERCENTAGE_OUT_OF_RANGE,
                "Percentage must be in [0, 1) but was %s".formatted(value));
    }
}
