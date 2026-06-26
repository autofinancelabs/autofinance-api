package com.autofinance.api.clients.interfaces.rest.controllers;

import com.autofinance.api.clients.domain.model.queries.GetAllClientsQuery;
import com.autofinance.api.clients.domain.model.queries.GetClientByIdQuery;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.clients.domain.services.ClientCommandService;
import com.autofinance.api.clients.domain.services.ClientQueryService;
import com.autofinance.api.clients.interfaces.rest.resources.ClientResource;
import com.autofinance.api.clients.interfaces.rest.resources.RegisterClientResource;
import com.autofinance.api.clients.interfaces.rest.resources.UpdateClientResource;
import com.autofinance.api.clients.interfaces.rest.transform.ClientResourceFromEntityAssembler;
import com.autofinance.api.clients.interfaces.rest.transform.RegisterClientCommandFromResourceAssembler;
import com.autofinance.api.clients.interfaces.rest.transform.UpdateClientCommandFromResourceAssembler;
import com.autofinance.api.shared.interfaces.rest.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Inbound REST adapter for the Clients context. The dealership (tenant) is taken from the
 * {@code X-Dealership-Id} header on writes that need it; reads and updates are scoped by the tenant
 * filter. Thin: resource → assembler → command/query services → assembler → resource (re-querying after
 * a write so the response reflects stored state). OpenAPI docs live in {@link ClientsApi}.
 */
@RestController
@RequestMapping(value = "/api/v1/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientsController implements ClientsApi {

    private final ClientCommandService commandService;
    private final ClientQueryService queryService;
    private final CurrentUser currentUser;

    public ClientsController(ClientCommandService commandService, ClientQueryService queryService,
                            CurrentUser currentUser) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.currentUser = currentUser;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResource> register(@Valid @RequestBody RegisterClientResource resource) {
        var command = RegisterClientCommandFromResourceAssembler.toCommandFromResource(
                currentUser.dealershipId(), resource);
        var id = commandService.handle(command);
        return queryService.handle(new GetClientByIdQuery(id))
                .map(client -> new ResponseEntity<>(
                        ClientResourceFromEntityAssembler.toResourceFromEntity(client), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping(value = "/{clientId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientResource> update(
            @PathVariable UUID clientId,
            @Valid @RequestBody UpdateClientResource resource) {
        var command = UpdateClientCommandFromResourceAssembler.toCommandFromResource(clientId, resource);
        return commandService.handle(command)
                .flatMap(id -> queryService.handle(new GetClientByIdQuery(id)))
                .map(client -> ResponseEntity.ok(
                        ClientResourceFromEntityAssembler.toResourceFromEntity(client)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/{clientId}")
    public ResponseEntity<ClientResource> getById(@PathVariable UUID clientId) {
        return queryService.handle(new GetClientByIdQuery(new ClientId(clientId)))
                .map(client -> ResponseEntity.ok(
                        ClientResourceFromEntityAssembler.toResourceFromEntity(client)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ClientResource>> list() {
        List<ClientResource> resources = queryService.handle(new GetAllClientsQuery())
                .stream()
                .map(ClientResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
