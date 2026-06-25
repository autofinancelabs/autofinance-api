<h1 align="center">AutoFinance API</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white" alt="Java 25">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot 4.1">
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/architecture-DDD-blue" alt="Architecture: DDD">
  <img src="https://img.shields.io/badge/status-in%20design-yellow" alt="Status: in design">
</p>

Backend (REST API) to build and persist **vehicle-credit payment plans in Peru**, computed with the
**French amortization method** (*vencido ordinario*, 30/360) under the **"Compra Inteligente"
balloon** modality (deferred final installment / *cuotón*). It is the **quotation tool** for
**vehicle dealerships** (multi-tenant — each dealership has its own account and isolated data) and
exposes the **SBS transparency indicators** together with **VAN and TIR (NPV/IRR) from the debtor's
perspective**.

## Features

- Exact, reproducible schedule: French method + Compra Inteligente (cuotón), with total (`T`) and
  partial (`P`) grace periods.
- Single-currency: each operation entirely in Soles (PEN) **or** Dollars (USD); no FX.
- Configurable rate: effective, or nominal stating its capitalization.
- Indicators: VAN/TIR from the debtor's perspective, plus the SBS transparency battery (TCEA,
  credit-life insurance, all-risk insurance, GPS, shipping fees, admin fees).
- **Multi-tenant**: many dealerships on one app, each with its own account and data isolated via
  Hibernate `@TenantId`.
- Persistence and traceability of every quote (register, edit, reopen, re-save).

> Scope: this is a **backend** (quotation tool) for vehicle dealerships. Out of scope: other
> amortization methods, FX, real payments, scoring/credit-history, account billing, and UI. Details in
> [docs/product/about.md](docs/product/about.md).

## Stack

Java 25 · Spring Boot 4.1 · Spring Data JPA · PostgreSQL · Lombok · Bean Validation · Maven ·
Domain-Driven Design (4 bounded contexts across 4 layers).

## Requirements

- JDK 25
- A running PostgreSQL instance

## Getting started

```bash
./mvnw clean package      # compile, run tests and build the jar
./mvnw test               # tests only
./mvnw spring-boot:run    # start the API
```

> The PostgreSQL datasource is not yet configured in `src/main/resources/application.yaml`; it is
> part of the pending infrastructure work.

## Documentation

| Folder                                     | Contents                                                                                        |
|--------------------------------------------|-------------------------------------------------------------------------------------------------|
| [`docs/product/`](docs/product/)           | What it is and for whom: brief, glossary (ubiquitous language), backlog, segments.              |
| [`docs/ddd/`](docs/ddd/)                   | Domain model (DDD): bounded contexts, discovery and tactical model.                             |
| [`docs/architecture/`](docs/architecture/) | C4 architecture (Structurizr) and database model.                                               |
| [`docs/guides/`](docs/guides/)             | **Financial reference** (formulas, derivations, worked examples): source of truth for the math. |
| [`docs/report/`](docs/report/)             | Academic-report material (formula summary, algorithm, test datasets).                           |
| [`AGENTS.md`](AGENTS.md)                   | Guide for AI agents working in the repository.                                                  |

## Status

**Design/documentation phase**: the model is described in `docs/` and implementing the domain is the
next step. The product's core is the calculation engine (the **Credit Simulation** context).

## Academic context

Deliverable for the **Finanzas e Ingeniería Económica** course (UPC). The system is one part of the
report; the screens shown in the report are design, not part of this API.
