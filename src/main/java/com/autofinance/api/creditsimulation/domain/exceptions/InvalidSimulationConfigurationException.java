package com.autofinance.api.creditsimulation.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

/** Raised when a credit simulation's cross-field configuration invariants are violated. */
public class InvalidSimulationConfigurationException extends DomainException {
    public InvalidSimulationConfigurationException(String message) {
        super(SimulationErrorCode.INVALID_SIMULATION_CONFIGURATION, message);
    }
}
