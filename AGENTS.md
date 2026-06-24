# AGENTS.md — autofinance-api

AutoFinance is the **backend REST API** for building and persisting **vehicle-credit payment
plans (cronogramas) in Peru**, computed with the **French method (*vencido ordinario*, 30/360)**
under the **"Compra Inteligente" balloon** modality (a deferred final installment / *cuotón*).
It is a tool for the **lending entity** (operational, not a consumer app) and exposes the **SBS
transparency indicators** plus **VAN/TIR from the debtor's perspective**.

> **Status: documented, not yet implemented.** `src/` is a fresh Spring Boot skeleton (only
> `AutofinanceApiApplication.java` + a smoke test). What to build is specified in **`docs/`**: the
> product brief (`product/`), the DDD model (`ddd/`), the C4 architecture (`architecture/`) and the
> finance references (`guides/`). Implementing a feature = turning those into code — **read the
> relevant doc before writing code.** (`docs/report/` was written for the academic *informe*; use it as a
> supporting reference, **not** as the source of truth — see *Where things live*.) There is no prior `CLAUDE.md`/`.cursorrules`/etc.; this file is
> the entry point for agents.

## Stack & build

**Java 25 · Spring Boot 4.1 · Maven (wrapper) · PostgreSQL + Spring Data JPA · Lombok · Bean
Validation.** Package root: `com.autofinance.api`.

```bash
./mvnw clean package      # compile + run tests + build jar
./mvnw test               # tests only
./mvnw spring-boot:run    # run (needs a PostgreSQL datasource in application.yaml — not configured yet)
```

- **Spring Boot 4 starter names** differ from older guides: `spring-boot-starter-webmvc` (not
  `-web`), and the test slices `…-webmvc-test`, `…-validation-test`, `…-data-jpa-test`. Use these names.
- `src/main/resources/application.yaml` currently holds only `spring.application.name`; the
  datasource/JPA config is upcoming infrastructure work.

## Big-picture architecture (DDD, 4 layers)

Four **bounded contexts** — the value and the modeling effort concentrate in one:

| Context               | Type       | What it does                                                                     |
|-----------------------|------------|----------------------------------------------------------------------------------|
| **Credit Simulation** | **core**   | The engine: configuration → French+balloon schedule (grace, costs) → indicators. |
| Clients               | supporting | CRUD of the debtor.                                                              |
| Vehicle Offers        | supporting | CRUD of the vehicle offer (sale price, currency).                                |
| Identity & Access     | generic    | Login/session; **Conformist** to an external Identity Provider.                  |

The core references the other contexts **only by ID** (`ClientId`, `VehicleOfferId`) through an
**Anti-Corruption Layer** — it never imports `Client`/`VehicleOffer`, only pulls minimal data
(`salePrice`, `currency`, validity). Why: `docs/ddd/bounded-contexts.md` (context map) and
`docs/architecture/c4-architecture.md` (C4).

Each context maps to the **4 DDD layers**; dependencies point **inward** (domain depends on nothing):

| Layer            | Holds                                                                                                                                                                           |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `domain`         | `CreditSimulation` aggregate root, value objects, `ScheduleCalculator` & `IndicatorsCalculator` services, `CreditSimulationRepository` **interface**, factory, internal events. |
| `application`    | use-case handlers (`GenerateSimulation`, `SaveSimulation`, …) — orchestration, no business rules.                                                                               |
| `infrastructure` | JPA implementation of repositories + persistence.                                                                                                                               |
| `interfaces`     | REST controllers + DTOs.                                                                                                                                                        |

## The core domain model (read `docs/ddd/domain-model.md`)

- **Aggregate root `CreditSimulation`** groups *configuration + schedule + indicators* in **one
  transaction**, because the reconciliation invariant spans every row. States:
  `Draft → Configured → Generated → Saved → Reopened → (Reconfigured → Generated) → Saved`.
- **Value objects carry the rules**: `Money` (BigDecimal + `Currency`, forbids cross-currency ops),
  `Rate` (`value` + `RateType` NOMINAL/EFFECTIVE + optional `Capitalization`; **owns**
  `toEffectiveAnnual()` / `toPeriodicRate(...)` — rate conversion is **VO behavior, not a service**),
  `Percentage` (∈ [0,1)), `Term`, `GraceConfiguration` / `GraceType` (NONE/TOTAL/PARTIAL ≙ S/T/P),
  `InitialCosts`, `PeriodicCosts` (TSD/TSR/GPS/portes/admin), `ScheduleRow` (**a row is a VO**, not an
  entity), `Indicators` (npv/periodicIrr/tcea), `DiscountFactor`, `BalloonPresentValue`.
