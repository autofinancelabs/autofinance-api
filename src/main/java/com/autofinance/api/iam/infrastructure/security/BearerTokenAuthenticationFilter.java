package com.autofinance.api.iam.infrastructure.security;

import com.autofinance.api.iam.infrastructure.tokens.jwt.JwtTokenService;
import com.autofinance.api.shared.infrastructure.multitenancy.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Authenticates a request from a {@code Authorization: Bearer <jwt>} header: validates the token, sets the
 * {@link AuthenticatedUserPrincipal} into the security context, and — replacing the old header-based
 * tenant filter — sets the dealership (tenant) into {@link TenantContext} so Hibernate's {@code @TenantId}
 * scopes the data. Cleared after the request.
 */
public class BearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenService tokenService;

    public BearerTokenAuthenticationFilter(JwtTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            tokenFrom(request)
                    .flatMap(tokenService::parse)
                    .ifPresent(principal -> {
                        var authentication = new UsernamePasswordAuthenticationToken(principal, null, List.of());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        TenantContext.setTenant(principal.dealershipId());
                    });
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private java.util.Optional<String> tokenFrom(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return java.util.Optional.of(header.substring(BEARER_PREFIX.length()));
        }
        return java.util.Optional.empty();
    }
}
