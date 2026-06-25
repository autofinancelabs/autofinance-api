package com.autofinance.api.creditsimulation.domain.model.valueobjects;

/** Lifecycle state of a credit simulation. */
public enum SimulationState {
    DRAFT,
    CONFIGURED,
    GENERATED,
    SAVED,
    REOPENED
}
