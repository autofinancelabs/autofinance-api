# Modelo de base de datos (entidad-relación)

> Modelo relacional para PostgreSQL: **mapeo tabla↔dominio**, **DDL** y **diagrama ER**. Deriva del
> modelo táctico [domain-model.md](../ddd/domain-model.md) y respeta la precisión del diccionario
> [analisis-de-datos.md](../report/analisis-de-datos.md). El diagrama ER también está en PlantUML en
> [database-er-diagram.puml](../diagrams/database-er-diagram.puml).

## Reglas de mapeo (DDD → JPA → PostgreSQL)

| Concepto DDD                  | Mapeo                                                                                                                                                           |
|-------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Raíz de agregado              | `@Entity` → tabla; identidad `@EmbeddedId` (VO tipado) → PK `uuid`.                                                                                             |
| Value object escalar          | `@Embeddable` record → **columnas embebidas** en la tabla del dueño (Money = `*_amount` + `*_currency`).                                                        |
| Referencia a otro agregado    | id tipado `@Embedded` → **columna** (`client_id`) **sin FK** (frontera ACL).                                                                                    |
| Colección propia del agregado | `@ElementCollection` → **tabla hija** con FK real a la raíz (`ON DELETE CASCADE`).                                                                              |
| Enum                          | `varchar` + `CHECK IN (...)` (`@Enumerated(STRING)`).                                                                                                           |
| Discriminador de tenant       | `@TenantId` sobre `dealership_id` → columna que Hibernate **auto-filtra y auto-rellena** con la concesionaria de la sesión (`CurrentTenantIdentifierResolver`). |

## Mapeo tabla ↔ dominio

| Tabla                | Origen DDD                                    | Notas                                                                                                |
|----------------------|-----------------------------------------------|------------------------------------------------------------------------------------------------------|
| `dealerships`        | `Dealership` (IAM, generic) — **tenant**      | Cuenta de la concesionaria; registro de tenants (la tabla **no** es @TenantId).                      |
| `users`              | `User` (IAM, generic)                         | Credenciales mínimas; `dealership_id` (FK) → su concesionaria.                                       |
| `clients`            | `Client` (supporting)                         | VOs `DocumentId`, `ContactInfo` embebidos; `dealership_id` (`@TenantId`).                            |
| `vehicle_offers`     | `VehicleOffer` (supporting)                   | `Vehicle`, `SalePrice` (Money), `Plan` embebidos; `dealership_id` (`@TenantId`).                     |
| `credit_simulations`     | `CreditSimulation` (core, raíz)               | VOs escalares embebidos; `client_id`/`vehicle_offer_id` by-id sin FK; `dealership_id` (`@TenantId`). El **cronograma** (`schedule`) y el **resumen** (`summary`) viven como **columnas `jsonb`** (snapshots) en esta misma tabla. |
| `grace_periods`          | `grace: List<GraceType>` (wrap `GraceConfiguration`) | Tabla hija **ordenada** (`period_index`); PK `(credit_simulation_id, period_index)`.            |
| `credit_simulation_costs`| `costs: List<Cost>` (wrap `Costs`)            | Tabla hija **ordenada** (`cost_index`) de costos flexibles; PK `(credit_simulation_id, cost_index)`. |

## Precisión (alineada con el diccionario de datos)

| Tipo de dato                                     | PostgreSQL       |
|--------------------------------------------------|------------------|
| Money (montos de salida)                         | `numeric(18,2)`  |
| Montos de precisión interna (`financed_balance`) | `numeric(18,12)` |
| Tasas / `tsd` / `cok`                            | `numeric(18,10)` |
| Porcentajes                                      | `numeric(18,6)`  |
| `tcea`                                           | `numeric(18,6)`  |
| `tir` (periodic_irr) y ecos de tasa              | `numeric(18,8)`  |
| Conteos / periodos                               | `integer`        |
| Identidad                                        | `uuid`           |

## DDL (PostgreSQL)

