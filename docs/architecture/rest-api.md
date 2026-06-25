# REST API — Credit Simulation

Adaptador de entrada del contexto **Credit Simulation** (`creditsimulation/interfaces/rest`). Sigue la
convención del skill `ddd-playbook`: controller delgado, `resources` (DTOs), `transform` (assemblers),
servicios de comando/consulta como puertos del dominio, y un `@RestControllerAdvice` que traduce las
excepciones de dominio a HTTP.

## Multi-tenant: header `X-Dealership-Id`

Toda request lleva el header **`X-Dealership-Id`** (UUID del concesionario = tenant). `TenantFilter`
(`shared/infrastructure/multitenancy`) lo coloca en `TenantContext` para el resolver `@TenantId` de
Hibernate (aísla los datos por dealership) y lo limpia al terminar. El controller lo lee además vía
`@RequestHeader` requerido para construir el comando; si falta o es inválido → **400**. El body **no**
lleva `dealershipId`. (Cuando exista auth, el header se reemplaza por el claim del JWT.)

## Endpoints (`/api/v1/credit-simulations`)

| Método | Ruta                | Descripción                          | Respuesta                            |
|--------|---------------------|--------------------------------------|--------------------------------------|
| `POST` | `/`                 | Genera una cotización y la persiste  | `201` + `SimulationResource`         |
| `GET`  | `/{id}`             | Recupera una cotización por id       | `200` + `SimulationResource` / `404` |
| `GET`  | `/?clientId={uuid}` | Lista las cotizaciones de un cliente | `200` + `[SimulationResource]`       |

Todas las lecturas/escrituras quedan acotadas al dealership del header. Tras un `POST`, el controller
**re-consulta** por id para que la respuesta refleje el snapshot almacenado
(flujo: *resource → assembler → command → commandService → id → queryService → entity → assembler → resource*).

## Request — `GenerateSimulationResource`

Espeja `GenerateSimulationCommand` **menos `dealershipId`**. Los enums viajan como `String`
(`currency`, `rateType`, `capitalization`, `gracePlan[]`, y `basis`/`timing` de cada costo) y se mapean a
enums de dominio en el assembler; un valor inválido → **400**. Validación Bean Validation
(`@NotNull/@Positive/@NotEmpty`, costos con `@Valid`). Campos: `clientId`, `vehicleOfferId`, `salePrice`,
`currency`, `rateValue`, `rateType`, `capitalization?`, `initialPercentage`, `balloonPercentage`,
`numberOfInstallments`, `frequencyDays`, `daysPerYear`, `gracePlan[]`, `costs[]` (`CostResource`),
`costOfCapitalAnnual`.

## Response — `SimulationResource`

Vista completa del snapshot: ids, configuración (`MoneyResource`/`RateResource`/`TermResource`,
porcentajes), `loanAmount`/`financedBalance`, `indicators` (`IndicatorsResource`), el cronograma
(`schedule[]` de `ScheduleRowResource`, cada fila con `appliedCosts[]`), `summary` (`SummaryResource`
con `totalsPerCost`) y `state`. Sin tipos de dominio: enums expuestos como `String`.

## Errores

Todo error sale como **RFC 9457 `ProblemDetail`** (`application/problem+json`), nunca con stack trace
(`server.error.include-stacktrace: never`). Un único `@RestControllerAdvice`
(`creditsimulation/interfaces/rest/GlobalExceptionHandler extends ResponseEntityExceptionHandler`) maneja
tanto las excepciones de dominio (que no conocen HTTP) como las de Spring MVC (header faltante, validación,
JSON ilegible), y las etiqueta con un **`code`** estable del catálogo `ErrorCode`. **El frontend reacciona
al `code`, no al `detail`** (mensaje para devs) ni al status. Sin i18n en backend: la traducción de copy es
del frontend, por `code`.

Cuerpo: `type`, `title`, `status`, `detail`, `instance`, `code`, `timestamp` y — en validación de body —
`errors[] = {field, message}`.

| `code` | Status | Casos |
|--------|--------|-------|
| `MISSING_TENANT` | `400` | Header `X-Dealership-Id` ausente |
| `MALFORMED_REQUEST` | `400` | JSON ilegible, UUID/tipo inválido |
| `VALIDATION_FAILED` | `400` | Bean Validation del body (`errors[]`); parseo de enums (`IllegalArgumentException`) |
| `INVALID_SIMULATION_CONFIGURATION` | `400` | Invariantes cruzadas (inicial+balloon<1, gracia, etc.) |
| `PERCENTAGE_OUT_OF_RANGE` | `400` | `Percentage` fuera de `[0,1)` |
| `CURRENCY_MISMATCH` | `400` | Monedas incompatibles |
| `MISSING_CAPITALIZATION` | `400` | Tasa `NOMINAL` sin capitalización |
| `SCHEDULE_NOT_BALANCED` | `422` | El cronograma no cuadra (request válida) |
| `IRR_NOT_BRACKETED` | `422` | La TIR no converge (request válida) |
| `INTERNAL_ERROR` | `500` | Fallback de cualquier error no contemplado (sin filtrar internals) |

`GET /{id}` sin coincidencia en el dealership actual → `404` (sin cuerpo).

Cualquier excepción no mapeada cae en un `@ExceptionHandler(Exception.class)` → `500 INTERNAL_ERROR`
con `detail` genérico (no se filtra el mensaje real). Los 5xx se loguean con stack en el servidor
(`log.error`); los 4xx a `debug`.

## OpenAPI

Documentado vía springdoc + Scalar UI (`@Tag`/`@Operation`/`@ApiResponses` en el controller). Las respuestas
de error se documentan con el schema `ProblemDetail` (`ApiErrorSchema`), que expone el enum de `code` y la
forma de `errors[]`, para que el consumidor vea el catálogo completo. Ver `scalar.enabled` en `application.yaml`.
