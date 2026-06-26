package com.autofinance.api.creditsimulation.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

/** Raised when the generated schedule fails its balance post-conditions (last balance not ~0). */
public class ScheduleNotBalancedException extends DomainException {
    public ScheduleNotBalancedException(String message) {
        super(SimulationErrorCode.SCHEDULE_NOT_BALANCED, message);
    }
}