> **Nota — modelo de costos flexible y snapshots (implementado).** Los costos no son columnas fijas:
> son una **lista de `Cost`** `{ name, value, basis, timing, embedded }` por simulación, persistida en
> la tabla hija **`credit_simulation_costs`** (ordenada por `cost_index`). No existen `schedule_row` ni
> `schedule_row_applied_cost`: el **cronograma** se guarda como una columna **`jsonb`** `schedule`
> (`List<ScheduleRow>`, con el desglose `appliedCosts` anidado) y el **resumen** acumulado como una
> columna **`jsonb`** `summary` (`SimulationSummary`, incl. `totalsPerCost`). Ambos son *snapshots*
> serializados por Jackson (`ScheduleRow` y `AppliedCost` son `record` planos, sin anotaciones JPA). El
> DDL de abajo refleja el esquema **implementado** y validado por Hibernate (espejo de `V1__init.sql`).

```sql
-- Identity & Access (generic) — cuenta/tenant + usuarios
CREATE TABLE dealerships (
    id             uuid PRIMARY KEY,                       -- el tenant
    name           varchar(255) NOT NULL,
    ruc            varchar(11)  NOT NULL UNIQUE,           -- identificación de la concesionaria
    contact_email  varchar(255),
    created_at     timestamp    NOT NULL,
    updated_at     timestamp    NOT NULL
);

CREATE TABLE users (
    id             uuid PRIMARY KEY,
    dealership_id  uuid NOT NULL REFERENCES dealerships (id) ON DELETE CASCADE,  -- un usuario -> una concesionaria (FK; NO @TenantId)
    email          varchar(255) NOT NULL UNIQUE,
    username       varchar(100) NOT NULL UNIQUE,
    password_hash  varchar(255) NOT NULL,
    created_at     timestamp    NOT NULL,
    updated_at     timestamp    NOT NULL
);

-- Clients (supporting)
CREATE TABLE clients (
    id                  uuid PRIMARY KEY,
    dealership_id       uuid NOT NULL,                  -- @TenantId (concesionaria)
    document_id_type    varchar(10)  NOT NULL,          -- VO DocumentId
    document_id_number  varchar(20)  NOT NULL,
    contact_email       varchar(255),                   -- VO ContactInfo
    contact_phone       varchar(30),
    contact_address     varchar(255),
    created_at          timestamp    NOT NULL,
    updated_at          timestamp    NOT NULL,
    CONSTRAINT uq_clients_document UNIQUE (dealership_id, document_id_type, document_id_number)
);

-- Vehicle Offers (supporting)
CREATE TABLE vehicle_offers (
    id                   uuid PRIMARY KEY,
    dealership_id        uuid NOT NULL,                  -- @TenantId (concesionaria)
    vehicle_make         varchar(80)   NOT NULL,         -- VO Vehicle
    vehicle_model        varchar(80)   NOT NULL,
    vehicle_year         integer       NOT NULL,
    sale_price_amount    numeric(18,2) NOT NULL,         -- SalePrice = Money
    sale_price_currency  varchar(3)    NOT NULL,
    plan_name            varchar(40),                    -- VO Plan
    plan_installments    integer,
    created_at           timestamp     NOT NULL,
    updated_at           timestamp     NOT NULL,
    CONSTRAINT ck_offer_price_positive CHECK (sale_price_amount > 0),
    CONSTRAINT ck_offer_currency       CHECK (sale_price_currency IN ('PEN','USD'))
);

-- Credit Simulation (core, aggregate root)
CREATE TABLE credit_simulations (
    id                          uuid PRIMARY KEY,
    dealership_id               uuid NOT NULL,           -- @TenantId (concesionaria)
    -- referencias by-id (SIN FK: frontera ACL)
    client_id                   uuid NOT NULL,
    vehicle_offer_id            uuid NOT NULL,
    -- Money: sale_price
    sale_price_amount           numeric(18,2)  NOT NULL,
    sale_price_currency         varchar(3)     NOT NULL,
    -- Rate
    rate_value                  numeric(18,10) NOT NULL,
    rate_type                   varchar(10)    NOT NULL,
    rate_capitalization         varchar(12),               -- obligatorio si NOMINAL (invariante de dominio)
    -- Percentages
    initial_percentage          numeric(18,6)  NOT NULL DEFAULT 0,
    balloon_percentage          numeric(18,6)  NOT NULL DEFAULT 0,
    -- Term
    number_of_installments      integer NOT NULL,
    frequency_days              integer NOT NULL,
    installments_per_year       integer NOT NULL,
    days_per_year               integer NOT NULL,
    -- Cost of capital (Rate VO -> 3 columnas)
    cost_of_capital_value           numeric(18,10) NOT NULL,
    cost_of_capital_type            varchar(10)    NOT NULL,
    cost_of_capital_capitalization  varchar(12),
    -- Derived monetary results
    loan_amount_amount          numeric(18,2)  NOT NULL,
    loan_amount_currency        varchar(3)     NOT NULL,
    financed_balance_amount     numeric(18,12) NOT NULL,
    financed_balance_currency   varchar(3)     NOT NULL,
    -- Indicators (@Embedded plano)
    npv                         numeric(18,2),
    periodic_irr                numeric(18,8),
    tcea                        numeric(18,6),
    effective_annual_rate       numeric(18,8),
    periodic_rate               numeric(18,8),
    periodic_cost_of_capital    numeric(18,8),
    -- Snapshots jsonb (cronograma + resumen acumulado)
    schedule                    jsonb,
    summary                     jsonb,
    -- State + concurrency + audit
    state                       varchar(12) NOT NULL,
    version                     bigint      NOT NULL,                  -- optimistic locking (@Version)
    created_at                  timestamp   NOT NULL,
    updated_at                  timestamp   NOT NULL,

    CONSTRAINT ck_sim_initial_pct CHECK (initial_percentage >= 0 AND initial_percentage < 1),
    CONSTRAINT ck_sim_balloon_pct CHECK (balloon_percentage >= 0 AND balloon_percentage < 1),
    CONSTRAINT ck_sim_pct_sum     CHECK (initial_percentage + balloon_percentage < 1),
    CONSTRAINT ck_sim_loan_pos    CHECK (loan_amount_amount > 0),
    CONSTRAINT ck_sim_sale_pos    CHECK (sale_price_amount > 0),
    CONSTRAINT ck_sim_n           CHECK (number_of_installments >= 1),
    CONSTRAINT ck_sim_freq        CHECK (frequency_days > 0),
    CONSTRAINT ck_sim_currency    CHECK (sale_price_currency IN ('PEN','USD')),
    CONSTRAINT ck_sim_rate_type   CHECK (rate_type IN ('NOMINAL','EFFECTIVE')),
    CONSTRAINT ck_sim_state       CHECK (state IN ('DRAFT','CONFIGURED','GENERATED','SAVED','REOPENED'))
);
CREATE INDEX ix_sim_client ON credit_simulations (dealership_id, client_id);   -- soporta findByClientId dentro del tenant (historial, E7)

-- Grace configuration (tabla hija ordenada; nombre pluralizado por la naming strategy)
CREATE TABLE grace_periods (
    credit_simulation_id  uuid    NOT NULL,
    period_index          integer NOT NULL,             -- @OrderColumn → preserva la secuencia S/T/P
    grace_type            varchar(10) NOT NULL,
    PRIMARY KEY (credit_simulation_id, period_index),
    CONSTRAINT fk_grace_sim FOREIGN KEY (credit_simulation_id)
        REFERENCES credit_simulations (id) ON DELETE CASCADE,
    CONSTRAINT ck_grace_type CHECK (grace_type IN ('NONE','TOTAL','PARTIAL'))
);

-- Flexible costs (tabla hija ordenada; lista de Cost por simulación)
CREATE TABLE credit_simulation_costs (
    credit_simulation_id uuid    NOT NULL,
    cost_index           integer NOT NULL,             -- @OrderColumn
    name                 varchar(100)   NOT NULL,
    value                numeric(18,10) NOT NULL,
    basis                varchar(16)    NOT NULL,       -- FIXED / ON_BALANCE / ON_SALE_PRICE
    timing               varchar(16)    NOT NULL,       -- INITIAL / PERIODIC
    embedded             boolean        NOT NULL,
    PRIMARY KEY (credit_simulation_id, cost_index),
    CONSTRAINT fk_cost_sim FOREIGN KEY (credit_simulation_id)
        REFERENCES credit_simulations (id) ON DELETE CASCADE,
    CONSTRAINT ck_cost_basis  CHECK (basis IN ('FIXED','ON_BALANCE','ON_SALE_PRICE')),
    CONSTRAINT ck_cost_timing CHECK (timing IN ('INITIAL','PERIODIC')),
    CONSTRAINT ck_cost_value  CHECK (value >= 0)
);
```

