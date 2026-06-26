package com.autofinance.api.iam.domain.services;

import com.autofinance.api.iam.domain.model.aggregates.Dealership;
import com.autofinance.api.iam.domain.model.aggregates.User;
import com.autofinance.api.iam.domain.model.queries.GetDealershipByIdQuery;
import com.autofinance.api.iam.domain.model.queries.GetUserByIdQuery;

import java.util.Optional;

/** Application-service port (domain) for the IAM read use cases. */
public interface IamQueryService {

    Optional<Dealership> handle(GetDealershipByIdQuery query);

    Optional<User> handle(GetUserByIdQuery query);
}
