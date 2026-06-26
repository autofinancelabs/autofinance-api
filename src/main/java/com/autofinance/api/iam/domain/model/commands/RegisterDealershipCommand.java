package com.autofinance.api.iam.domain.model.commands;

/**
 * Raw inputs to register a dealership account and its first user, in one transaction. Carries primitives
 * so the application/REST boundary can map flat input directly; the application layer hashes
 * {@code rawPassword} into a {@code PasswordHash} and the factories build the VOs.
 */
public record RegisterDealershipCommand(
        String name,
        String ruc,
        String contactEmail,
        String userEmail,
        String username,
        String rawPassword
) {
}
