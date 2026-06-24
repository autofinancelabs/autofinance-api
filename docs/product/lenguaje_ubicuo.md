# Lenguaje ubicuo (v1)

> Glosario **semilla** del lenguaje común de AutoFinance. Es la fuente de la grafía y el
> significado de los términos usados en [about.md](about.md),
> [segmentos_objetivo.md](segmentos_objetivo.md) y [product_backlog.md](product_backlog.md).
> Evolucionará en las fases de DDD estratégico/táctico.

## Cómo leer este glosario

- Cada término trae una **definición breve** y, cuando aplica, el **contexto/agregado** donde
  probablemente vivirá. Esa asignación es tentativa (semilla), no definitiva.
- **Detalle financiero completo** (fórmulas, ejemplos, derivaciones) vive en `docs/guides/`; aquí
  solo se fija el significado de negocio.
- **Nota de naming:** el término `Usuario` pertenece al futuro contexto de **IAM**
  (autenticación/autorización), **no** se usa como persona en el resto de documentos. La persona
  operativa es el **asesor de crédito**.

## Grupo A — Acceso / Identidad `generic (IAM)`

| Término | Definición |
|---|---|
| Usuario | Identidad de acceso al sistema (concepto de IAM). Reservado para el contexto de autenticación; no se usa como sinónimo de asesor. |
| Credenciales | Usuario y contraseña con los que se autentica el acceso. |
| Login | Acción de autenticarse para obtener una sesión. |
| Sesión | Estado autenticado que habilita operar los endpoints de negocio. |
| Asesor de crédito | Rol operativo de la entidad financiera que opera el sistema (registra, configura, simula). |

## Grupo B — Cliente y oferta vehicular `supporting`

| Término | Definición |
|---|---|
| Entidad financiera | Organización (banco, financiera, concesionaria) que ofrece el crédito; perspectiva del sistema. |
| Cliente / Deudor | Persona que adquiere el vehículo y asume el crédito; **beneficiario final**, no opera el sistema. |
| Oferta vehicular | Conjunto de características del vehículo y su precio que sirven de base al financiamiento. |
| Vehículo | Bien financiado. |
| Precio de venta (PV) | Valor del vehículo; base para la cuota inicial y el cuotón. |
| Plan | Configuración estándar de plazo/condiciones (p. ej. Plan 36 = 36 cuotas). |

## Grupo C — Configuración del crédito `core (config)`

| Término | Definición |
|---|---|
| Moneda | Divisa única de la operación: Soles (PEN) o Dólares (USD). Sin conversión (mono-divisa). |
| Tasa nominal anual (TNA) | Tasa nominal que requiere indicar su capitalización para volverse efectiva. |
| Capitalización | Frecuencia con que la tasa nominal capitaliza (p. ej. diaria, mensual). Obligatoria si la tasa es nominal. |
| Tasa efectiva anual (TEA) | Tasa efectiva en base anual. |
| Tasa efectiva del periodo (TEP) | Tasa efectiva ajustada a la frecuencia de pago: `TEP = (1 + TEA)^(días periodo/días año) − 1`. |
| Tasa efectiva mensual (TEM) | Caso de TEP cuando el periodo es mensual. |
| Tasas equivalentes | Tasas que, en distintas frecuencias, producen el mismo rendimiento efectivo. |
| Cuota inicial (CI) | Pago adelantado del comprador; `CI = PV × % cuota inicial`. |
| % cuota inicial | Porcentaje del precio de venta aportado como cuota inicial. |
| Cuota final / Cuotón / Valor residual (balloon) | Parte del valor diferida al final de la operación; `cuotón = PV × % cuota final`. |
| % cuota final | Porcentaje del precio de venta diferido como cuotón. |
| Plazo (n) | Número total de cuotas/periodos del cronograma. |
| Frecuencia de pago | Cada cuántos días se paga (p. ej. cada 30 días). |
| Convención 30/360 | Meses de 30 días y año de 360 días, propios del método francés vencido ordinario. |

## Grupo D — Gracia `core`

