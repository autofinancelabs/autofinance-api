package com.autofinance.api.iam.infrastructure.security;

import com.autofinance.api.shared.interfaces.rest.ProblemDetails;
import com.autofinance.api.shared.interfaces.rest.WebErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/** Writes a 403 RFC 9457 problem+json (code {@code ACCESS_DENIED}) when an authenticated request is denied. */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        var body = ProblemDetails.body(WebErrorCode.ACCESS_DENIED,
                "You are not allowed to perform this action.", request.getRequestURI());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        MAPPER.writeValue(response.getWriter(), body);
    }
}
