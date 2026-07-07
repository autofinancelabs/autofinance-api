package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.services.FinancialMath;

import java.math.BigDecimal;
import java.util.List;

/**
 * The set of costs of an operation, with the derivations the engine needs. Flexible: holds any
 * number of {@link Cost}s. Persistence mapping is deferred to the persistence slice.
 */
public record Costs(List<Cost> items) {

    /** A month in the 30/360 convention: the base period of an ON_BALANCE (desgravamen/TSD) rate. */
    private static final BigDecimal MONTHLY_DAYS = BigDecimal.valueOf(30);

    public Costs {
        if (items == null) {
            throw new IllegalArgumentException("Costs requires a (possibly empty) list");
        }
        items = List.copyOf(items);
    }

    public static Costs none() {
        return new Costs(List.of());
    }

    /** Σ of the INITIAL costs (financed into the loan). */
    public BigDecimal initialTotal() {
        return items.stream()
                .filter(c -> c.timing() == CostTiming.INITIAL)
                .map(Cost::value)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b, FinancialMath.MC));
    }

    /**
     * Σ of the embedded ON_BALANCE rates, scaled to the payment period (the per-period TSD that joins
     * {@code j} and grows the balloon). Embedded rates are quoted per month (30 days).
     */
    public BigDecimal embeddedRate(int frequencyDays) {
        return items.stream()
                .filter(Cost::embedded)
                .map(c -> scaleToPeriod(c.value(), frequencyDays, MONTHLY_DAYS))
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b, FinancialMath.MC));
    }

    /** The periodic costs to evaluate each period. */
    public List<Cost> periodic() {
        return items.stream().filter(c -> c.timing() == CostTiming.PERIODIC).toList();
    }

    /**
     * Amount of one cost for a payment period, given the running balance and the constant sale price.
     * Rate-based costs are scaled from their quoted base to the payment period: an ON_BALANCE rate
     * (desgravamen/TSD) is quoted per month (× frequencyDays/30); an ON_SALE_PRICE rate (riesgo/TSR)
     * is quoted per year (× frequencyDays/daysPerYear). FIXED costs are amounts already per period.
     */
    public BigDecimal amountFor(Cost cost, BigDecimal openingBalance, BigDecimal salePrice,
                                int frequencyDays, int daysPerYear) {
        return switch (cost.basis()) {
            case FIXED -> cost.value();
            case ON_BALANCE -> openingBalance.multiply(
                    scaleToPeriod(cost.value(), frequencyDays, MONTHLY_DAYS), FinancialMath.MC);
            case ON_SALE_PRICE -> salePrice.multiply(
                    scaleToPeriod(cost.value(), frequencyDays, BigDecimal.valueOf(daysPerYear)), FinancialMath.MC);
        };
    }

    /** Scales a rate quoted per {@code baseDays} to the payment period of {@code frequencyDays} (linear). */
    private static BigDecimal scaleToPeriod(BigDecimal rate, int frequencyDays, BigDecimal baseDays) {
        return rate.multiply(BigDecimal.valueOf(frequencyDays), FinancialMath.MC)
                .divide(baseDays, FinancialMath.MC);
    }
}
