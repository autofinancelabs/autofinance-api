package com.autofinance.api.iam.interfaces.rest.controllers;

import com.autofinance.api.iam.interfaces.rest.resources.DealershipResource;
import com.autofinance.api.iam.interfaces.rest.resources.RegisterDealershipResource;
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

import java.util.UUID;

/**
 * OpenAPI documentation for the Dealership (IAM account) endpoints. Kept separate from the controller so
 * the implementation stays free of doc annotations; {@code DealershipsController} implements it and
 * SpringDoc merges these annotations with the Spring MVC mappings.
 */
@Tag(name = "Dealerships", description = "Register a dealership account and its first user (no tenant — the front door)")
public interface DealershipsApi {

    @Operation(summary = "Register a dealership account (and its first user) — returns the stored account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DealershipResource.class))),
            @ApiResponse(responseCode = "400", description = "Validation / malformed body (see 'code')",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class))),
            @ApiResponse(responseCode = "409", description = "RUC, email or username already in use (codes DUPLICATE_RUC / DUPLICATE_EMAIL / DUPLICATE_USERNAME)",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class)))
    })
    ResponseEntity<DealershipResource> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisterDealershipResource.class),
                            examples = @ExampleObject(name = "Registro de concesionaria", value = """
                                    {
                                      "name": "AutoNorte SAC",
                                      "ruc": "20123456789",
                                      "contactEmail": "ventas@autonorte.pe",
                                      "userEmail": "ana@autonorte.pe",
                                      "username": "ana",
                                      "password": "s3cr3t-pass"
                                    }""")))
            RegisterDealershipResource resource);

    @Operation(summary = "Get a dealership account by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DealershipResource.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    ResponseEntity<DealershipResource> getById(UUID dealershipId);
}
