package com.autofinance.api.iam.application.internal.commandservices;

import com.autofinance.api.iam.application.internal.outboundservices.hashing.PasswordHasher;
import com.autofinance.api.iam.domain.exceptions.DuplicateEmailException;
import com.autofinance.api.iam.domain.exceptions.DuplicateRucException;
import com.autofinance.api.iam.domain.exceptions.DuplicateUsernameException;
import com.autofinance.api.iam.domain.model.aggregates.Dealership;
import com.autofinance.api.iam.domain.model.aggregates.DealershipFactory;
import com.autofinance.api.iam.domain.model.aggregates.User;
import com.autofinance.api.iam.domain.model.aggregates.UserFactory;
import com.autofinance.api.iam.domain.model.commands.RegisterDealershipCommand;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import com.autofinance.api.iam.domain.model.valueobjects.PasswordHash;
import com.autofinance.api.iam.domain.model.valueobjects.Ruc;
import com.autofinance.api.iam.domain.repositories.DealershipRepository;
import com.autofinance.api.iam.domain.repositories.UserRepository;
import com.autofinance.api.iam.domain.services.IamCommandService;
import com.autofinance.api.shared.domain.model.valueobjects.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Orchestrates the dealership-registration use case: rejects a duplicate RUC / email / username, hashes
 * the password, and creates the dealership account and its first user in one transaction. The dealership
 * is saved before the user so the {@code dealership_id} FK holds. IAM is global (no {@code @TenantId}).
 */
@Service
public class IamCommandServiceImpl implements IamCommandService {

    private final DealershipRepository dealershipRepository;
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final DealershipFactory dealershipFactory = new DealershipFactory();
    private final UserFactory userFactory = new UserFactory();

    public IamCommandServiceImpl(DealershipRepository dealershipRepository,
                                 UserRepository userRepository,
                                 PasswordHasher passwordHasher) {
        this.dealershipRepository = dealershipRepository;
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    @Transactional
    public DealershipId handle(RegisterDealershipCommand command) {
        Ruc ruc = new Ruc(command.ruc());
        Email email = new Email(command.userEmail());

        if (dealershipRepository.existsByRuc(ruc)) {
            throw new DuplicateRucException(ruc);
        }
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
        if (userRepository.existsByUsername(command.username())) {
            throw new DuplicateUsernameException(command.username());
        }

        PasswordHash passwordHash = passwordHasher.hash(command.rawPassword());

        Dealership dealership = dealershipFactory.create(command);
        dealershipRepository.save(dealership);

        User user = userFactory.create(dealership.getId(), command, passwordHash);
        userRepository.save(user);

        return dealership.getId();
    }
}
