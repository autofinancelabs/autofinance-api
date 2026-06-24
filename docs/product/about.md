# AutoFinance — Plataforma de planes de pago para crédito vehicular

> Documento de producto (brief). Resume **qué es**, **qué problema resuelve** y **hasta dónde
> llega** AutoFinance. Es el punto de entrada para entender el alcance antes del modelado de
> dominio y el código. El vocabulario usado aquí está definido en
> [lenguaje_ubicuo.md](lenguaje_ubicuo.md).

## Qué es

AutoFinance es el **backend (REST API)** de un sistema que construye y conserva **planes de pago
de crédito vehicular en Perú**, calculados por el **método francés vencido ordinario** (meses de
30 días, año de 360) bajo la modalidad **Compra Inteligente** (cuota *balloon* / cuotón / valor
residual diferido al final).

El sistema se construye **desde la perspectiva de la entidad financiera** que ofrece el crédito:
es una herramienta operativa de la entidad, no una aplicación para el público. La entidad
registra a sus clientes y las ofertas vehiculares, configura las condiciones del financiamiento,
genera el cronograma y obtiene los indicadores de transparencia exigidos.

## Problema que resuelve

| Dolor actual | Cómo lo aborda AutoFinance |
|---|---|
| El cálculo manual o en Excel del cronograma (cuotón, gracia, seguros, costos) es laborioso y propenso a error. | Motor de cálculo exacto y **reproducible** que sigue las fórmulas del método francés y de la Compra Inteligente. |
| La norma de transparencia del Sistema Financiero Peruano (SBS) exige mostrar indicadores (TCEA, VAN, TIR, desglose de seguros y costos) de forma fiable. | Calcula y expone esos indicadores de manera consistente con el cronograma. |
| La información de clientes, ofertas y simulaciones suele estar dispersa. | Centraliza clientes, ofertas vehiculares y simulaciones en una única fuente persistente, editable y trazable. |

## Propuesta de valor

- **Cronograma exacto y reproducible** del método francés vencido ordinario + Compra Inteligente
  (cuotón).
- **Multimoneda mono-divisa**: cada operación se denomina íntegramente en Soles (PEN) **o**
  Dólares (USD), definida al inicio. (Sin tipo de cambio; ver *Alcance y límites*.)
- **Tasa configurable**: efectiva, o **nominal** indicando su **capitalización**.
- **Periodos de gracia** total (`T`) y parcial (`P`) definidos al inicio de la operación.
- **Indicadores desde la óptica del deudor** (VAN y TIR) más la **batería de transparencia SBS**
  (TCEA, seguro de desgravamen, seguro contra todo riesgo, GPS, portes, gastos administrativos).
- **Trazabilidad**: toda operación queda registrada y puede editarse y volver a guardarse.

## Alcance y límites

| Dentro del alcance (IN) | Fuera del alcance (OUT) |
|---|---|
| Acceso autenticado (login/password obligatorio). | Otros métodos de amortización (alemán, americano, peruano): son referencia teórica, no producto. |
| Gestión (CRUD) de clientes. | Originación/desembolso real del crédito. |
| Gestión (CRUD) de ofertas vehiculares. | Pasarela o registro de pagos reales. |
| Configuración del financiamiento (moneda, tipo de tasa + capitalización, gracia, % cuota inicial, % cuotón, plazo). | Scoring o evaluación crediticia. |
| Motor de simulación: cronograma francés + Compra Inteligente (balloon). | Conversión de tipo de cambio (FX) PEN↔USD. |
| Indicadores: VAN/TIR óptica del deudor, TCEA, transparencia SBS. | Interfaz de usuario (UI): AutoFinance es **backend REST**; las pantallas del informe académico son diseño, no parte de esta API. |
| Persistencia y trazabilidad de las operaciones. | |

## Perspectiva

Conviene no confundir tres planos:

| Plano | A quién corresponde |
|---|---|
| **Punto de vista del producto/sistema** | La **entidad financiera** (el sistema es su herramienta). |
| **Perspectiva de cálculo de VAN y TIR** | El **deudor** (los indicadores se calculan desde su óptica). |
| **Segmento objetivo** | La **entidad financiera**, representada por el **asesor de crédito** (ver [segmentos_objetivo.md](segmentos_objetivo.md)). |

## Stakeholders

| Stakeholder | Rol |
|---|---|
| Entidad financiera | Dueña del producto y de la operación; perspectiva del sistema. |
| Asesor de crédito | Opera el sistema: registra cliente y oferta, configura y genera la simulación. |
| Deudor / comprador | **Beneficiario final**: recibe la oferta; su óptica define VAN/TIR y la transparencia. No opera el sistema. |
| Contexto académico | Curso de Finanzas e Ingeniería Económica; el sistema es parte del entregable. |

## Tecnología de un vistazo

| Aspecto | Elección |
|---|---|
| Tipo | Backend REST API |
| Framework | Spring Boot 4.1 |
| Lenguaje | Java 25 |
| Base de datos | PostgreSQL |
| Persistencia | Spring Data JPA |
| Utilitario | Lombok |
| Enfoque de diseño | Domain-Driven Design (DDD) |
