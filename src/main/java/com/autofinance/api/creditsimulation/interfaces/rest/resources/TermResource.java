package com.autofinance.api.creditsimulation.interfaces.rest.resources;

/** Loan term description. */
public record TermResource(int numberOfInstallments, int frequencyDays, int installmentsPerYear, int daysPerYear) {
}
