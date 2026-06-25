package com.autofinance.api.creditsimulation.interfaces.rest.controllers;

import com.autofinance.api.creditsimulation.domain.model.queries.GetSimulationByIdQuery;
import com.autofinance.api.creditsimulation.domain.model.queries.GetSimulationsByClientIdQuery;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ClientId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import com.autofinance.api.creditsimulation.domain.services.CreditSimulationCommandService;
import com.autofinance.api.creditsimulation.domain.services.CreditSimulationQueryService;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.GenerateSimulationResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.SimulationResource;
import com.autofinance.api.creditsimulation.interfaces.rest.transform.GenerateSimulationCommandFromResourceAssembler;
import com.autofinance.api.creditsimulation.interfaces.rest.transform.SimulationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Inbound REST adapter for the Credit Simulation context. The dealership (tenant) is taken from the
 * {@code X-Dealership-Id} header; the body never carries it. Thin: resource → assembler → command/query
 * services → assembler → resource (re-querying after a write so the response reflects stored state).
 */
@RestController
@RequestMapping(value = "/api/v1/credit-simulations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Credit Simulations", description = "Generate and retrieve vehicle-credit quotations")
public class CreditSimulationsController {

    private final CreditSimulationCommandService commandService;
    private final CreditSimulationQueryService queryService;

    public CreditSimulationsController(CreditSimulationCommandService commandService,
                                       CreditSimulationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generate a credit simulation and return the stored snapshot")
    public ResponseEntity<SimulationResource> generate(
            @RequestHeader("X-Dealership-Id") UUID dealershipId,
            @Valid @RequestBody GenerateSimulationResource resource) {
        var command = GenerateSimulationCommandFromResourceAssembler.toCommandFromResource(dealershipId, resource);
        var simulationId = commandService.handle(command);
        return queryService.handle(new GetSimulationByIdQuery(simulationId))
                .map(simulation -> new ResponseEntity<>(
                        SimulationResourceFromEntityAssembler.toResourceFromEntity(simulation), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{simulationId}")
    @Operation(summary = "Get a credit simulation by id")
    public ResponseEntity<SimulationResource> getById(@PathVariable UUID simulationId) {
        return queryService.handle(new GetSimulationByIdQuery(new SimulationId(simulationId)))
                .map(simulation -> ResponseEntity.ok(
                        SimulationResourceFromEntityAssembler.toResourceFromEntity(simulation)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(params = "clientId")
    @Operation(summary = "List a client's credit simulations")
    public ResponseEntity<List<SimulationResource>> getByClient(@RequestParam UUID clientId) {
        List<SimulationResource> resources = queryService.handle(new GetSimulationsByClientIdQuery(new ClientId(clientId)))
                .stream()
                .map(SimulationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
