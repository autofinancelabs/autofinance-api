package com.autofinance.api.iam.application.internal.outboundservices.tokens;

import com.autofinance.api.iam.domain.model.aggregates.User;

/**
 * Outbound port for issuing access tokens. Implemented in infrastructure (JWT). The token encodes the
 * user id, the dealership (tenant) and the username so the bearer filter can rebuild the principal.
 */
public interface TokenService {

    String generateToken(User user);
}
