package com.autofinance.api.iam;

import com.autofinance.api.iam.domain.exceptions.DuplicateEmailException;
import com.autofinance.api.iam.domain.exceptions.DuplicateRucException;
import com.autofinance.api.iam.domain.exceptions.DuplicateUsernameException;
import com.autofinance.api.iam.domain.model.commands.RegisterDealershipCommand;
import com.autofinance.api.iam.domain.model.queries.GetDealershipByIdQuery;
import com.autofinance.api.iam.domain.model.valueobjects.DealershipId;
import com.autofinance.api.iam.domain.repositories.UserRepository;
import com.autofinance.api.iam.domain.services.IamCommandService;
import com.autofinance.api.iam.domain.services.IamQueryService;
import com.autofinance.api.shared.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test (Testcontainers Postgres): registering a dealership creates the account and its first
 * user with a hashed password, and the RUC / email / username uniqueness guards reject duplicates. IAM is
 * the tenant registry, so it is global — no {@code TenantContext}.
 */
class IamPersistenceTest extends AbstractIntegrationTest {

    @Autowired
    private IamCommandService commandService;

    @Autowired
    private IamQueryService queryService;

    @Autowired
    private UserRepository userRepository;

    private static RegisterDealershipCommand registerCommand(String ruc, String email, String username) {
        return new RegisterDealershipCommand(
                "AutoNorte SAC", ruc, "ventas@autonorte.pe",
                email, username, "s3cr3t-pass");
    }

    @Test
    void registersDealershipWithFirstUser() {
        DealershipId id = commandService.handle(
                registerCommand("20123456789", "ana@autonorte.pe", "ana"));

        var dealership = queryService.handle(new GetDealershipByIdQuery(id)).orElseThrow();
        assertThat(dealership.getName()).isEqualTo("AutoNorte SAC");
        assertThat(dealership.getRuc().value()).isEqualTo("20123456789");
        assertThat(dealership.getContactEmail()).isEqualTo("ventas@autonorte.pe");

        var user = userRepository.findByUsername("ana").orElseThrow();
        assertThat(user.getEmail().email()).isEqualTo("ana@autonorte.pe");
        assertThat(user.getDealershipId()).isEqualTo(id.value());
    }

    @Test
    void storesABcryptHashNotThePlaintext() {
        commandService.handle(registerCommand("20999999999", "bob@autonorte.pe", "bob"));

        var user = userRepository.findByUsername("bob").orElseThrow();
        assertThat(user.getPasswordHash().value())
                .isNotEqualTo("s3cr3t-pass")
                .startsWith("$2");
    }

    @Test
    void rejectsDuplicateRuc() {
        commandService.handle(registerCommand("20111111111", "c1@autonorte.pe", "c1"));
        assertThatThrownBy(() -> commandService.handle(
                registerCommand("20111111111", "c2@autonorte.pe", "c2")))
                .isInstanceOf(DuplicateRucException.class);
    }

    @Test
    void rejectsDuplicateEmail() {
        commandService.handle(registerCommand("20222222222", "dup@autonorte.pe", "d1"));
        assertThatThrownBy(() -> commandService.handle(
                registerCommand("20333333333", "dup@autonorte.pe", "d2")))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void rejectsDuplicateUsername() {
        commandService.handle(registerCommand("20444444444", "e1@autonorte.pe", "dupuser"));
        assertThatThrownBy(() -> commandService.handle(
                registerCommand("20555555555", "e2@autonorte.pe", "dupuser")))
                .isInstanceOf(DuplicateUsernameException.class);
    }
}