## Invariantes: base de datos vs dominio

| Invariante                                                                                            | Dónde                                                |
|-------------------------------------------------------------------------------------------------------|------------------------------------------------------|
| `%CI∈[0,1)`, `%balloon∈[0,1)`, `%CI+%balloon<1`, `loan>0`, `sale>0`, `n≥1`, `frequency_days>0`, enums | **CHECK** en la base.                                |
| Capitalización obligatoria si `rate_type = NOMINAL`                                                   | **Dominio** (multi-columna condicional).             |
| Moneda única en toda la operación                                                                     | **Dominio** (cruza varias columnas/tablas).          |
| Cuadre del cronograma (último saldo ≈ 0; `installment = interest + amortization`)                     | **Dominio** (lo garantiza el motor).                 |
| Aislamiento por concesionaria (cada fila pertenece a su `dealership_id`)                              | **Infra** (`@TenantId` de Hibernate filtra/rellena). |

## Diagrama ER

FKs reales **intra-agregado** (`credit_simulations` → `grace_periods`, `credit_simulation_costs`). El
cronograma (`schedule`) y el resumen (`summary`) **no** son tablas hijas: son columnas `jsonb` en
`credit_simulations`. Las relaciones
de `clients` y `vehicle_offers` con `credit_simulations` son **referencias by-id sin FK** (frontera
ACL); se dibujan con cardinalidad pero **no existe integridad referencial forzada** entre agregados.
Todas las tablas de negocio (`clients`, `vehicle_offers`, `credit_simulations`) llevan `dealership_id`
(el **tenant**, vía `@TenantId`); `users` referencia `dealerships` por FK. `dealerships` es el registro
de tenants (no lleva discriminador).

