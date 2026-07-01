package com.autofinance.api.clients.interfaces.rest.controllers;

import com.autofinance.api.clients.interfaces.rest.resources.ClientResource;
import com.autofinance.api.clients.interfaces.rest.resources.RegisterClientResource;
import com.autofinance.api.clients.interfaces.rest.resources.UpdateClientResource;
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
 * OpenAPI documentation for the Clients endpoints. Kept separate from the controller so the
 * implementation stays free of doc annotations; {@code ClientsController} implements it and SpringDoc
 * merges these annotations with the Spring MVC mappings.
 */
@Tag(name = "Clients", description = "Register and maintain the dealership's clients (debtors)")
public interface ClientsApi {

    @Operation(summary = "Register a client and return the stored snapshot")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registered",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientResource.class))),
            @ApiResponse(responseCode = "400", description = "Validation / malformed body (see 'code')",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class))),
            @ApiResponse(responseCode = "409", description = "A client with the same identity document already exists in the dealership (code DUPLICATE_CLIENT_DOCUMENT)",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class)))
    })
    ResponseEntity<ClientResource> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisterClientResource.class),
                            examples = {
                                    @ExampleObject(name = "Cliente con DNI", value = """
                                            {
                                              "documentType": "DNI",
                                              "documentNumber": "12345678",
                                              "firstName": "Ana María",
                                              "lastName": "Pérez García",
                                              "email": "ana@example.com",
                                              "phone": "+51 999 888 777",
                                              "address": "Av. Lima 123"
                                            }"""),
                                    @ExampleObject(name = "Cliente con CE (sin contacto)", value = """
                                            {
                                              "documentType": "CE",
                                              "documentNumber": "001234567",
                                              "firstName": "Carlos",
                                              "lastName": "Rodríguez",
                                              "email": null,
                                              "phone": null,
                                              "address": null
                                            }""")
                            }))
            RegisterClientResource resource);

    @Operation(summary = "Update a client's name and contact data and return the stored snapshot")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientResource.class))),
            @ApiResponse(responseCode = "400", description = "Validation / malformed body (see 'code')",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class))),
            @ApiResponse(responseCode = "404", description = "Not found in the current dealership", content = @Content)
    })
    ResponseEntity<ClientResource> update(UUID clientId, UpdateClientResource resource);

    @Operation(summary = "Get a client by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientResource.class))),
            @ApiResponse(responseCode = "404", description = "Not found in the current dealership", content = @Content)
    })
    ResponseEntity<ClientResource> getById(UUID clientId);

    @Operation(summary = "List the current dealership's clients")
    ResponseEntity<List<ClientResource>> list();
}
