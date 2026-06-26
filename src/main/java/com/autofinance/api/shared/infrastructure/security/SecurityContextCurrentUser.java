package com.autofinance.api.shared.infrastructure.security;

import com.autofinance.api.shared.interfaces.rest.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/** Reads the authenticated {@link TenantPrincipal} from the Spring Security context. */
@Component
public class SecurityContextCurrentUser implements CurrentUser {

    @Override
    public UUID dealershipId() {
        return principal().dealershipId();
    }

    @Override
    public UUID userId() {
        return principal().userId();
    }

    private TenantPrincipal principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof TenantPrincipal principal) {
            return principal;
        }
        throw new IllegalStateException("No authenticated tenant principal in the security context");
    }
}
