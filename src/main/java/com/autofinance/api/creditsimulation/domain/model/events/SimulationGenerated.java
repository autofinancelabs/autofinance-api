package com.autofinance.api.creditsimulation.domain.model.events;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.DealershipId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Raised when a simulation's schedule and indicators have been computed. Internal in v1. */
@Getter
public class SimulationGenerated extends ApplicationEvent {

    private final SimulationId simulationId;
    private final DealershipId dealershipId;
    private final int rowCount;

    public SimulationGenerated(SimulationId simulationId, DealershipId dealershipId, int rowCount) {
        super(simulationId);
        this.simulationId = simulationId;
        this.dealershipId = dealershipId;
        this.rowCount = rowCount;
    }
}
