package com.autofinance.api.iam.domain.model.queries;

import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;

/** Intent to read a single dealership by its id. */
public record GetDealershipByIdQuery(DealershipId dealershipId) {
}
