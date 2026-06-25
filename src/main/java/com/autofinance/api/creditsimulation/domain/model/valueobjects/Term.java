package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/** Loan term: number of installments and the payment frequency, with derived periods-per-year. */
@Embeddable
public record Term(
        @Column(name = "number_of_installments") int numberOfInstallments,
        @Column(name = "frequency_days") int frequencyDays,
        @Column(name = "installments_per_year") int installmentsPerYear,
        @Column(name = "days_per_year") int daysPerYear
) {
    public Term {
        if (numberOfInstallments < 1) {
            throw new IllegalArgumentException("numberOfInstallments must be >= 1");
        }
        if (frequencyDays <= 0) {
            throw new IllegalArgumentException("frequencyDays must be > 0");
        }
        if (daysPerYear <= 0) {
            throw new IllegalArgumentException("daysPerYear must be > 0");
        }
        if (installmentsPerYear != daysPerYear / frequencyDays) {
            throw new IllegalArgumentException("installmentsPerYear must equal daysPerYear / frequencyDays");
        }
    }

    public Term() {
        this(1, 30, 12, 360);
    }

    public static Term of(int numberOfInstallments, int frequencyDays, int daysPerYear) {
        return new Term(numberOfInstallments, frequencyDays, daysPerYear / frequencyDays, daysPerYear);
    }
}
