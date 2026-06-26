package com.autofinance.api.vehicleoffers.interfaces.rest.controllers;

import com.autofinance.api.vehicleoffers.domain.model.queries.GetAllVehicleOffersQuery;
import com.autofinance.api.vehicleoffers.domain.model.queries.GetVehicleOfferByIdQuery;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferCommandService;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferQueryService;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.RegisterVehicleOfferResource;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.UpdateVehicleOfferResource;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.VehicleOfferResource;
import com.autofinance.api.vehicleoffers.interfaces.rest.transform.RegisterVehicleOfferCommandFromResourceAssembler;
import com.autofinance.api.vehicleoffers.interfaces.rest.transform.UpdateVehicleOfferCommandFromResourceAssembler;
import com.autofinance.api.vehicleoffers.interfaces.rest.transform.VehicleOfferResourceFromEntityAssembler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Inbound REST adapter for the Vehicle Offers context. The dealership (tenant) is taken from the
 * {@code X-Dealership-Id} header on writes that need it; reads and updates are scoped by the tenant
 * filter. Thin: resource → assembler → command/query services → assembler → resource (re-querying after
 * a write so the response reflects stored state). OpenAPI docs live in {@link VehicleOffersApi}.
 */
@RestController
@RequestMapping(value = "/api/v1/vehicle-offers", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleOffersController implements VehicleOffersApi {

    private final VehicleOfferCommandService commandService;
    private final VehicleOfferQueryService queryService;

    public VehicleOffersController(VehicleOfferCommandService commandService,
                                   VehicleOfferQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleOfferResource> register(
            @RequestHeader("X-Dealership-Id") UUID dealershipId,
            @Valid @RequestBody RegisterVehicleOfferResource resource) {
        var command = RegisterVehicleOfferCommandFromResourceAssembler.toCommandFromResource(dealershipId, resource);
        var id = commandService.handle(command);
        return queryService.handle(new GetVehicleOfferByIdQuery(id))
                .map(offer -> new ResponseEntity<>(
                        VehicleOfferResourceFromEntityAssembler.toResourceFromEntity(offer), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping(value = "/{vehicleOfferId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleOfferResource> update(
            @PathVariable UUID vehicleOfferId,
            @Valid @RequestBody UpdateVehicleOfferResource resource) {
        var command = UpdateVehicleOfferCommandFromResourceAssembler.toCommandFromResource(vehicleOfferId, resource);
        return commandService.handle(command)
                .flatMap(id -> queryService.handle(new GetVehicleOfferByIdQuery(id)))
                .map(offer -> ResponseEntity.ok(
                        VehicleOfferResourceFromEntityAssembler.toResourceFromEntity(offer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/{vehicleOfferId}")
    public ResponseEntity<VehicleOfferResource> getById(@PathVariable UUID vehicleOfferId) {
        return queryService.handle(new GetVehicleOfferByIdQuery(new VehicleOfferId(vehicleOfferId)))
                .map(offer -> ResponseEntity.ok(
                        VehicleOfferResourceFromEntityAssembler.toResourceFromEntity(offer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping
    public ResponseEntity<List<VehicleOfferResource>> list() {
        List<VehicleOfferResource> resources = queryService.handle(new GetAllVehicleOffersQuery())
                .stream()
                .map(VehicleOfferResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
