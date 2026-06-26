package com.autofinance.api.iam.interfaces.rest.controllers;

import com.autofinance.api.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.autofinance.api.iam.interfaces.rest.resources.SignInResource;
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

/** OpenAPI documentation for authentication. Implemented by {@code AuthenticationController}. */
@Tag(name = "Authentication", description = "Sign in to obtain a bearer token")
public interface AuthenticationApi {

    @Operation(summary = "Sign in with username/email + password and obtain a bearer token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthenticatedUserResource.class))),
            @ApiResponse(responseCode = "400", description = "Validation / malformed body (see 'code')",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials (code INVALID_CREDENTIALS)",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ApiErrorSchema.class)))
    })
    ResponseEntity<AuthenticatedUserResource> signIn(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SignInResource.class),
                            examples = @ExampleObject(name = "Sign in", value = """
                                    {
                                      "identifier": "ana",
                                      "password": "s3cr3t-pass"
                                    }""")))
            SignInResource resource);
}
