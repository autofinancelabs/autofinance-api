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
 * End-to-end REST test over the real stack (Testcontainers Postgres + Flyway + Spring Security + JWT):
 * a dealership registers and signs in, generates a simulation with its bearer token (the tenant comes
 * from the token, not a header), reads it back, and a different dealership's token cannot see it.
 * Unauthenticated requests are rejected.
 */
@AutoConfigureMockMvc
class CreditSimulationRestTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void generatesScopedToTheAuthenticatedDealershipAndIsolatesByTenant() throws Exception {
        String tokenA = registerAndLogin("20100000001", "a@autonorte.pe", "dealerA");
        String body = objectMapper.writeValueAsString(resourceFrom(GoldenDatasets.d1()));

        String created = mockMvc.perform(post("/api/v1/credit-simulations")
                        .header("Authorization", "Bearer " + tokenA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.state").value("GENERATED"))
                .andExpect(jsonPath("$.schedule.length()").value(37))
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(created).get("id").asText();

        // same dealership token → found
        mockMvc.perform(get("/api/v1/credit-simulations/{id}", id).header("Authorization", "Bearer " + tokenA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        // a different dealership token → not visible
        String tokenB = registerAndLogin("20100000002", "b@autonorte.pe", "dealerB");
        mockMvc.perform(get("/api/v1/credit-simulations/{id}", id).header("Authorization", "Bearer " + tokenB))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsUnauthenticatedRequests() throws Exception {
        mockMvc.perform(get("/api/v1/credit-simulations/{id}", UUID.randomUUID()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHENTICATED"));
    }

    /** Registers a dealership (+ first user) and signs in; returns the bearer token. */
    private String registerAndLogin(String ruc, String email, String username) throws Exception {
        String register = """
                {"name":"AutoNorte SAC","ruc":"%s","contactEmail":"%s","userEmail":"%s","username":"%s","password":"s3cr3t-pass"}
                """.formatted(ruc, email, email, username);
        mockMvc.perform(post("/api/v1/dealerships")
                        .contentType(MediaType.APPLICATION_JSON).content(register))
                .andExpect(status().isCreated());

        String signIn = """
                {"identifier":"%s","password":"s3cr3t-pass"}
                """.formatted(username);
        String body = mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON).content(signIn))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(body).get("token").asText();
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
