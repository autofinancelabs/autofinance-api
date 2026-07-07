package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.exceptions.MissingCapitalizationException;
import com.autofinance.api.creditsimulation.domain.services.FinancialMath;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

/**
 * An interest rate with its conversion behavior. Two orthogonal periods, both in days
 * ({@code null} = annual):
 * <ul>
 *   <li>{@code ratePeriod} — the period the {@code value} is quoted over (both types).</li>
 *   <li>{@code capitalization} — the compounding frequency (nominal only; required).</li>
 * </ul>
 * e.g. {@code 1}=daily, {@code 30}=monthly, {@code 360}=annual, or any value like {@code 100}.
 * Conversions follow the 30/360 convention.
 */
@Embeddable
public record Rate(
        @Column(name = "value") BigDecimal value,
        @Enumerated(EnumType.STRING) @Column(name = "type") RateType type,
        @Column(name = "capitalization") Integer capitalization,
        @Column(name = "rate_period") Integer ratePeriod
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
        if (ratePeriod != null && ratePeriod <= 0) {
            throw new IllegalArgumentException("rate period (days) must be > 0");
        }
    }

    public Rate() {
        this(BigDecimal.ZERO, RateType.EFFECTIVE, null, null);
    }

    /** An effective annual rate (TEA). */
    public static Rate effective(BigDecimal effectiveAnnual) {
        return new Rate(effectiveAnnual, RateType.EFFECTIVE, null, null);
    }

    /** An effective rate expressed for a given period, in days (e.g. 30 = effective monthly, TEM). */
    public static Rate effective(BigDecimal effectivePeriodic, Integer periodDays) {
        return new Rate(effectivePeriodic, RateType.EFFECTIVE, null, periodDays);
    }

    /** A nominal annual rate with its capitalization frequency, in days. */
    public static Rate nominal(BigDecimal nominalAnnual, Integer capitalizationDays) {
        return new Rate(nominalAnnual, RateType.NOMINAL, capitalizationDays, null);
    }

    /**
     * A nominal rate quoted for a given period (in days), with its capitalization frequency (in days).
     * {@code periodDays == null} means the rate is quoted annually (TNA).
     */
    public static Rate nominal(BigDecimal nominalPeriodic, Integer capitalizationDays, Integer periodDays) {
        return new Rate(nominalPeriodic, RateType.NOMINAL, capitalizationDays, periodDays);
    }

    /**
     * Effective annual rate (TEA). Works for any period in days — the exponents may be fractional
     * (e.g. a 100-day period → 3.6). {@link FinancialMath#pow(BigDecimal, BigDecimal)} uses an exact
     * integer power when the exponent is whole (the common 30/360 divisors), so those cases are
     * bit-for-bit unchanged; non-divisor periods fall to the fractional path. {@code R} is the rate
     * period (days), defaulting to the year when {@code ratePeriod} is null.
     * <ul>
     *   <li>Effective: {@code (1 + value)^(daysPerYear/R) - 1} (equals the value when R = 1 year).</li>
     *   <li>Nominal: {@code (1 + nominalAnnual/m)^m - 1}, m = daysPerYear/capitalization,
     *       nominalAnnual = value × (daysPerYear/R) (equals {@code (1 + value·C/R)^(D/C) - 1}).</li>
     * </ul>
     */
    public BigDecimal toEffectiveAnnual(int daysPerYear) {
        BigDecimal days = BigDecimal.valueOf(daysPerYear);
        BigDecimal periodDays = ratePeriod == null ? days : BigDecimal.valueOf(ratePeriod);
        if (type == RateType.EFFECTIVE) {
            if (ratePeriod == null) {
                return value;
            }
            BigDecimal periods = days.divide(periodDays, FinancialMath.MC);
            return FinancialMath.pow(BigDecimal.ONE.add(value), periods).subtract(BigDecimal.ONE);
        }
        BigDecimal m = days.divide(BigDecimal.valueOf(capitalization), FinancialMath.MC);
        BigDecimal nominalAnnual = value.multiply(days.divide(periodDays, FinancialMath.MC), FinancialMath.MC);
        BigDecimal base = BigDecimal.ONE.add(nominalAnnual.divide(m, FinancialMath.MC), FinancialMath.MC);
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
