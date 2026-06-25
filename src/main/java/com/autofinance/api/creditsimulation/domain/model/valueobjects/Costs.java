package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import com.autofinance.api.creditsimulation.domain.services.FinancialMath;

import java.math.BigDecimal;
import java.util.List;

/**
 * The set of costs of an operation, with the derivations the engine needs. Flexible: holds any
 * number of {@link Cost}s. Persistence mapping is deferred to the persistence slice.
 */
public record Costs(List<Cost> items) {
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

    /** Σ of the embedded ON_BALANCE rates (the TSD that joins {@code j} and grows the balloon). */
    public BigDecimal embeddedRate() {
        return items.stream()
                .filter(Cost::embedded)
                .map(Cost::value)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b, FinancialMath.MC));
    }

    /** The periodic costs to evaluate each period. */
    public List<Cost> periodic() {
        return items.stream().filter(c -> c.timing() == CostTiming.PERIODIC).toList();
    }

    /** Amount of one cost for a period, given the running balance and the constant sale price. */
    public BigDecimal amountFor(Cost cost, BigDecimal openingBalance, BigDecimal salePrice) {
        return switch (cost.basis()) {
            case FIXED -> cost.value();
            case ON_BALANCE -> openingBalance.multiply(cost.value(), FinancialMath.MC);
            case ON_SALE_PRICE -> salePrice.multiply(cost.value(), FinancialMath.MC);
        };
    }
}
