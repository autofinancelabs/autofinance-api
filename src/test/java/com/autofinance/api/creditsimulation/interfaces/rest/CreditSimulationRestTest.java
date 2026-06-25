package com.autofinance.api.creditsimulation.interfaces.rest;

import com.autofinance.api.creditsimulation.GoldenDatasets;
import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.CostResource;
import com.autofinance.api.creditsimulation.interfaces.rest.resources.GenerateSimulationResource;
import com.autofinance.api.shared.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * End-to-end REST test over the real stack (Testcontainers Postgres + Flyway + @TenantId): POST generates
 * and persists, GET reads back the jsonb snapshot, and a different dealership header cannot see it.
 */
@AutoConfigureMockMvc
class CreditSimulationRestTest extends AbstractIntegrationTest {

    private static final String HEADER = "X-Dealership-Id";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void generatesPersistsAndReadsBackScopedByTenant() throws Exception {
        GenerateSimulationCommand command = GoldenDatasets.d1();
        UUID dealershipId = command.dealershipId();
        String body = objectMapper.writeValueAsString(resourceFrom(command));

        String created = mockMvc.perform(post("/api/v1/credit-simulations")
                        .header(HEADER, dealershipId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.state").value("GENERATED"))
                .andExpect(jsonPath("$.schedule.length()").value(37))
                .andExpect(jsonPath("$.schedule[0].appliedCosts").isArray())
                .andExpect(jsonPath("$.indicators.tcea").exists())
                .andExpect(jsonPath("$.summary.totalsPerCost.gps").exists())
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(created).get("id").asText();

        // same tenant → found
        mockMvc.perform(get("/api/v1/credit-simulations/{id}", id).header(HEADER, dealershipId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        // different tenant → not visible
        mockMvc.perform(get("/api/v1/credit-simulations/{id}", id).header(HEADER, UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void missingTenantHeaderIsRejected() throws Exception {
        String body = objectMapper.writeValueAsString(resourceFrom(GoldenDatasets.d2()));
        mockMvc.perform(post("/api/v1/credit-simulations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MISSING_TENANT"))
                .andExpect(jsonPath("$.trace").doesNotExist());
    }

    private static GenerateSimulationResource resourceFrom(GenerateSimulationCommand c) {
        List<String> grace = c.gracePlan().stream().map(Enum::name).toList();
        List<CostResource> costs = c.costs().stream()
                .map(x -> new CostResource(x.name(), x.value(), x.basis().name(), x.timing().name(), x.embedded()))
                .toList();
        return new GenerateSimulationResource(
                c.clientId(), c.vehicleOfferId(), c.salePrice(), c.currency().name(),
                c.rateValue(), c.rateType().name(), c.capitalization() == null ? null : c.capitalization().name(),
                c.initialPercentage(), c.balloonPercentage(),
                c.numberOfInstallments(), c.frequencyDays(), c.daysPerYear(),
                grace, costs, c.costOfCapitalAnnual());
    }
}
