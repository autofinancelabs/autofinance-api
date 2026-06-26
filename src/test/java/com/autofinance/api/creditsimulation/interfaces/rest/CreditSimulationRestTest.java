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
 * a dealership registers and signs in, creates a client and a vehicle offer, then generates a simulation
 * referencing them by id — the body carries no price (the ACL takes it from the offer). Reads back with
 * its token; a different dealership's token cannot see it; unauthenticated requests are rejected.
 */
@AutoConfigureMockMvc
class CreditSimulationRestTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void generatesFromTheReferencedOfferAndIsolatesByTenant() throws Exception {
        String tokenA = registerAndLogin("20100000001", "a@autonorte.pe", "dealerA");
        UUID clientId = createClient(tokenA, "12345678");
        UUID offerId = createOffer(tokenA);

        String body = objectMapper.writeValueAsString(resourceFrom(GoldenDatasets.d1(), clientId, offerId));
        String created = mockMvc.perform(post("/api/v1/credit-simulations")
                        .header("Authorization", "Bearer " + tokenA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.state").value("GENERATED"))
                .andExpect(jsonPath("$.schedule.length()").value(37))   // price (16000) came from the offer
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(created).get("id").asText();

        mockMvc.perform(get("/api/v1/credit-simulations/{id}", id).header("Authorization", "Bearer " + tokenA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

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

    private UUID createClient(String token, String document) throws Exception {
        String resource = """
                {"documentType":"DNI","documentNumber":"%s","email":null,"phone":null,"address":null}
                """.formatted(document);
        String body = mockMvc.perform(post("/api/v1/clients")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON).content(resource))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return UUID.fromString(objectMapper.readTree(body).get("id").asText());
    }

    private UUID createOffer(String token) throws Exception {
        String resource = """
                {"make":"Toyota","model":"Corolla","year":2024,"salePrice":16000,"currency":"PEN","planName":null,"planInstallments":null}
                """;
        String body = mockMvc.perform(post("/api/v1/vehicle-offers")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON).content(resource))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return UUID.fromString(objectMapper.readTree(body).get("id").asText());
    }

    private static GenerateSimulationResource resourceFrom(GenerateSimulationCommand c, UUID clientId, UUID vehicleOfferId) {
        List<String> grace = c.gracePlan().stream().map(Enum::name).toList();
        List<CostResource> costs = c.costs().stream()
                .map(x -> new CostResource(x.name(), x.value(), x.basis().name(), x.timing().name(), x.embedded()))
                .toList();
        return new GenerateSimulationResource(
                clientId, vehicleOfferId,
                c.rateValue(), c.rateType().name(), c.capitalization() == null ? null : c.capitalization().name(),
                c.initialPercentage(), c.balloonPercentage(),
                c.numberOfInstallments(), c.frequencyDays(), c.daysPerYear(),
                grace, costs, c.costOfCapitalAnnual());
    }
}
