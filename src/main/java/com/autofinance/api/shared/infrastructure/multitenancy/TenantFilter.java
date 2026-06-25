package com.autofinance.api.shared.infrastructure.multitenancy;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Populates {@link TenantContext} from the {@code X-Dealership-Id} request header so Hibernate's
 * {@code @TenantId} resolver scopes the transaction to the right dealership, and clears it afterwards.
 * A missing/malformed header is left unset here — the controller's required {@code @RequestHeader UUID}
 * surfaces it as a 400.
 */
@Component
public class TenantFilter extends OncePerRequestFilter {

    public static final String TENANT_HEADER = "X-Dealership-Id";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader(TENANT_HEADER);
            if (header != null && !header.isBlank()) {
                try {
                    TenantContext.setTenant(UUID.fromString(header));
                } catch (IllegalArgumentException malformed) {
                    // leave unset; controller binding will reject the request with 400
                }
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
