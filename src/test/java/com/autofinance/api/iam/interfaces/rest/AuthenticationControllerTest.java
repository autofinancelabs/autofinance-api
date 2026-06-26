package com.autofinance.api.iam.interfaces.rest;

import com.autofinance.api.iam.domain.exceptions.InvalidCredentialsException;
import com.autofinance.api.iam.domain.model.aggregates.User;
import com.autofinance.api.iam.domain.model.commands.SignInCommand;
import com.autofinance.api.iam.domain.model.valueobjects.PasswordHash;
import com.autofinance.api.iam.domain.model.valueobjects.UserId;
import com.autofinance.api.iam.domain.services.AuthenticatedUser;
import com.autofinance.api.iam.domain.services.AuthenticationCommandService;
import com.autofinance.api.iam.interfaces.rest.controllers.AuthenticationController;
import com.autofinance.api.iam.interfaces.rest.resources.SignInResource;
import com.autofinance.api.shared.domain.model.valueobjects.Email;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AuthenticationCommandService authenticationCommandService;

    @Test
    void signInReturns200WithTheToken() throws Exception {
        var user = new User(UserId.generate(), UUID.randomUUID(),
                new Email("ana@autonorte.pe"), "ana", new PasswordHash("$2a$hashed"));
        when(authenticationCommandService.handle(any(SignInCommand.class)))
                .thenReturn(new AuthenticatedUser(user, "the.jwt.token"));

        mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignInResource("ana", "s3cr3t-pass"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ana"))
                .andExpect(jsonPath("$.token").value("the.jwt.token"))
                .andExpect(jsonPath("$.userId").value(user.getId().value().toString()));
    }

    @Test
    void signInWithBadCredentialsReturns401() throws Exception {
        when(authenticationCommandService.handle(any(SignInCommand.class)))
                .thenThrow(new InvalidCredentialsException());

        mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SignInResource("ana", "wrong"))))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("INVALID_CREDENTIALS"))
                .andExpect(jsonPath("$.trace").doesNotExist());
    }
}
