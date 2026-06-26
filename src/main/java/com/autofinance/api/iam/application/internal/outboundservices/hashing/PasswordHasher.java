package com.autofinance.api.iam.application.internal.outboundservices.hashing;

import com.autofinance.api.iam.domain.model.valueobjects.PasswordHash;

/** Outbound port for hashing and verifying passwords. Implemented in infrastructure (BCrypt). */
public interface PasswordHasher {

    PasswordHash hash(String rawPassword);

    boolean matches(String rawPassword, PasswordHash passwordHash);
}
