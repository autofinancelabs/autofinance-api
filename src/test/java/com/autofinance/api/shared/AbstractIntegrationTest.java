package com.autofinance.api.shared;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Base for integration tests: boots the full Spring context against an ephemeral PostgreSQL
 * (Testcontainers). Flyway runs V1 and Hibernate {@code validate} runs against it — this is the real
 * schema gate. Skipped automatically when Docker is unavailable.
 *
 * <p>The container is a singleton started once and shared across all integration-test classes: with
 * Spring's context cache the same datasource must stay alive across classes, so we manage its lifecycle
 * manually (no {@code @Container}, never stopped) instead of per-class.
 */
@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
public abstract class AbstractIntegrationTest {

    @ServiceConnection
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:18.3-alpine3.23");

    static {
        POSTGRES.start();
    }
}
