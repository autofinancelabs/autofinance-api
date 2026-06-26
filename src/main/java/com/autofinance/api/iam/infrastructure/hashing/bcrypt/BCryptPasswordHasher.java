package com.autofinance.api.iam.infrastructure.hashing.bcrypt;

import com.autofinance.api.iam.application.internal.outboundservices.hashing.PasswordHasher;
import com.autofinance.api.iam.domain.model.valueobjects.PasswordHash;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/** BCrypt implementation of the {@link PasswordHasher} port (spring-security-crypto, no web security). */
@Service
public class BCryptPasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public PasswordHash hash(String rawPassword) {
        return new PasswordHash(encoder.encode(rawPassword));
    }
}
