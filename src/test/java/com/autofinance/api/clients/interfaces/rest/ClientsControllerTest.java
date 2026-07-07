package com.autofinance.api.clients.interfaces.rest;

import com.autofinance.api.clients.domain.exceptions.DuplicateClientDocumentException;
import com.autofinance.api.clients.domain.model.aggregates.Client;
import com.autofinance.api.clients.domain.model.commands.RegisterClientCommand;
import com.autofinance.api.clients.domain.model.commands.UpdateClientCommand;
import com.autofinance.api.clients.domain.model.queries.GetClientByIdQuery;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.clients.domain.model.valueobjects.ContactInfo;
import com.autofinance.api.clients.domain.model.valueobjects.DocumentId;
import com.autofinance.api.clients.domain.model.valueobjects.DocumentType;
import com.autofinance.api.clients.domain.model.valueobjects.PersonName;
import com.autofinance.api.clients.domain.services.ClientCommandService;
import com.autofinance.api.clients.domain.services.ClientQueryService;
import com.autofinance.api.clients.interfaces.rest.controllers.ClientsController;
import com.autofinance.api.clients.interfaces.rest.resources.RegisterClientResource;
import com.autofinance.api.clients.interfaces.rest.resources.UpdateClientResource;
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

@WebMvcTest(ClientsController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ClientsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ClientCommandService commandService;

    @MockitoBean
    private ClientQueryService queryService;

    @MockitoBean
    private CurrentUser currentUser;

    private static final UUID DEALER = UUID.randomUUID();

    private final Client client = new Client(
            ClientId.generate(), DEALER,
            new DocumentId(DocumentType.DNI, "12345678"),
            new PersonName("Ana María", "Pérez García"),
            ContactInfo.of("ana@example.com", "+51 999 888 777", "Av. Lima 123"));

    private RegisterClientResource validRegister() {
        return new RegisterClientResource(
                "DNI", "12345678", "Ana María", "Pérez García",
                "ana@example.com", "+51 999 888 777", "Av. Lima 123");
    }

    @Test
    void registerReturns201WithTheStoredSnapshot() throws Exception {
        when(currentUser.dealershipId()).thenReturn(DEALER);
        when(commandService.handle(any(RegisterClientCommand.class))).thenReturn(client.getId());
        when(queryService.handle(any(GetClientByIdQuery.class))).thenReturn(Optional.of(client));

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegister())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(client.getId().value().toString()))
                .andExpect(jsonPath("$.documentType").value("DNI"))
                .andExpect(jsonPath("$.documentNumber").value("12345678"))
                .andExpect(jsonPath("$.firstName").value("Ana María"))
                .andExpect(jsonPath("$.lastName").value("Pérez García"))
                .andExpect(jsonPath("$.email").value("ana@example.com"));
    }

    @Test
    void registerWithInvalidBodyReturns400WithFieldErrors() throws Exception {
        var invalid = new RegisterClientResource("DNI", "  ", "  ", "  ", null, null, null);
        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void registerWithDuplicateDocumentReturns409() throws Exception {
        when(currentUser.dealershipId()).thenReturn(DEALER);
        when(commandService.handle(any(RegisterClientCommand.class)))
                .thenThrow(new DuplicateClientDocumentException(new DocumentId(DocumentType.DNI, "12345678")));

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegister())))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("DUPLICATE_CLIENT_DOCUMENT"))
                .andExpect(jsonPath("$.trace").doesNotExist());
    }

    @Test
    void updateReturns200WhenFound() throws Exception {
        when(commandService.handle(any(UpdateClientCommand.class))).thenReturn(Optional.of(client.getId()));
        when(queryService.handle(any(GetClientByIdQuery.class))).thenReturn(Optional.of(client));

        var body = new UpdateClientResource("Ana Lucía", "Pérez Soto", "nuevo@example.com", null, null);

        mockMvc.perform(put("/api/v1/clients/{id}", client.getId().value())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId().value().toString()));
    }

    @Test
    void updateReturns404WhenMissing() throws Exception {
        when(commandService.handle(any(UpdateClientCommand.class))).thenReturn(Optional.empty());

        var body = new UpdateClientResource("Ana Lucía", "Pérez Soto", "nuevo@example.com", null, null);

        mockMvc.perform(put("/api/v1/clients/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByIdReturns200WhenFound() throws Exception {
        when(queryService.handle(any(GetClientByIdQuery.class))).thenReturn(Optional.of(client));

        mockMvc.perform(get("/api/v1/clients/{id}", client.getId().value()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId().value().toString()));
    }

    @Test
    void getByIdReturns404WhenMissing() throws Exception {
        when(queryService.handle(any(GetClientByIdQuery.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/clients/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listReturns200WithArray() throws Exception {
        when(queryService.handle(any(com.autofinance.api.clients.domain.model.queries.GetAllClientsQuery.class)))
                .thenReturn(List.of(client));

        mockMvc.perform(get("/api/v1/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(client.getId().value().toString()));
    }
}
