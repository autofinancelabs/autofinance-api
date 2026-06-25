package com.autofinance.api.creditsimulation.domain.exceptions;

/** Raised when a nominal rate is created without a capitalization frequency. */
public class MissingCapitalizationException extends RuntimeException {
    public MissingCapitalizationException() {
        super("A nominal rate requires a capitalization frequency");
    }
}
