package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import java.util.Collections;
import java.util.List;

/**
 * The per-period grace plan: one {@link GraceType} per installment.
 * <p>
 * Persistence mapping (the {@code grace_period} child table via {@code @ElementCollection})
 * is deferred to the persistence slice; here it is a pure domain value object.
 */
public record GraceConfiguration(List<GraceType> periods) {
    public GraceConfiguration {
        if (periods == null || periods.isEmpty()) {
            throw new IllegalArgumentException("Grace plan must have at least one period");
        }
        if (periods.stream().anyMatch(g -> g == null)) {
            throw new IllegalArgumentException("Grace plan cannot contain null entries");
        }
        // Grace is defined at the start of the operation: no T/P period may follow an ordinary one.
        boolean ordinaryStarted = false;
        for (GraceType g : periods) {
            if (g == GraceType.NONE) {
                ordinaryStarted = true;
            } else if (ordinaryStarted) {
                throw new IllegalArgumentException(
                        "Grace periods must be contiguous at the start of the schedule");
            }
        }
        periods = List.copyOf(periods);
    }

    public int size() {
        return periods.size();
    }

    public int totalCount() {
        return (int) periods.stream().filter(g -> g == GraceType.TOTAL).count();
    }

    public int partialCount() {
        return (int) periods.stream().filter(g -> g == GraceType.PARTIAL).count();
    }

    /** Grace type at a 1-based period index. */
    public GraceType at(int period) {
        return periods.get(period - 1);
    }

    public static GraceConfiguration none(int numberOfInstallments) {
        return new GraceConfiguration(Collections.nCopies(numberOfInstallments, GraceType.NONE));
    }
}
