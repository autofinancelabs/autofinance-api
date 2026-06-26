package com.autofinance.api.iam.domain.model.aggregates;

import com.autofinance.api.iam.domain.model.commands.RegisterDealershipCommand;
import com.autofinance.api.iam.domain.model.valueobjects.DealershipId;
import com.autofinance.api.iam.domain.model.valueobjects.PasswordHash;
import com.autofinance.api.iam.domain.model.valueobjects.UserId;
import com.autofinance.api.shared.domain.model.valueobjects.Email;

/**
 * Domain factory for the first {@link User} of a dealership. The password is provided already hashed by
 * the application layer (the domain never handles plaintext).
 */
public class UserFactory {

    public User create(DealershipId dealershipId, RegisterDealershipCommand command, PasswordHash passwordHash) {
        return new User(
                UserId.generate(),
                dealershipId.value(),
                new Email(command.userEmail()),
                command.username(),
                passwordHash);
    }
}
