package com.autofinance.api.creditsimulation.domain.model.valueobjects;

/** Grace of a period: none (S), total (T, capitalizes interest) or partial (P, pays interest only). */
public enum GraceType {
    NONE,
    TOTAL,
    PARTIAL
}
