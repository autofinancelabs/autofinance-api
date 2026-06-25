package com.autofinance.api.creditsimulation.domain.exceptions;

/** Raised when the generated schedule fails its balance post-conditions (last balance not ~0). */
public class ScheduleNotBalancedException extends RuntimeException {
    public ScheduleNotBalancedException(String message) {
        super(message);
    }
}
