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
 * frequency, expressed as a number of days (e.g. 1=daily, 30=monthly, 360=annual) so any
 * frequency is supported. Conversions follow the 30/360 convention.
 */
@Embeddable
public record Rate(
        @Column(name = "value") BigDecimal value,
        @Enumerated(EnumType.STRING) @Column(name = "type") RateType type,
        @Column(name = "capitalization") Integer capitalization
) {
    public Rate {
        if (value == null || type == null) {
            throw new IllegalArgumentException("Rate requires a value and a type");
        }
        if (type == RateType.NOMINAL && capitalization == null) {
            throw new MissingCapitalizationException();
        }
        if (capitalization != null && capitalization <= 0) {
            throw new IllegalArgumentException("capitalization (days) must be > 0");
        }
    }

    public Rate() {
        this(BigDecimal.ZERO, RateType.EFFECTIVE, null);
    }

    /** An effective annual rate (TEA). */
    public static Rate effective(BigDecimal effectiveAnnual) {
        return new Rate(effectiveAnnual, RateType.EFFECTIVE, null);
    }

    /** An effective rate expressed for a given period, in days (e.g. 30 = effective monthly, TEM). */
    public static Rate effective(BigDecimal effectivePeriodic, Integer periodDays) {
        return new Rate(effectivePeriodic, RateType.EFFECTIVE, periodDays);
    }

    /** A nominal annual rate with its capitalization frequency, in days. */
    public static Rate nominal(BigDecimal nominalAnnual, Integer capitalizationDays) {
        return new Rate(nominalAnnual, RateType.NOMINAL, capitalizationDays);
    }

    /**
     * Effective annual rate (TEA). Works for any period in days — {@code m = daysPerYear/period} may
     * be fractional (e.g. a 100-day period → 3.6). {@link FinancialMath#pow(BigDecimal, BigDecimal)}
     * uses an exact integer power when {@code m} is whole (the common 30/360 divisors), so those cases
     * are bit-for-bit unchanged; non-divisor periods fall to the fractional path.
     * <ul>
     *   <li>Nominal: {@code (1 + TNA/m)^m - 1}, m = daysPerYear/capitalizationDays.</li>
     *   <li>Effective: returned as-is when annual (no period); with a period, compounded up:
     *       {@code (1 + value)^(daysPerYear/period) - 1} (equals the value when period = 1 year).</li>
     * </ul>
     */
    public BigDecimal toEffectiveAnnual(int daysPerYear) {
        BigDecimal days = BigDecimal.valueOf(daysPerYear);
        if (type == RateType.EFFECTIVE) {
            if (capitalization == null) {
                return value;
            }
            BigDecimal periods = days.divide(BigDecimal.valueOf(capitalization), FinancialMath.MC);
            return FinancialMath.pow(BigDecimal.ONE.add(value), periods).subtract(BigDecimal.ONE);
        }
        BigDecimal m = days.divide(BigDecimal.valueOf(capitalization), FinancialMath.MC);
        BigDecimal base = BigDecimal.ONE.add(value.divide(m, FinancialMath.MC), FinancialMath.MC);
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
