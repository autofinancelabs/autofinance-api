package com.autofinance.api.creditsimulation.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.ErrorCategory;
import com.autofinance.api.shared.domain.exceptions.ErrorCode;

/** Error catalog for the Credit Simulation context. The frontend reacts to {@link #code()}. */
public enum SimulationErrorCode implements ErrorCode {

    INVALID_SIMULATION_CONFIGURATION(ErrorCategory.VALIDATION),
    PERCENTAGE_OUT_OF_RANGE(ErrorCategory.VALIDATION),
    MISSING_CAPITALIZATION(ErrorCategory.VALIDATION),
    SCHEDULE_NOT_BALANCED(ErrorCategory.UNPROCESSABLE),
    IRR_NOT_BRACKETED(ErrorCategory.UNPROCESSABLE),
    CLIENT_NOT_FOUND(ErrorCategory.UNPROCESSABLE),
    VEHICLE_OFFER_NOT_FOUND(ErrorCategory.UNPROCESSABLE);

    private final ErrorCategory category;

    SimulationErrorCode(ErrorCategory category) {
        this.category = category;
    }

    @Override
    public String code() {
        return name();
    }

    @Override
    public ErrorCategory category() {
        return category;
    }
}
