package com.autofinance.api.iam.domain.model.commands;

/** Raw inputs to sign in. {@code identifier} is the username or the email; the app verifies the password. */
public record SignInCommand(String identifier, String password) {
}
