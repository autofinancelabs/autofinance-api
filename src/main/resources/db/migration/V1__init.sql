-- AutoFinance — initial schema (V1). Multi-tenant (dealership_id), French + Compra Inteligente engine.
-- Only `credit_simulations` (+ child tables) is mapped to a JPA @Entity in this slice; the other
-- tables are created up front (full schema) for the upcoming Clients/VehicleOffers/IAM slices.
-- Audit columns are `timestamp` (java.util.Date -> JDBC TIMESTAMP). Child tables are pluralized to
-- match the snake-case + pluralizing physical naming strategy.

-- ---------------------------------------------------------------------------
-- Identity & Access (generic) — tenant registry + users
-- ---------------------------------------------------------------------------
CREATE TABLE dealerships (
    id             uuid PRIMARY KEY,
    name           varchar(255) NOT NULL,
    ruc            varchar(11)  NOT NULL UNIQUE,
    contact_email  varchar(255),
    created_at     timestamp    NOT NULL,
    updated_at     timestamp    NOT NULL,
    version        bigint       NOT NULL
);

CREATE TABLE users (
    id             uuid PRIMARY KEY,
    dealership_id  uuid         NOT NULL REFERENCES dealerships (id) ON DELETE CASCADE,
    email          varchar(255) NOT NULL UNIQUE,
    username       varchar(100) NOT NULL UNIQUE,
    password_hash  varchar(255) NOT NULL,
    created_at     timestamp    NOT NULL,
    updated_at     timestamp    NOT NULL,
    version        bigint       NOT NULL
);

-- ---------------------------------------------------------------------------
-- Clients (supporting)
-- ---------------------------------------------------------------------------
CREATE TABLE clients (
    id                  uuid PRIMARY KEY,
    dealership_id       uuid         NOT NULL,
    document_id_type    varchar(10)  NOT NULL,
    document_id_number  varchar(20)  NOT NULL,
    contact_email       varchar(255),
    contact_phone       varchar(30),
    contact_address     varchar(255),
    created_at          timestamp    NOT NULL,
    updated_at          timestamp    NOT NULL,
    version             bigint       NOT NULL,
    CONSTRAINT uq_clients_document UNIQUE (dealership_id, document_id_type, document_id_number)
);

-- ---------------------------------------------------------------------------
-- Vehicle Offers (supporting)
-- ---------------------------------------------------------------------------
CREATE TABLE vehicle_offers (
    id                   uuid PRIMARY KEY,
    dealership_id        uuid          NOT NULL,
    vehicle_make         varchar(80)   NOT NULL,
    vehicle_model        varchar(80)   NOT NULL,
    vehicle_year         integer       NOT NULL,
    sale_price_amount    numeric(18,2) NOT NULL,
    sale_price_currency  varchar(3)    NOT NULL,
    plan_name            varchar(40),
    plan_installments    integer,
    created_at           timestamp     NOT NULL,
    updated_at           timestamp     NOT NULL,
    version              bigint        NOT NULL,
    CONSTRAINT ck_offer_price_positive CHECK (sale_price_amount > 0),
    CONSTRAINT ck_offer_currency       CHECK (sale_price_currency IN ('PEN','USD'))
);

-- ---------------------------------------------------------------------------
-- Credit Simulation (core, aggregate root)
-- ---------------------------------------------------------------------------
CREATE TABLE credit_simulations (
    id                              uuid PRIMARY KEY,
    dealership_id                   uuid NOT NULL,
    -- by-id references (no cross-aggregate FK)
    client_id                       uuid NOT NULL,
    vehicle_offer_id                uuid NOT NULL,
    -- Money sale_price
    sale_price_amount               numeric(18,2)  NOT NULL,
    sale_price_currency             varchar(3)     NOT NULL,
    -- Rate
    rate_value                      numeric(18,10) NOT NULL,
    rate_type                       varchar(10)    NOT NULL,
    rate_capitalization             varchar(12),
    -- Percentages
    initial_percentage              numeric(18,6)  NOT NULL,
    balloon_percentage              numeric(18,6)  NOT NULL,
    -- Term
    number_of_installments          integer NOT NULL,
    frequency_days                  integer NOT NULL,
    installments_per_year           integer NOT NULL,
    days_per_year                   integer NOT NULL,
    -- Cost of capital (Rate VO -> 3 columns)
    cost_of_capital_value           numeric(18,10) NOT NULL,
    cost_of_capital_type            varchar(10)    NOT NULL,
    cost_of_capital_capitalization  varchar(12),
    -- Derived money
    loan_amount_amount              numeric(18,2)  NOT NULL,
    loan_amount_currency            varchar(3)     NOT NULL,
    financed_balance_amount         numeric(18,12) NOT NULL,
    financed_balance_currency       varchar(3)     NOT NULL,
    -- Indicators (@Embedded flat)
    npv                             numeric(18,2),
    periodic_irr                    numeric(18,8),
    tcea                            numeric(18,6),
    effective_annual_rate           numeric(18,8),
    periodic_rate                   numeric(18,8),
    periodic_cost_of_capital        numeric(18,8),
    -- jsonb snapshots
    schedule                        jsonb,
    summary                         jsonb,
    -- state + concurrency + audit
    state                           varchar(12) NOT NULL,
    version                         bigint      NOT NULL,
    created_at                      timestamp   NOT NULL,
    updated_at                      timestamp   NOT NULL,
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
CREATE INDEX ix_sim_client ON credit_simulations (dealership_id, client_id);

-- Child: grace plan (pluralized table name)
CREATE TABLE grace_periods (
    credit_simulation_id uuid    NOT NULL,
    period_index         integer NOT NULL,
    grace_type           varchar(10) NOT NULL,
    PRIMARY KEY (credit_simulation_id, period_index),
    CONSTRAINT fk_grace_sim FOREIGN KEY (credit_simulation_id)
        REFERENCES credit_simulations (id) ON DELETE CASCADE,
    CONSTRAINT ck_grace_type CHECK (grace_type IN ('NONE','TOTAL','PARTIAL'))
);

-- Child: flexible costs (pluralized table name)
CREATE TABLE credit_simulation_costs (
    credit_simulation_id uuid    NOT NULL,
    cost_index           integer NOT NULL,
    name                 varchar(100)   NOT NULL,
    value                numeric(18,10) NOT NULL,
    basis                varchar(16)    NOT NULL,
    timing               varchar(16)    NOT NULL,
    embedded             boolean        NOT NULL,
    PRIMARY KEY (credit_simulation_id, cost_index),
    CONSTRAINT fk_cost_sim FOREIGN KEY (credit_simulation_id)
        REFERENCES credit_simulations (id) ON DELETE CASCADE,
    CONSTRAINT ck_cost_basis  CHECK (basis IN ('FIXED','ON_BALANCE','ON_SALE_PRICE')),
    CONSTRAINT ck_cost_timing CHECK (timing IN ('INITIAL','PERIODIC')),
    CONSTRAINT ck_cost_value  CHECK (value >= 0)
);
