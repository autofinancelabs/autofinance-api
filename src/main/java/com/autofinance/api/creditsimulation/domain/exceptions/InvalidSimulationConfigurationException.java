package com.autofinance.api.creditsimulation.domain.exceptions;

/** Raised when a credit simulation's cross-field configuration invariants are violated. */
public class InvalidSimulationConfigurationException extends RuntimeException {
    public InvalidSimulationConfigurationException(String message) {
        super(message);
    }
}
