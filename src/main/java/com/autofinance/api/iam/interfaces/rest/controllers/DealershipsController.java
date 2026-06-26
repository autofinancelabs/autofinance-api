package com.autofinance.api.iam.interfaces.rest.controllers;

import com.autofinance.api.iam.domain.model.queries.GetDealershipByIdQuery;
import com.autofinance.api.iam.domain.model.valueobjects.DealershipId;
import com.autofinance.api.iam.domain.services.IamCommandService;
import com.autofinance.api.iam.domain.services.IamQueryService;
import com.autofinance.api.iam.interfaces.rest.resources.DealershipResource;
import com.autofinance.api.iam.interfaces.rest.resources.RegisterDealershipResource;
import com.autofinance.api.iam.interfaces.rest.transform.DealershipResourceFromEntityAssembler;
import com.autofinance.api.iam.interfaces.rest.transform.RegisterDealershipCommandFromResourceAssembler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Inbound REST adapter for IAM dealership registration. Registration is the front door — it creates the
 * dealership (tenant), so it takes NO {@code X-Dealership-Id} header. IAM is global (no {@code @TenantId}).
 * Thin: resource → assembler → command/query services → assembler → resource. OpenAPI docs live in
 * {@link DealershipsApi}.
 */
@RestController
@RequestMapping(value = "/api/v1/dealerships", produces = MediaType.APPLICATION_JSON_VALUE)
public class DealershipsController implements DealershipsApi {

    private final IamCommandService commandService;
    private final IamQueryService queryService;

    public DealershipsController(IamCommandService commandService, IamQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DealershipResource> register(@Valid @RequestBody RegisterDealershipResource resource) {
        var command = RegisterDealershipCommandFromResourceAssembler.toCommandFromResource(resource);
        var id = commandService.handle(command);
        return queryService.handle(new GetDealershipByIdQuery(id))
                .map(dealership -> new ResponseEntity<>(
                        DealershipResourceFromEntityAssembler.toResourceFromEntity(dealership), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/{dealershipId}")
    public ResponseEntity<DealershipResource> getById(@PathVariable UUID dealershipId) {
        return queryService.handle(new GetDealershipByIdQuery(new DealershipId(dealershipId)))
                .map(dealership -> ResponseEntity.ok(
                        DealershipResourceFromEntityAssembler.toResourceFromEntity(dealership)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
