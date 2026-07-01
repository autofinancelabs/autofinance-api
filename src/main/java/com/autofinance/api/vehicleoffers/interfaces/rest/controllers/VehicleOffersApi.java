package com.autofinance.api.vehicleoffers.interfaces.rest.controllers;

import com.autofinance.api.shared.interfaces.rest.ApiErrorSchema;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.RegisterVehicleOfferResource;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.UpdateVehicleOfferResource;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.VehicleOfferResource;
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
 * OpenAPI documentation for the Vehicle Offers endpoints. Kept separate from the controller so the
 * implementation stays free of doc annotations; {@code VehicleOffersController} implements it and
 * SpringDoc merges these annotations with the Spring MVC mappings.
 */
@Tag(name = "Vehicle Offers", description = "Register and maintain vehicle offers used as financing base")
public interface VehicleOffersApi {

    @Operation(summary = "Register a vehicle offer and return the stored snapshot")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VehicleOfferResource.class))),
            @ApiResponse(responseCode = "400", description = "Validation / malformed body (see 'code')",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class)))
    })
    ResponseEntity<VehicleOfferResource> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisterVehicleOfferResource.class),
                            examples = {
                                    @ExampleObject(name = "Oferta vehicular", value = """
                                            {
                                              "make": "Toyota",
                                              "model": "Corolla",
                                              "year": 2024,
                                              "salePrice": 50000.00,
                                              "currency": "PEN"
                                            }""")
                            }))
            RegisterVehicleOfferResource resource);

    @Operation(summary = "Update a vehicle offer and return the stored snapshot")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VehicleOfferResource.class))),
            @ApiResponse(responseCode = "400", description = "Validation / malformed body (see 'code')",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class))),
            @ApiResponse(responseCode = "404", description = "Not found in the current dealership", content = @Content)
    })
    ResponseEntity<VehicleOfferResource> update(UUID vehicleOfferId, UpdateVehicleOfferResource resource);

    @Operation(summary = "Get a vehicle offer by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VehicleOfferResource.class))),
            @ApiResponse(responseCode = "404", description = "Not found in the current dealership", content = @Content)
    })
    ResponseEntity<VehicleOfferResource> getById(UUID vehicleOfferId);

    @Operation(summary = "List the current dealership's vehicle offers")
    ResponseEntity<List<VehicleOfferResource>> list();
}
