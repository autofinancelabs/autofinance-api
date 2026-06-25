package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GraceConfigurationTest {

    @Test
    void countsAndIndexesGraceTypes() {
        GraceConfiguration grace = new GraceConfiguration(List.of(
                GraceType.TOTAL, GraceType.TOTAL, GraceType.PARTIAL, GraceType.NONE));
        assertThat(grace.size()).isEqualTo(4);
        assertThat(grace.totalCount()).isEqualTo(2);
        assertThat(grace.partialCount()).isEqualTo(1);
        assertThat(grace.at(1)).isEqualTo(GraceType.TOTAL);
        assertThat(grace.at(4)).isEqualTo(GraceType.NONE);
    }

    @Test
    void rejectsEmptyPlan() {
        assertThatThrownBy(() -> new GraceConfiguration(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void noneFactoryFillsWithNone() {
        GraceConfiguration grace = GraceConfiguration.none(3);
        assertThat(grace.totalCount()).isZero();
        assertThat(grace.partialCount()).isZero();
        assertThat(grace.at(2)).isEqualTo(GraceType.NONE);
    }
}
