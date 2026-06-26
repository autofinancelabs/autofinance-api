package com.autofinance.api.clients.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * A client's contact data: email, phone and address. All optional in v1 (plain Strings). Absent contact
 * data is modelled as a {@code null} {@code ContactInfo} on the aggregate — see {@link #of}.
 */
@Embeddable
public record ContactInfo(
        @Column(name = "contact_email") String email,
        @Column(name = "contact_phone") String phone,
        @Column(name = "contact_address") String address
) {

    /** Builds contact info from optional inputs; returns {@code null} when none is provided. */
    public static ContactInfo of(String email, String phone, String address) {
        if (isBlank(email) && isBlank(phone) && isBlank(address)) {
            return null;
        }
        return new ContactInfo(email, phone, address);
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
