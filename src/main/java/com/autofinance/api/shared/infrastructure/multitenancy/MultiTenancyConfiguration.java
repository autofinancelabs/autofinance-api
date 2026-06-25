package com.autofinance.api.shared.infrastructure.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * Registers the {@link CurrentTenantResolver} bean. Spring Boot auto-detects a single
 * {@link CurrentTenantIdentifierResolver} bean and wires it into Hibernate, enabling the
 * {@code @TenantId} discriminator filtering used to isolate data per dealership.
 */
@Configuration
public class MultiTenancyConfiguration {

    @Bean
    public CurrentTenantIdentifierResolver<UUID> currentTenantIdentifierResolver() {
        return new CurrentTenantResolver();
    }
}
