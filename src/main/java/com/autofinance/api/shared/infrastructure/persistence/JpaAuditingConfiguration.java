package com.autofinance.api.shared.infrastructure.persistence;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables JPA auditing (createdAt/updatedAt) in a dedicated configuration rather than on the main
 * application class, so web-slice tests ({@code @WebMvcTest}) don't try to build the JPA auditing
 * handler (which needs a non-empty JPA metamodel).
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}
