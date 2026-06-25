package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

/**
 * A configurable cost or insurance an entity can charge. Flexible by design: any number of costs
 * can be defined per operation. {@code value} is an amount (FIXED) or a rate (ON_BALANCE/ON_SALE_PRICE).
 * <p>
 * {@code embedded} marks a credit-life-insurance-style cost whose rate joins the installment rate
 * {@code j = i + rate} and capitalizes into the balloon; it is only valid for a periodic balance rate.
 */
@Embeddable
public record Cost(
        @Column(name = "name") String name,
        @Column(name = "value") BigDecimal value,
        @Enumerated(EnumType.STRING) @Column(name = "basis") CostBasis basis,
        @Enumerated(EnumType.STRING) @Column(name = "timing") CostTiming timing,
        @Column(name = "embedded") boolean embedded
) {
    public Cost {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Cost requires a name");
        }
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException("Cost value must be >= 0");
        }
        if (basis == null || timing == null) {
            throw new IllegalArgumentException("Cost requires a basis and a timing");
        }
        if (embedded && (basis != CostBasis.ON_BALANCE || timing != CostTiming.PERIODIC)) {
            throw new IllegalArgumentException("An embedded cost must be a periodic ON_BALANCE rate");
        }
    }

    public Cost() {
        this("", BigDecimal.ZERO, CostBasis.FIXED, CostTiming.PERIODIC, false);
    }

    public static Cost initial(String name, BigDecimal amount) {
        return new Cost(name, amount, CostBasis.FIXED, CostTiming.INITIAL, false);
    }

    public static Cost fixedPeriodic(String name, BigDecimal amount) {
        return new Cost(name, amount, CostBasis.FIXED, CostTiming.PERIODIC, false);
    }

    public static Cost onBalance(String name, BigDecimal rate, boolean embedded) {
        return new Cost(name, rate, CostBasis.ON_BALANCE, CostTiming.PERIODIC, embedded);
    }

    public static Cost onSalePrice(String name, BigDecimal rate) {
        return new Cost(name, rate, CostBasis.ON_SALE_PRICE, CostTiming.PERIODIC, false);
    }
}
