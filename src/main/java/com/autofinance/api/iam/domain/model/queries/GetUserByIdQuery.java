package com.autofinance.api.iam.domain.model.queries;

import com.autofinance.api.iam.domain.model.valueobjects.UserId;

/** Intent to read a single user by its id. */
public record GetUserByIdQuery(UserId userId) {
}
