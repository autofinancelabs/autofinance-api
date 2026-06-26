package com.autofinance.api.iam.domain.services;

import com.autofinance.api.iam.domain.model.aggregates.User;

/** Result of a successful sign-in: the authenticated user and the issued (bearer) token. */
public record AuthenticatedUser(User user, String token) {
}
