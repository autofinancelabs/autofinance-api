package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TermTest {

    @Test
    void derivesInstallmentsPerYear() {
        Term term = Term.of(36, 30, 360);
        assertThat(term.installmentsPerYear()).isEqualTo(12);
    }

    @Test
    void rejectsNonPositiveInstallments() {
        assertThatThrownBy(() -> Term.of(0, 30, 360)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsInconsistentInstallmentsPerYear() {
        assertThatThrownBy(() -> new Term(36, 30, 99, 360)).isInstanceOf(IllegalArgumentException.class);
    }
}
