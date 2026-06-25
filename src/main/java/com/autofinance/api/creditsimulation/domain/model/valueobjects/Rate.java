package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.exceptions.MissingCapitalizationException;
import com.autofinance.api.creditsimulation.domain.services.FinancialMath;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

/**
 * An interest rate with its conversion behavior. Nominal rates require a capitalization
 * frequency. Conversions follow the 30/360 convention.
 */
@Embeddable
public record Rate(
        @Column(name = "value") BigDecimal value,
        @Enumerated(EnumType.STRING) @Column(name = "type") RateType type,
        @Enumerated(EnumType.STRING) @Column(name = "capitalization") Capitalization capitalization
) {
    public Rate {
        if (value == null || type == null) {
            throw new IllegalArgumentException("Rate requires a value and a type");
        }
        if (type == RateType.NOMINAL && capitalization == null) {
            throw new MissingCapitalizationException();
        }
    }

    public Rate() {
        this(BigDecimal.ZERO, RateType.EFFECTIVE, null);
    }

    public static Rate effective(BigDecimal effectiveAnnual) {
        return new Rate(effectiveAnnual, RateType.EFFECTIVE, null);
    }

    public static Rate nominal(BigDecimal nominalAnnual, Capitalization capitalization) {
        return new Rate(nominalAnnual, RateType.NOMINAL, capitalization);
    }

    /** Effective annual rate (TEA). For nominal: {@code (1 + TNA/m)^m - 1}, m = daysPerYear/capDays. */
    public BigDecimal toEffectiveAnnual(int daysPerYear) {
        if (type == RateType.EFFECTIVE) {
            return value;
        }
        int m = capitalization.periodsPerYear(daysPerYear);
        BigDecimal base = BigDecimal.ONE.add(value.divide(BigDecimal.valueOf(m), FinancialMath.MC));
        return FinancialMath.pow(base, m).subtract(BigDecimal.ONE);
    }

    /** Effective rate of the payment period (TEP/TEM): {@code (1 + TEA)^(frequencyDays/daysPerYear) - 1}. */
    public BigDecimal toPeriodicRate(int frequencyDays, int daysPerYear) {
        BigDecimal tea = toEffectiveAnnual(daysPerYear);
        BigDecimal exponent = BigDecimal.valueOf(frequencyDays)
                .divide(BigDecimal.valueOf(daysPerYear), FinancialMath.MC);
        return FinancialMath.pow(BigDecimal.ONE.add(tea), exponent).subtract(BigDecimal.ONE);
    }
}
