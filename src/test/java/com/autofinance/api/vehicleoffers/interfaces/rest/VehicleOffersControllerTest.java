package com.autofinance.api.vehicleoffers.interfaces.rest;

import com.autofinance.api.creditsimulation.interfaces.rest.GlobalExceptionHandler;
import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.shared.domain.model.valueobjects.Money;
import com.autofinance.api.vehicleoffers.domain.model.aggregates.VehicleOffer;
import com.autofinance.api.vehicleoffers.domain.model.queries.GetAllVehicleOffersQuery;
import com.autofinance.api.vehicleoffers.domain.model.queries.GetVehicleOfferByIdQuery;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Plan;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Vehicle;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferCommandService;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferQueryService;
import com.autofinance.api.vehicleoffers.interfaces.rest.controllers.VehicleOffersController;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.RegisterVehicleOfferResource;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.UpdateVehicleOfferResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleOffersController.class)
@Import(GlobalExceptionHandler.class)
class VehicleOffersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private VehicleOfferCommandService commandService;

    @MockitoBean
    private VehicleOfferQueryService queryService;

    private static final String HEADER = "X-Dealership-Id";
    private static final UUID DEALER = UUID.randomUUID();

    private final VehicleOffer offer = new VehicleOffer(
            VehicleOfferId.generate(), DEALER,
            new Vehicle("Toyota", "Corolla", 2024),
            Money.of(new BigDecimal("50000.00"), Currency.PEN),
            new Plan("Plan 36", 36));

    private RegisterVehicleOfferResource validRegister() {
        return new RegisterVehicleOfferResource("Toyota", "Corolla", 2024,
                new BigDecimal("50000.00"), "PEN", "Plan 36", 36);
    }

    /** Deserializes fine but violates Bean Validation (blank make + non-positive salePrice). */
    private RegisterVehicleOfferResource invalidRegister() {
        return new RegisterVehicleOfferResource("", "Corolla", 2024,
                new BigDecimal("-1"), "PEN", null, null);
    }

    @Test
    void registerReturns201WithTheStoredSnapshot() throws Exception {
        when(commandService.handle(any(com.autofinance.api.vehicleoffers.domain.model.commands.RegisterVehicleOfferCommand.class)))
                .thenReturn(offer.getId());
        when(queryService.handle(any(GetVehicleOfferByIdQuery.class))).thenReturn(Optional.of(offer));

        mockMvc.perform(post("/api/v1/vehicle-offers")
                        .header(HEADER, DEALER.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegister())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(offer.getId().value().toString()))
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.salePrice.currency").value("PEN"))
                .andExpect(jsonPath("$.planInstallments").value(36));
    }

    @Test
    void registerWithoutTenantHeaderReturns400WithProblemDetail() throws Exception {
        mockMvc.perform(post("/api/v1/vehicle-offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegister())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MISSING_TENANT"))
                .andExpect(jsonPath("$.trace").doesNotExist());
    }

    @Test
    void registerWithInvalidBodyReturns400WithFieldErrors() throws Exception {
        mockMvc.perform(post("/api/v1/vehicle-offers")
                        .header(HEADER, DEALER.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegister())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void updateReturns200WhenFound() throws Exception {
        when(commandService.handle(any(com.autofinance.api.vehicleoffers.domain.model.commands.UpdateVehicleOfferCommand.class)))
                .thenReturn(Optional.of(offer.getId()));
        when(queryService.handle(any(GetVehicleOfferByIdQuery.class))).thenReturn(Optional.of(offer));

        var body = new UpdateVehicleOfferResource("Toyota", "Yaris", 2025,
                new BigDecimal("42000.00"), "USD", null, null);

        mockMvc.perform(put("/api/v1/vehicle-offers/{id}", offer.getId().value())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(offer.getId().value().toString()));
    }

    @Test
    void updateReturns404WhenMissing() throws Exception {
        when(commandService.handle(any(com.autofinance.api.vehicleoffers.domain.model.commands.UpdateVehicleOfferCommand.class)))
                .thenReturn(Optional.empty());

        var body = new UpdateVehicleOfferResource("Toyota", "Yaris", 2025,
                new BigDecimal("42000.00"), "USD", null, null);

        mockMvc.perform(put("/api/v1/vehicle-offers/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByIdReturns200WhenFound() throws Exception {
        when(queryService.handle(any(GetVehicleOfferByIdQuery.class))).thenReturn(Optional.of(offer));

        mockMvc.perform(get("/api/v1/vehicle-offers/{id}", offer.getId().value()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(offer.getId().value().toString()));
    }

    @Test
    void getByIdReturns404WhenMissing() throws Exception {
        when(queryService.handle(any(GetVehicleOfferByIdQuery.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/vehicle-offers/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listReturns200WithArray() throws Exception {
        when(queryService.handle(any(GetAllVehicleOffersQuery.class))).thenReturn(List.of(offer));

        mockMvc.perform(get("/api/v1/vehicle-offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(offer.getId().value().toString()));
    }
}
