package com.autofinance.api.iam.domain.services;

import com.autofinance.api.iam.domain.model.commands.RegisterDealershipCommand;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;

/** Application-service port (domain) for the IAM write use cases. */
public interface IamCommandService {

    /** Registers a dealership account and its first user; returns the new dealership id. */
    DealershipId handle(RegisterDealershipCommand command);
}
