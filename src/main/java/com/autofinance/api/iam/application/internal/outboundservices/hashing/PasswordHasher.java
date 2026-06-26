package com.autofinance.api.iam.application.internal.outboundservices.hashing;

import com.autofinance.api.iam.domain.model.valueobjects.PasswordHash;

/**
 * Outbound port for hashing a raw password into an opaque {@link PasswordHash}. Implemented in
 * infrastructure (BCrypt). Credential verification ({@code matches}) is added in the security pass.
 */
public interface PasswordHasher {

    PasswordHash hash(String rawPassword);
}
