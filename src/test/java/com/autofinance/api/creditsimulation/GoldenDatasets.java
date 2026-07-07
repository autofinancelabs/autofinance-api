package com.autofinance.api.creditsimulation;

import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Cost;
import com.autofinance.api.shared.domain.model.valueobjects.Currency;
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
        List<Cost> costs = List.of(
                Cost.initial("notario", bd("100")),
                Cost.initial("registral", bd("75")),
                Cost.onBalance("desgravamen", bd("0.000490"), true),
                Cost.fixedPeriodic("riesgo", bd("4.00")),
                Cost.fixedPeriodic("gps", bd("20.00")),
                Cost.fixedPeriodic("portes", bd("3.50")),
                Cost.fixedPeriodic("gastosAdm", bd("3.50")));
        return new GenerateSimulationCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                bd("16000"), Currency.PEN,
                bd("0.15"), RateType.NOMINAL, 1, null, // daily capitalization = 1 day; annual quote (TNA)
                bd("0.20"), bd("0.40"),
                36, 30, 360,
                grace(3, 3, 30),
                costs,
                bd("0.50"));
    }

    /** D2 — simple French (PEN): no balloon/grace/costs, TEA 9%, n=3, COK 12%. */
    public static GenerateSimulationCommand d2() {
        return new GenerateSimulationCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                bd("15000"), Currency.PEN,
                bd("0.09"), RateType.EFFECTIVE, null, null,
                bd("0.20"), bd("0.00"),
                3, 30, 360,
                grace(0, 0, 3),
                List.of(),
                bd("0.12"));
    }

    /** D3 — 60 months (PEN): 3 total grace, costs, no balloon, TEA 9%, COK 5%, VAN < 0. */
    public static GenerateSimulationCommand d3() {
        List<Cost> costs = List.of(
                Cost.initial("notario", bd("100")),
                Cost.initial("registral", bd("50")),
                Cost.initial("comision", bd("30")),
                Cost.onBalance("desgravamen", bd("0.000450"), false),
                Cost.onSalePrice("riesgo", bd("0.001")), // annual TSR (0.10%); ≈5.42/period at 30/360

                Cost.fixedPeriodic("portes", bd("20.00")),
                Cost.fixedPeriodic("gastosAdm", bd("40.00")));
        return new GenerateSimulationCommand(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                bd("65000"), Currency.PEN,
                bd("0.09"), RateType.EFFECTIVE, null, null,
                bd("0.20"), bd("0.00"),
                60, 30, 360,
                grace(3, 0, 57),
                costs,
                bd("0.05"));
    }
}
