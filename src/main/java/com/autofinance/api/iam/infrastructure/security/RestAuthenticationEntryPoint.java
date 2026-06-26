package com.autofinance.api.iam.infrastructure.security;

import com.autofinance.api.shared.interfaces.rest.ProblemDetails;
import com.autofinance.api.shared.interfaces.rest.WebErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/** Writes a 401 RFC 9457 problem+json (code {@code UNAUTHENTICATED}) when a request is not authenticated. */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        var body = ProblemDetails.body(WebErrorCode.UNAUTHENTICATED,
                "Authentication is required to access this resource.", request.getRequestURI());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        MAPPER.writeValue(response.getWriter(), body);
    }
}
