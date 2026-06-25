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

Un único `@RestControllerAdvice` (`creditsimulation/interfaces/rest/GlobalExceptionHandler`) usa
`ErrorResponse.create(...)` (RFC 7807 `ProblemDetail`). Las excepciones de dominio no conocen HTTP:

| Status                     | Casos                                                                                                                                                                                                                                            |
|----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `400 Bad Request`          | `InvalidSimulationConfigurationException`, `CurrencyMismatchException`, `PercentageOutOfRangeException`, `MissingCapitalizationException`, `IllegalArgumentException` (parseo de enums), validación del body, header de tenant ausente/ inválido |
| `422 Unprocessable Entity` | `ScheduleNotBalancedException`, `IrrNotBracketedException` (request válida, el cálculo no converge)                                                                                                                                              |
| `404 Not Found`            | `GET /{id}` sin coincidencia en el dealership actual                                                                                                                                                                                             |

## OpenAPI

Documentado vía springdoc + Scalar UI (`@Tag`/`@Operation` en el controller). Ver `scalar.enabled` en
`application.yaml`.
