package com.autofinance.api.iam.domain.model.aggregates;

import com.autofinance.api.iam.domain.model.commands.RegisterDealershipCommand;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import com.autofinance.api.iam.domain.model.valueobjects.Ruc;

/**
 * Domain factory for the {@link Dealership} aggregate: assembles the dealership account from raw inputs
 * and generates its identity. Co-located with the aggregate it creates.
 */
public class DealershipFactory {

    public Dealership create(RegisterDealershipCommand command) {
        return new Dealership(
                DealershipId.generate(),
                command.name(),
                new Ruc(command.ruc()),
                command.contactEmail());
    }
}
