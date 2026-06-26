package com.autofinance.api.iam.interfaces.rest.controllers;

import com.autofinance.api.iam.domain.services.AuthenticationCommandService;
import com.autofinance.api.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.autofinance.api.iam.interfaces.rest.resources.SignInResource;
import com.autofinance.api.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.autofinance.api.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inbound REST adapter for authentication. Public endpoint (permitted in the security config). Thin:
 * resource → assembler → command service → assembler → resource. OpenAPI docs in {@link AuthenticationApi}.
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationCommandService authenticationCommandService;

    public AuthenticationController(AuthenticationCommandService authenticationCommandService) {
        this.authenticationCommandService = authenticationCommandService;
    }

    @Override
    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticatedUserResource> signIn(@Valid @RequestBody SignInResource resource) {
        var command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var authenticated = authenticationCommandService.handle(command);
        return ResponseEntity.ok(AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticated));
    }
}
