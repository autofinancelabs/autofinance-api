package com.autofinance.api.creditsimulation;

import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Capitalization;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Currency;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/** The three golden datasets from docs/report/datos-de-prueba.md as commands. */
public final class GoldenDatasets {

    private GoldenDatasets() {
    }

    private static List<GraceType> grace(int total, int partial, int none) {
        List<GraceType> plan = new ArrayList<>();
        plan.addAll(Collections.nCopies(total, GraceType.TOTAL));
        plan.addAll(Collections.nCopies(partial, GraceType.PARTIAL));
        plan.addAll(Collections.nCopies(none, GraceType.NONE));
        return plan;
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }

    /** D1 — Plan 36 Compra Inteligente (PEN): balloon, grace 3T+3P, embedded credit-life insurance, TNA 15% daily, COK 50%. */
    public static GenerateSimulationCommand d1() {
        return new GenerateSimulationCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                bd("16000"), Currency.PEN,
                bd("0.15"), RateType.NOMINAL, Capitalization.DAILY,
                bd("0.20"), bd("0.40"),
                36, 30, 360,
                grace(3, 3, 30),
                bd("100"), bd("75"), bd("0"), bd("0"),
                bd("0.000490"), bd("4.00"), bd("20.00"), bd("3.50"), bd("3.50"),
                bd("0.50"),
                true);
    }

    /** D2 — simple French (PEN): no balloon/grace/costs, TEA 9%, n=3, COK 12%. */
    public static GenerateSimulationCommand d2() {
        return new GenerateSimulationCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                bd("15000"), Currency.PEN,
                bd("0.09"), RateType.EFFECTIVE, null,
                bd("0.20"), bd("0.00"),
                3, 30, 360,
                grace(0, 0, 3),
                bd("0"), bd("0"), bd("0"), bd("0"),
                bd("0"), bd("0"), bd("0"), bd("0"), bd("0"),
                bd("0.12"),
                false);
    }

    /** D3 — 60 months (PEN): 3 total grace, costs, no balloon, TEA 9%, COK 5%, VAN < 0. */
    public static GenerateSimulationCommand d3() {
        return new GenerateSimulationCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                bd("65000"), Currency.PEN,
                bd("0.09"), RateType.EFFECTIVE, null,
                bd("0.20"), bd("0.00"),
                60, 30, 360,
                grace(3, 0, 57),
                bd("100"), bd("50"), bd("0"), bd("30"),
                bd("0.000450"), bd("5.42"), bd("0"), bd("20.00"), bd("40.00"),
                bd("0.05"),
                false);
    }
}
