package com.autofinance.api.iam.application.internal.commandservices;

import com.autofinance.api.iam.application.internal.outboundservices.hashing.PasswordHasher;
import com.autofinance.api.iam.application.internal.outboundservices.tokens.TokenService;
import com.autofinance.api.iam.domain.exceptions.InvalidCredentialsException;
import com.autofinance.api.iam.domain.model.aggregates.User;
import com.autofinance.api.iam.domain.model.commands.SignInCommand;
import com.autofinance.api.iam.domain.repositories.UserRepository;
import com.autofinance.api.iam.domain.services.AuthenticatedUser;
import com.autofinance.api.iam.domain.services.AuthenticationCommandService;
import com.autofinance.api.shared.domain.model.valueobjects.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Orchestrates sign-in: resolves the user by username or email, verifies the password, and issues a token.
 * Failures are reported as a single {@link InvalidCredentialsException} (no disclosure of which part
 * failed). Global lookup — IAM is not tenant-scoped.
 */
@Service
public class AuthenticationCommandServiceImpl implements AuthenticationCommandService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenService tokenService;

    public AuthenticationCommandServiceImpl(UserRepository userRepository,
                                            PasswordHasher passwordHasher,
                                            TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticatedUser handle(SignInCommand command) {
        User user = findByIdentifier(command.identifier())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordHasher.matches(command.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        return new AuthenticatedUser(user, tokenService.generateToken(user));
    }

    private Optional<User> findByIdentifier(String identifier) {
        Optional<User> byUsername = userRepository.findByUsername(identifier);
        if (byUsername.isPresent()) {
            return byUsername;
        }
        try {
            return userRepository.findByEmail(new Email(identifier));
        } catch (IllegalArgumentException notAnEmail) {
            return Optional.empty();
        }
    }
}
