# Arquitectura C4

> Vista de arquitectura del sistema en el modelo **C4** (contexto → contenedores → componentes). La
> fuente renderizable es [workspace.dsl](workspace.dsl) (Structurizr DSL); este documento la explica.
> Se apoya en [bounded-contexts.md](../ddd/bounded-contexts.md) y
> [domain-model.md](../ddd/domain-model.md). El nivel de código (clases) ya está en
> [credit-simulation-class-diagram.puml](../diagrams/credit-simulation-class-diagram.puml).

## Cómo renderizar

`workspace.dsl` se abre con **Structurizr** (Lite vía Docker, o el editor en structurizr.com). Genera
tres vistas: `SystemContext`, `Container` y `Component`. El nivel 4 (código) no se modela en DSL: se
usa el diagrama de clases PlantUML de la Fase 3.

## Nivel 1 — System Context

| Elemento                          | Rol                                                                                                                                                                |
|-----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Asesor de ventas (person)         | Opera el sistema por cuenta de su **concesionaria**: registra cliente/oferta, ingresa las condiciones y genera cotizaciones. **No** se usa "usuario" como persona. |
| AutoFinance API (software system) | El sistema que construimos.                                                                                                                                        |
| Identity Provider (external)      | Provee identidad/sesión; el negocio lo consume **tal cual** (Conformist).                                                                                          |

El asesor se autentica contra el Identity Provider y opera AutoFinance API; AutoFinance verifica la
sesión contra el proveedor.

## Nivel 2 — Containers

| Container | Tecnología                | Responsabilidad                                                                                                                            |
|-----------|---------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| REST API  | Spring Boot 4.1 / Java 25 | Expone los casos de uso; aloja los 4 bounded contexts en 4 capas DDD.                                                                      |
| Database  | PostgreSQL                | Persiste los agregados (FKs reales intra-agregado; referencias by-id sin FK); columna `dealership_id` (tenant) en las tablas multi-tenant. |

El Identity Provider permanece como sistema externo.

## Nivel 3 — Components (REST API)

Los componentes reflejan los **bounded contexts** y, para el core, las **4 capas DDD**.

### Credit Simulation (core)

| Componente                       | Capa             | Pieza DDD                                                               |
|----------------------------------|------------------|-------------------------------------------------------------------------|
| Simulations Controller           | interfaces       | Adaptador REST de entrada.                                              |
| Simulation Command/Query Service | application      | Orquesta el caso de uso (sin reglas de negocio).                        |
| CreditSimulation Aggregate       | domain           | Raíz: configuración + cronograma + indicadores; enforce de invariantes. |
| ScheduleCalculator               | domain (service) | Construye las n filas + liquidación del cuotón.                         |
| IndicatorsCalculator             | domain (service) | VAN/TIR/TCEA + COK del periodo + eco de tasas.                          |
| CreditSimulationFactory          | domain           | Ensambla una configuración válida.                                      |
| CreditSimulationRepository       | domain (port)    | Contrato de persistencia.                                               |
| JPA CreditSimulation Repository  | infrastructure   | Implementa el port con Spring Data JPA.                                 |

Las dependencias apuntan **hacia adentro** (interfaces → application → domain ← infrastructure); el
dominio no depende de nada externo.

### Supporting / generic

| Componente                  | Subdominio                                                                                                                                         |
|-----------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| Clients Component           | supporting (`Client`).                                                                                                                             |
| Vehicle Offers Component    | supporting (`VehicleOffer`).                                                                                                                       |
| Identity & Access Component | generic (`User`/`Session`/`Dealership`); Conformist; **resuelve la concesionaria (tenant) de la sesión** y delega el auth en el Identity Provider. |

## Decisiones reflejadas en el modelo

- **`IndicatorsCalculator` es un componente del core, no un contenedor/contexto aparte:** VAN/TIR/TCEA
  se calculan sobre los flujos del mismo agregado, en la misma transacción. Coincide con la decisión de
  [bounded-contexts.md](../ddd/bounded-contexts.md) (Indicators = servicio de dominio).
- **Las flechas del core a Clients y Vehicle Offers son ACL by-id** (tag `acl`, punteadas): el core solo
  pasa `ClientId`/`VehicleOfferId` y recibe datos mínimos (precio, moneda, validez); no importa sus
  modelos. Refleja el patrón Customer/Supplier + ACL del context map.
- **IAM es un sistema externo (Conformist):** la identidad/sesión se consume tal cual; el componente
  `Identity & Access` solo media con el proveedor.
- **Multi-tenant (`@TenantId`):** IAM resuelve el `DealershipId` (concesionaria) de la sesión; un
  `CurrentTenantIdentifierResolver` lo expone y Hibernate **aísla y rellena** la columna `dealership_id`
  en `clients`, `vehicle_offers` y `credit_simulations` (esquema compartido). Es una preocupación de
  infraestructura, transversal a los contextos.
- **Persistencia en infraestructura:** el `CreditSimulationRepository` es un *port* del dominio; su
  implementación JPA vive en infrastructure y habla con la base PostgreSQL. El esquema está en
  [database-model.md](database-model.md).

## Tags y estilos

| Tag        | Efecto                                                      |
|------------|-------------------------------------------------------------|
| `database` | Forma cilindro (PostgreSQL).                                |
| `core`     | Color destacado para los componentes del Credit Simulation. |
| `external` | Gris para el Identity Provider.                             |
| `acl`      | Relaciones punteadas para las referencias by-id.            |
