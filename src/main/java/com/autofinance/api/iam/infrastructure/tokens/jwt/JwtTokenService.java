package com.autofinance.api.iam.infrastructure.tokens.jwt;

import com.autofinance.api.iam.application.internal.outboundservices.tokens.TokenService;
import com.autofinance.api.iam.domain.model.aggregates.User;
import com.autofinance.api.iam.infrastructure.security.AuthenticatedUserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * JWT (HMAC) implementation of {@link TokenService}. The token's subject is the user id; it also carries
 * the dealership (tenant) and username so the bearer filter can rebuild the principal without a DB hit.
 */
@Service
public class JwtTokenService implements TokenService {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenService.class);
    private static final String CLAIM_DEALERSHIP_ID = "dealershipId";
    private static final String CLAIM_USERNAME = "username";

    private final SecretKey key;
    private final long expirationMinutes;

    public JwtTokenService(@Value("${security.jwt.secret}") String secret,
                           @Value("${security.jwt.expiration-minutes}") long expirationMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    @Override
    public String generateToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getId().value().toString())
                .claim(CLAIM_DEALERSHIP_ID, user.getDealershipId().toString())
                .claim(CLAIM_USERNAME, user.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    /** Validates the token (signature + expiry) and rebuilds the principal, or empty if invalid. */
    public Optional<AuthenticatedUserPrincipal> parse(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            return Optional.of(new AuthenticatedUserPrincipal(
                    UUID.fromString(claims.getSubject()),
                    UUID.fromString(claims.get(CLAIM_DEALERSHIP_ID, String.class)),
                    claims.get(CLAIM_USERNAME, String.class)));
        } catch (JwtException | IllegalArgumentException invalid) {
            log.debug("Rejected JWT: {}", invalid.getMessage());
            return Optional.empty();
        }
    }
}