```mermaid
erDiagram
    dealerships {
        uuid id PK
        varchar name
        varchar ruc
    }
    users {
        uuid id PK
        uuid dealership_id FK
        varchar email
        varchar username
        varchar password_hash
    }
    clients {
        uuid id PK
        uuid dealership_id "tenant"
        varchar document_id_type
        varchar document_id_number
        varchar contact_email
    }
    vehicle_offers {
        uuid id PK
        uuid dealership_id "tenant"
        varchar vehicle_make
        varchar vehicle_model
        numeric sale_price_amount
        varchar sale_price_currency
    }
    credit_simulations {
        uuid id PK
        uuid dealership_id "tenant"
        uuid client_id "by-id, sin FK"
        uuid vehicle_offer_id "by-id, sin FK"
        numeric rate_value
        varchar rate_type
        numeric initial_percentage
        numeric balloon_percentage
        integer number_of_installments
        numeric loan_amount_amount
        numeric npv
        numeric periodic_irr
        numeric tcea
        jsonb schedule "snapshot cronograma"
        jsonb summary "snapshot totales"
        varchar state
    }
    grace_periods {
        uuid credit_simulation_id PK,FK
        integer period_index PK
        varchar grace_type
    }
    credit_simulation_costs {
        uuid credit_simulation_id PK,FK
        integer cost_index PK
        varchar name
        numeric value
        varchar basis
        varchar timing
        boolean embedded
    }

    dealerships ||--o{ users : "tiene (FK)"
    dealerships ||--o{ clients : "tenant (@TenantId)"
    dealerships ||--o{ vehicle_offers : "tenant (@TenantId)"
    dealerships ||--o{ credit_simulations : "tenant (@TenantId)"
    credit_simulations ||--o{ grace_periods : "tiene (FK)"
    credit_simulations ||--o{ credit_simulation_costs : "tiene (FK)"
    clients ||--o{ credit_simulations : "by-id (sin FK, ACL)"
    vehicle_offers ||--o{ credit_simulations : "by-id (sin FK, ACL)"
```

> Nota: `users` (asesores, IAM) pertenece a una `dealerships` (FK); su concesionaria es el **tenant**
> que aísla los datos. El registro de "qué usuario creó la simulación" no se modela en v1.

## Nota: migraciones (Flyway) — Fase 5

El DDL de arriba es un **artefacto de documentación**. En la Fase 5 (código) se deriva un
`src/main/resources/db/migration/V1__init.sql` (Flyway), se añade la dependencia Flyway al `pom.xml` y
la **naming strategy snake_case** en el shared kernel. No se crea aún en esta fase.
