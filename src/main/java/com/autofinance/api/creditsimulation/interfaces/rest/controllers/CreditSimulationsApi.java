package com.autofinance.api.creditsimulation.interfaces.rest.controllers;

import com.autofinance.api.creditsimulation.interfaces.rest.resources.GenerateSimulationResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.SimulationResource;
import com.autofinance.api.shared.interfaces.rest.ApiErrorSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * OpenAPI documentation for the Credit Simulation endpoints. Kept separate from the controller so the
 * implementation stays free of large doc annotations; {@code CreditSimulationsController} implements it
 * and SpringDoc merges these annotations with the Spring MVC mappings.
 */
@Tag(name = "Credit Simulations", description = "Generate and retrieve vehicle-credit quotations")
public interface CreditSimulationsApi {

    @Operation(summary = "Generate a credit simulation and return the stored snapshot")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Generated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SimulationResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid configuration / validation (see 'code')",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class))),
            @ApiResponse(responseCode = "422", description = "Valid request, but the schedule could not be computed (see 'code')",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class)))
    })
    ResponseEntity<SimulationResource> generate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GenerateSimulationResource.class),
                            examples = {
                                    @ExampleObject(name = "Plan francés simple (sin balloon)", value = """
                                            {
                                              "clientId": "22222222-2222-2222-2222-222222222222",
                                              "vehicleOfferId": "33333333-3333-3333-3333-333333333333",
                                              "rateValue": 0.20,
                                              "rateType": "EFFECTIVE",
                                              "capitalization": null,
                                              "ratePeriod": null,
                                              "initialPercentage": 0.20,
                                              "balloonPercentage": 0,
                                              "numberOfInstallments": 12,
                                              "frequencyDays": 30,
                                              "daysPerYear": 360,
                                              "gracePlan": ["NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE"],
                                              "costs": [],
                                              "costOfCapitalAnnual": 0.30
                                            }"""),
                                    @ExampleObject(name = "Compra Inteligente (balloon 30% + costos)", value = """
                                            {
                                              "clientId": "22222222-2222-2222-2222-222222222222",
                                              "vehicleOfferId": "33333333-3333-3333-3333-333333333333",
                                              "rateValue": 0.20,
                                              "rateType": "EFFECTIVE",
                                              "capitalization": null,
                                              "ratePeriod": null,
                                              "initialPercentage": 0.20,
                                              "balloonPercentage": 0.30,
                                              "numberOfInstallments": 12,
                                              "frequencyDays": 30,
                                              "daysPerYear": 360,
                                              "gracePlan": ["NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE"],
                                              "costs": [
                                                { "name": "portes", "value": 3.50, "basis": "FIXED", "timing": "PERIODIC", "embedded": false },
                                                { "name": "desgravamen", "value": 0.00049, "basis": "ON_BALANCE", "timing": "PERIODIC", "embedded": true }
                                              ],
                                              "costOfCapitalAnnual": 0.30
                                            }"""),
                                    @ExampleObject(name = "Tasa nominal con capitalización mensual", value = """
                                            {
                                              "clientId": "22222222-2222-2222-2222-222222222222",
                                              "vehicleOfferId": "33333333-3333-3333-3333-333333333333",
                                              "rateValue": 0.18,
                                              "rateType": "NOMINAL",
                                              "capitalization": 30,
                                              "ratePeriod": null,
                                              "initialPercentage": 0.20,
                                              "balloonPercentage": 0,
                                              "numberOfInstallments": 12,
                                              "frequencyDays": 30,
                                              "daysPerYear": 360,
                                              "gracePlan": ["NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE","NONE"],
                                              "costs": [],
                                              "costOfCapitalAnnual": 0.30
                                            }""")
                            }))
            GenerateSimulationResource resource);

    @Operation(summary = "Get a credit simulation by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SimulationResource.class))),
            @ApiResponse(responseCode = "404", description = "Not found in the current dealership", content = @Content)
    })
    ResponseEntity<SimulationResource> getById(UUID simulationId);

    @Operation(summary = "List a client's credit simulations")
    ResponseEntity<List<SimulationResource>> getByClient(UUID clientId);
}
