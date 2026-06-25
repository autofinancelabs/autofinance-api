package com.autofinance.api.creditsimulation.domain.model.valueobjects;

/** When a cost is charged. */
public enum CostTiming {
    /** One-off, financed into the loan amount. */
    INITIAL,
    /** Charged every period (paid even during grace). */
    PERIODIC
}
