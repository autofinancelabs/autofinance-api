package com.autofinance.api.iam.interfaces.rest;

import com.autofinance.api.iam.domain.exceptions.DuplicateRucException;
import com.autofinance.api.iam.domain.model.aggregates.Dealership;
import com.autofinance.api.iam.domain.model.commands.RegisterDealershipCommand;
import com.autofinance.api.iam.domain.model.queries.GetDealershipByIdQuery;
import com.autofinance.api.iam.domain.model.valueobjects.DealershipId;
import com.autofinance.api.iam.domain.model.valueobjects.Ruc;
import com.autofinance.api.iam.domain.services.IamCommandService;
import com.autofinance.api.iam.domain.services.IamQueryService;
import com.autofinance.api.iam.interfaces.rest.controllers.DealershipsController;
import com.autofinance.api.iam.interfaces.rest.resources.RegisterDealershipResource;
import com.autofinance.api.shared.interfaces.rest.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DealershipsController.class)
@Import(GlobalExceptionHandler.class)
class DealershipsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private IamCommandService commandService;

    @MockitoBean
    private IamQueryService queryService;

    private final Dealership dealership = new Dealership(
            DealershipId.generate(), "AutoNorte SAC", new Ruc("20123456789"), "ventas@autonorte.pe");

    private RegisterDealershipResource validRegister() {
        return new RegisterDealershipResource(
                "AutoNorte SAC", "20123456789", "ventas@autonorte.pe",
                "ana@autonorte.pe", "ana", "s3cr3t-pass");
    }

    @Test
    void registerReturns201WithTheStoredAccount() throws Exception {
        when(commandService.handle(any(RegisterDealershipCommand.class))).thenReturn(dealership.getId());
        when(queryService.handle(any(GetDealershipByIdQuery.class))).thenReturn(Optional.of(dealership));

        mockMvc.perform(post("/api/v1/dealerships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegister())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(dealership.getId().value().toString()))
                .andExpect(jsonPath("$.name").value("AutoNorte SAC"))
                .andExpect(jsonPath("$.ruc").value("20123456789"));
    }

    @Test
    void registerWithInvalidBodyReturns400WithFieldErrors() throws Exception {
        var invalid = new RegisterDealershipResource("", "123", "x", "not-an-email", "", "");
        mockMvc.perform(post("/api/v1/dealerships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void registerWithDuplicateRucReturns409() throws Exception {
        when(commandService.handle(any(RegisterDealershipCommand.class)))
                .thenThrow(new DuplicateRucException(new Ruc("20123456789")));

        mockMvc.perform(post("/api/v1/dealerships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegister())))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATE_RUC"))
                .andExpect(jsonPath("$.trace").doesNotExist());
    }

    @Test
    void getByIdReturns200WhenFound() throws Exception {
        when(queryService.handle(any(GetDealershipByIdQuery.class))).thenReturn(Optional.of(dealership));

        mockMvc.perform(get("/api/v1/dealerships/{id}", dealership.getId().value()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dealership.getId().value().toString()));
    }

    @Test
    void getByIdReturns404WhenMissing() throws Exception {
        when(queryService.handle(any(GetDealershipByIdQuery.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/dealerships/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
