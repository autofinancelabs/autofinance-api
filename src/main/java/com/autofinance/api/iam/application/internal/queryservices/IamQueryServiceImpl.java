package com.autofinance.api.iam.application.internal.queryservices;

import com.autofinance.api.iam.domain.model.aggregates.Dealership;
import com.autofinance.api.iam.domain.model.aggregates.User;
import com.autofinance.api.iam.domain.model.queries.GetDealershipByIdQuery;
import com.autofinance.api.iam.domain.model.queries.GetUserByIdQuery;
import com.autofinance.api.iam.domain.repositories.DealershipRepository;
import com.autofinance.api.iam.domain.repositories.UserRepository;
import com.autofinance.api.iam.domain.services.IamQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/** Reads IAM aggregates through the repositories; performs no state changes. */
@Service
public class IamQueryServiceImpl implements IamQueryService {

    private final DealershipRepository dealershipRepository;
    private final UserRepository userRepository;

    public IamQueryServiceImpl(DealershipRepository dealershipRepository, UserRepository userRepository) {
        this.dealershipRepository = dealershipRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Dealership> handle(GetDealershipByIdQuery query) {
        return dealershipRepository.findById(query.dealershipId());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }
}