- **Domain services (stateless)**: `ScheduleCalculator` builds the `n` rows + the cuotón block + its
  settlement; `IndicatorsCalculator` computes VAN/TIR/TCEA + the period COK.
- **Invariants** enforced in the aggregate (strong consistency, no corrective policies):
  1. `initialPercentage + balloonPercentage < 1`
  2. capitalization required **iff** `RateType = NOMINAL`
  3. `(totalGrace + partialGrace) < numberOfInstallments`
  4. single currency ∈ {PEN, USD} across the whole operation (**no FX**)
  5. `loanAmount > 0`
  6. after generating: last `closingBalance ≈ 0`; `installment = interest + amortization` in `S`
     periods; the cuotón grows `SI×(1+i)` and is settled at the end.

Clients & Vehicle Offers are **light CRUD** (root + a couple VOs + repository) — don't over-model them.

## Financial conventions (the part that's easy to get wrong)

**The authoritative financial references — the real formulas, derivations and worked examples — are
the `docs/guides/*.md`** (tasa simple/compuesta/efectiva, método francés, balloon/Compra Inteligente,
VAN/TIR, indicadores de rentabilidad). Go there for any formula or financial rule; that is the source
of truth. `docs/report/marco-conceptual-formulas.md` is a **summary/index** the academic report uses
to select the v1 formulas (notation, the French+balloon shape, `j = i + TSD`, grace handling) and
map back into the guides — a handy reference, **but not the authority**: when in doubt, the guide wins.

- **30/360**: 30-day months, 360-day year. `m = 360 / capitalization_days`.
- **Precision**: compute with **`BigDecimal`, high internal scale (≥ 12), `HALF_UP`**, and **round
  only at output** (money → 2 decimals; rates → 6–8). VAN/TIR run on the **unrounded** internal
  flows. ⇒ The docs' schedule tables show 2-decimal numbers that sometimes don't add up exactly
  (±0.01) — that is **display rounding, not a bug**; the engine must reconcile the last balance to 0
  internally.
- **Perspective duality**: the *system* is the lender's, but **VAN/TIR are the debtor's**
  (`VAN = loan − Σ installment_t/(1+COK)^t`; `VAN > 0` ⇒ the loan is cheap vs the debtor's COK).
  See `docs/guides/van-tir.md` §7.
- **Reproducibility**: the engine's output must match the **worked examples in the guides** (e.g.
  the Plan 36 balloon case in `metodo-frances-compra-inteligente-balloon.md`);
  `docs/report/datos-de-prueba.md` collects these as the informe's test datasets (≥ 2 required).

## Conventions & gotchas

- **DDD modeling follows the `ddd-playbook` skill** (`.agents/skills/ddd-playbook/`); every decision
  in `docs/ddd/` cites a playbook rule (entity vs VO, aggregate boundaries, by-id refs). Follow it
  when extending the model.
- **Domain identifiers in English** (`CreditSimulation`, `ScheduleRow`, `GraceType`); domain
  prose/docs in Spanish.
- **Docs filenames are kebab-case** (`tasa-interes-simple.md`, …). New `.md`/`.dsl`/`.puml` under
  `docs/` follow suit; when renaming, update cross-references and verify links. Do **not** rename
  convention files (`SKILL.md`, `HELP.md`, `README.md`) or external Excel filenames.
- **Diagrams**: C4 source is `docs/architecture/workspace.dsl` (Structurizr — render with Structurizr
  Lite via Docker); class/ER level is `docs/diagrams/*.puml` (PlantUML). DB schema:
  `docs/architecture/database-model.md` (real FKs intra-aggregate; by-id refs **without** FK).

## Where things live

For the **code**, the authoritative references are `product/` (scope), `ddd/` (domain model),
`architecture/` (structure) and `guides/` (finance). **`docs/report/` was written for the academic
*informe*; you may use it as a secondary reference, but it is not the source of truth.**

```
docs/product/      what & for whom — about, lenguaje-ubicuo (glossary), product-backlog, segmentos-objetivo
docs/ddd/          domain model (strategic + tactical) — bounded-contexts, domain-discovery, domain-model   ← read before coding the domain
docs/architecture/ how it's structured — C4 (c4-architecture + workspace.dsl), database-model
docs/guides/       AUTHORITATIVE finance reference — the real formulas, derivations & worked examples (método francés, balloon, tasas, VAN/TIR, indicadores). Source of truth for all math.
docs/report/       academic *informe* material — usable as a secondary reference, not the source of truth — marco-conceptual-formulas (formula summary), algoritmo (pseudocode), analisis-de-datos (data analysis), datos-de-prueba (test datasets)
src/main/java/com/autofinance/api/   Spring Boot app (domain implementation = next phase)
```