| Término | Definición |
|---|---|
| Periodo de gracia | Periodo en que se difiere total o parcialmente el pago, definido al inicio. |
| Gracia total (`T`) | No se paga cuota ni se amortiza; el interés se capitaliza y el saldo sube. |
| Gracia parcial (`P`) | Se pagan solo intereses; no se amortiza; el saldo se mantiene. |
| Sin gracia (`S`) | Periodo normal: se paga la cuota según el método. |

## Grupo E — Cronograma / motor francés-balloon `core (agregado Plan de Pagos)`

| Término | Definición |
|---|---|
| Plan de pagos / Cronograma | Tabla que muestra, por periodo, cómo se cancela la deuda (interés, amortización, saldo). |
| Método francés vencido ordinario | Sistema de amortización de **cuota constante** en el tramo ordinario, con pago al vencimiento del periodo. |
| Compra Inteligente | Modalidad de financiamiento francés que difiere parte del capital como cuotón (balloon), reduciendo la cuota periódica. |
| Préstamo / Capital financiado (C / VA) | Monto financiado: `PV − cuota inicial + costos iniciales financiados`. |
| Saldo inicial | Saldo de la deuda al comenzar el periodo; base del interés. |
| Saldo final | Saldo tras el pago o la capitalización del periodo. |
| Interés | Costo financiero del periodo: `saldo inicial × TEP`. |
| Amortización | Parte de la cuota que reduce el capital: `cuota − interés`. |
| Cuota / Cuota regular | Pago periódico constante del tramo ordinario (método francés). |
| Cuota del préstamo | Componente financiero del pago: `interés + amortización`. |
| Cuota total | Pago completo del periodo: cuota del préstamo + costos periódicos. |
| Liquidación del cuotón | Pago final que cancela la cuota balloon diferida. |

## Grupo F — Costos y seguros `core (parte del flujo)`

| Término | Definición |
|---|---|
| Costos iniciales | Gastos de formalización (notariales, registrales, tasación, comisiones); pueden financiarse en el préstamo. |
| Costos periódicos | Pagos que acompañan la cuota y no amortizan capital; se pagan también en gracia. |
| Seguro de desgravamen (TSD) | Seguro que cubre la deuda ante fallecimiento/invalidez; se calcula sobre el saldo. |
| Seguro contra todo riesgo (TSR) | Seguro del bien; suele calcularse sobre el precio del vehículo. |
| GPS | Costo periódico del dispositivo de rastreo. |
| Portes | Costo periódico administrativo de envío/gestión. |
| Gastos administrativos | Otros cobros administrativos del periodo. |
| Flujo total / Flujo de caja del periodo | Lo que realmente paga el deudor: `cuota + desgravamen + riesgo + GPS + portes + gastos administrativos`. |

## Grupo G — Indicadores y transparencia `core (servicio de dominio)`

| Término | Definición |
|---|---|
| VAN | Valor Actual Neto desde la óptica del deudor: `Préstamo + Σ Flujo_t/(1+COK)^t`. |
| TIR | Tasa Interna de Retorno: tasa que hace el VAN = 0 (periódica; se anualiza). |
| COK | Costo de Oportunidad del Capital del deudor; tasa de descuento. |
| TCEA | Tasa de Costo Efectivo Anual: `(1 + TIR periódica)^(periodos por año) − 1`. |
| Norma de transparencia SBS | Marco peruano que exige informar el costo real del crédito (TCEA y desglose de seguros/costos). |
| B/C, PRD, VAC, CAUE | **Referencia teórica** del material de indicadores; **no** forman parte del producto v1. |

## Bounded contexts candidatos

Mapa tentativo que liga los grupos a contextos (se refinará en la fase de DDD estratégico):

| Contexto candidato | Subdominio | Grupos / términos principales |
|---|---|---|
| Acceso / IAM | generic | A (Usuario, credenciales, login, sesión) |
| Clientes | supporting | B (cliente/deudor) |
| Ofertas Vehiculares | supporting | B (oferta, vehículo, precio de venta) |
| Financiamiento & Simulación | **core** | C, D, E, F (configuración, gracia, cronograma, costos) — agregado **Plan de Pagos** |
| Indicadores / Transparencia | **core** | G (VAN, TIR, TCEA, transparencia SBS) — posible **servicio de dominio** dentro del contexto de simulación |
