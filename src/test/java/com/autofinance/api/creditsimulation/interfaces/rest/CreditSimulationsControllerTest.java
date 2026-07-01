package com.autofinance.api.creditsimulation.interfaces.rest;

import com.autofinance.api.creditsimulation.GoldenDatasets;
import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulationFactory;
import com.autofinance.api.creditsimulation.domain.model.queries.GetAllSimulationsQuery;
import com.autofinance.api.creditsimulation.domain.model.queries.GetSimulationByIdQuery;
import com.autofinance.api.creditsimulation.domain.model.commands.RequestSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.queries.GetSimulationsByClientIdQuery;
import com.autofinance.api.creditsimulation.domain.services.CreditSimulationCommandService;
import com.autofinance.api.creditsimulation.domain.services.CreditSimulationQueryService;
import com.autofinance.api.creditsimulation.interfaces.rest.controllers.CreditSimulationsController;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.CostResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.GenerateSimulationResource;
import com.autofinance.api.shared.interfaces.rest.CurrentUser;
import com.autofinance.api.shared.interfaces.rest.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreditSimulationsController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class CreditSimulationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CreditSimulationCommandService commandService;

    @MockitoBean
    private CreditSimulationQueryService queryService;

    @MockitoBean
    private CurrentUser currentUser;

    private static final UUID DEALER = UUID.randomUUID();

    private final CreditSimulation generated = new CreditSimulationFactory().create(GoldenDatasets.d1());

    private GenerateSimulationResource validResource() {
        return new GenerateSimulationResource(
                UUID.randomUUID(), UUID.randomUUID(),
                new BigDecimal("0.20"), "EFFECTIVE", null, null,
                new BigDecimal("0.20"), BigDecimal.ZERO,
                12, 30, 360,
                List.of("NONE"), List.<CostResource>of(),
                new BigDecimal("0.50"));
    }

    /** Deserializes fine but violates a Bean Validation constraint (@Positive numberOfInstallments). */
    private GenerateSimulationResource constraintViolatingResource() {
        return new GenerateSimulationResource(
                UUID.randomUUID(), UUID.randomUUID(),
                new BigDecimal("0.20"), "EFFECTIVE", null, null,
                new BigDecimal("0.20"), BigDecimal.ZERO,
                0, 30, 360,
                List.of("NONE"), List.<CostResource>of(),
                new BigDecimal("0.50"));
    }

    /** Violates several constraints at once: gracePlan empty, numberOfInstallments not positive, rateValue null. */
    private GenerateSimulationResource multiViolationResource() {
        return new GenerateSimulationResource(
                UUID.randomUUID(), UUID.randomUUID(),
                null, "EFFECTIVE", null, null,
                new BigDecimal("0.20"), BigDecimal.ZERO,
                0, 30, 360,
                List.<String>of(), List.<CostResource>of(),
                new BigDecimal("0.50"));
    }

    @Test
    void generateReturns201WithTheStoredSnapshot() throws Exception {
        when(currentUser.dealershipId()).thenReturn(DEALER);
        when(commandService.handle(any(RequestSimulationCommand.class))).thenReturn(generated.getId());
        when(queryService.handle(any(GetSimulationByIdQuery.class))).thenReturn(Optional.of(generated));

        mockMvc.perform(post("/api/v1/credit-simulations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validResource())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(generated.getId().value().toString()))
                .andExpect(jsonPath("$.state").value("GENERATED"))
                .andExpect(jsonPath("$.schedule").isArray());
    }

    @Test
    void generateWithInvalidBodyReturns400WithFieldErrors() throws Exception {
        mockMvc.perform(post("/api/v1/credit-simulations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(constraintViolatingResource())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void validationErrorsAreSortedByField() throws Exception {
        mockMvc.perform(post("/api/v1/credit-simulations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(multiViolationResource())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.errors[0].field").value("gracePlan"))
                .andExpect(jsonPath("$.errors[1].field").value("numberOfInstallments"))
                .andExpect(jsonPath("$.errors[2].field").value("rateValue"));
    }

    @Test
    void malformedBodyReturns400() throws Exception {
        mockMvc.perform(post("/api/v1/credit-simulations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clientId\":null}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MALFORMED_REQUEST"))
                .andExpect(jsonPath("$.trace").doesNotExist());
    }

    @Test
    void unexpectedErrorReturns500WithoutLeakingInternals() throws Exception {
        when(currentUser.dealershipId()).thenReturn(DEALER);
        when(commandService.handle(any(RequestSimulationCommand.class))).thenThrow(new RuntimeException("boom: secret stacktrace"));

        mockMvc.perform(post("/api/v1/credit-simulations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validResource())))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.detail").value("An unexpected error occurred."))
                .andExpect(jsonPath("$.trace").doesNotExist());
    }

    @Test
    void getByIdReturns200WhenFound() throws Exception {
        when(queryService.handle(any(GetSimulationByIdQuery.class))).thenReturn(Optional.of(generated));

        mockMvc.perform(get("/api/v1/credit-simulations/{id}", generated.getId().value()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(generated.getId().value().toString()));
    }

    @Test
    void getByIdReturns404WhenMissing() throws Exception {
        when(queryService.handle(any(GetSimulationByIdQuery.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/credit-simulations/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByClientReturns200WithList() throws Exception {
        when(queryService.handle(any(GetSimulationsByClientIdQuery.class))).thenReturn(List.of(generated));

        mockMvc.perform(get("/api/v1/credit-simulations").param("clientId", UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(generated.getId().value().toString()));
    }

    @Test
    void listAllReturns200WithEveryDealershipSimulation() throws Exception {
        when(queryService.handle(any(GetAllSimulationsQuery.class))).thenReturn(List.of(generated));

        mockMvc.perform(get("/api/v1/credit-simulations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(generated.getId().value().toString()))
                .andExpect(jsonPath("$[0].state").value("GENERATED"));
    }
}
