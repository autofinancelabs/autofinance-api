package com.autofinance.api.creditsimulation.domain.model.valueobjects;

/**
 * Capitalization frequency for a nominal rate, expressed in days (30/360 convention).
 * {@code periodsPerYear} yields {@code m = daysPerYear / days}.
 */
public enum Capitalization {
    DAILY(1),
    MONTHLY(30),
    QUARTERLY(90),
    SEMIANNUAL(180),
    ANNUAL(360);

    private final int days;

    Capitalization(int days) {
        this.days = days;
    }

    public int days() {
        return days;
    }

    public int periodsPerYear(int daysPerYear) {
        return daysPerYear / days;
    }
}
