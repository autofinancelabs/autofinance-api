package com.autofinance.api.creditsimulation.domain.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Numeric helpers for the calculation engine. Centralizes the precision policy and the
 * power operation — the accuracy linchpin of the whole engine.
 * <p>
 * {@link BigDecimal#pow(int)} only supports integer exponents. For fractional exponents
 * (e.g. {@code ^(30/360)}, {@code ^(1/12)}) we drop to {@code double} via {@link Math#pow}
 * and lift back to {@code BigDecimal}. The dataset tolerances (1e-6 on rates, ±0.05 on money)
 * are far looser than double's ~15 significant digits, so the precision loss is immaterial.
 */
public final class FinancialMath {

    /** Internal working precision; round only for display. */
    public static final MathContext MC = new MathContext(34, RoundingMode.HALF_UP);

    private FinancialMath() {
    }

    /** {@code base^exponent} for integer exponents (negative via reciprocal), pure BigDecimal. */
    public static BigDecimal pow(BigDecimal base, int exponent) {
        if (exponent >= 0) {
            return base.pow(exponent, MC);
        }
        return BigDecimal.ONE.divide(base.pow(-exponent, MC), MC);
    }

    /** {@code base^exponent}; integer fast-path, otherwise a double hop for fractional exponents. */
    public static BigDecimal pow(BigDecimal base, BigDecimal exponent) {
        BigDecimal stripped = exponent.stripTrailingZeros();
        if (stripped.scale() <= 0) {
            return pow(base, stripped.intValueExact());
        }
        double result = Math.pow(base.doubleValue(), exponent.doubleValue());
        return new BigDecimal(result, MC);
    }
}
