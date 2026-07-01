package com.autofinance.api.creditsimulation.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

/**
 * Raised when a cost's configuration violates a domain invariant — e.g. an {@code embedded} cost that is
 * not a periodic rate over the balance ({@code ON_BALANCE}).
 */
public class InvalidCostConfigurationException extends DomainException {
    public InvalidCostConfigurationException(String message) {
        super(SimulationErrorCode.INVALID_COST_CONFIGURATION, message);
    }
}
