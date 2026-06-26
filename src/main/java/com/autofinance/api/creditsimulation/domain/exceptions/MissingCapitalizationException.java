package com.autofinance.api.creditsimulation.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

/** Raised when a nominal rate is created without a capitalization frequency. */
public class MissingCapitalizationException extends DomainException {
    public MissingCapitalizationException() {
        super(SimulationErrorCode.MISSING_CAPITALIZATION, "A nominal rate requires a capitalization frequency");
    }
}
