package com.autofinance.api.creditsimulation.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

import java.util.UUID;

/** Raised when a simulation references a client that does not exist in the current dealership. */
public class ReferencedClientNotFoundException extends DomainException {
    public ReferencedClientNotFoundException(UUID clientId) {
        super(SimulationErrorCode.CLIENT_NOT_FOUND, "Client %s does not exist".formatted(clientId));
    }
}
