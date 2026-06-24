# Método francés de amortización

## 1. ¿Qué es el método francés?

El **método francés** es un sistema de amortización de préstamos en el que la **cuota del préstamo se mantiene constante** durante el cronograma, siempre que la tasa de interés no cambie y no existan períodos de gracia que modifiquen el saldo.

Cada cuota está formada por dos partes:

```text
Cuota del préstamo = Amortización del capital + Intereses
```

Su comportamiento típico es el siguiente:

| Componente | Comportamiento en el método francés | Explicación |
|---|---|---|
| Cuota del préstamo | Constante | Se calcula como una anualidad financiera. |
| Intereses | Decrecientes | Se calculan sobre el saldo pendiente, que baja en cada período. |
| Amortización | Creciente | Como la cuota es fija y los intereses bajan, cada vez se amortiza más capital. |
| Saldo del préstamo | Decreciente | Se reduce con cada amortización hasta llegar a cero. |
| Costo financiero | Intermedio | Suele ser más costoso que el método alemán, pero menos costoso que el americano. |

El método francés es muy usado porque facilita la planificación del deudor: el pago periódico es estable, aunque internamente cambia la proporción entre intereses y capital.

---

## 2. Variables principales

| Símbolo | Significado |
|---|---|
| `PV` | Precio de venta del activo o bien financiado. |
| `CI` | Cuota inicial. |
| `C` | Monto del préstamo o capital financiado. |
| `TEA` | Tasa efectiva anual. |
| `TEP` o `i` | Tasa efectiva del período de pago. |
| `n` | Número total de cuotas. |
| `R` | Cuota constante del préstamo. |
| `SI_t` | Saldo inicial del período `t`. |
| `I_t` | Interés del período `t`. |
| `A_t` | Amortización del capital del período `t`. |
| `SF_t` | Saldo final del período `t`. |
| `CP_t` | Costos periódicos del período `t`. |
| `CT_t` | Cuota total del período `t`. |

---

## 3. Fórmulas básicas

### 3.1. Monto del préstamo

```text
Préstamo = Precio del activo - Cuota inicial + Costos iniciales
```

Si la cuota inicial está expresada como porcentaje:

```text
Cuota inicial = Precio del activo × % cuota inicial
```

Por tanto:

```text
C = PV - CI + Costos iniciales
```

Cuando no se consideran costos iniciales:

```text
C = PV - CI
```

---

### 3.2. Conversión de TEA a TEP

La tasa efectiva del período depende de la frecuencia de pago.

```text
TEP = (1 + TEA)^(1 / m) - 1
```

Donde `m` es la cantidad de períodos de pago por año.

| Frecuencia | Períodos por año (`m`) | Fórmula |
|---|---:|---|
| Mensual | 12 | `TEP = (1 + TEA)^(1/12) - 1` |
| Bimestral | 6 | `TEP = (1 + TEA)^(1/6) - 1` |
| Trimestral | 4 | `TEP = (1 + TEA)^(1/4) - 1` |
| Cuatrimestral | 3 | `TEP = (1 + TEA)^(1/3) - 1` |
| Semestral | 2 | `TEP = (1 + TEA)^(1/2) - 1` |
| Anual | 1 | `TEP = TEA` |

---

### 3.3. Cuota constante del préstamo

La cuota del préstamo se calcula como una anualidad:

```text
R = C × [ TEP × (1 + TEP)^n ] / [ (1 + TEP)^n - 1 ]
```

Forma equivalente:

```text
R = C × [ TEP / (1 - (1 + TEP)^(-n)) ]
```

Donde:

| Variable | Significado |
|---|---|
| `R` | Cuota constante del préstamo. |
| `C` | Monto del préstamo. |
| `TEP` | Tasa efectiva del período. |
| `n` | Número total de cuotas. |

---

### 3.4. Interés del período

```text
I_t = SI_t × TEP
```

El interés se calcula sobre el saldo inicial del período.

---

### 3.5. Amortización del capital

```text
A_t = R - I_t
```

Como `R` es constante y los intereses disminuyen, la amortización aumenta progresivamente.

---

### 3.6. Saldo final

```text
SF_t = SI_t - A_t
```

Luego, para el siguiente período:

```text
SI_(t+1) = SF_t
```

---

### 3.7. Cuota total con costos periódicos

La cuota del préstamo no siempre coincide con el pago total del deudor. Si existen seguros, comisiones, portes o gastos administrativos, estos se suman al pago periódico.

```text
Cuota total = Cuota del préstamo + Costos periódicos
```

```text
CT_t = R + CP_t
```

Ejemplos de costos periódicos:

```text
Costos periódicos = Comisiones + Portes + Gastos de administración + Seguro de desgravamen + Seguro contra todo riesgo
```

---

### 3.8. Seguro de desgravamen

El seguro de desgravamen cubre la deuda ante riesgos como fallecimiento o invalidez del deudor. Suele calcularse sobre el saldo pendiente del préstamo.

```text
Seguro de desgravamen = Saldo del préstamo × TSD
```

```text
SD_t = SI_t × TSD
```

---

### 3.9. Seguro contra todo riesgo

El seguro contra todo riesgo se calcula usualmente sobre el precio de venta del bien asegurado.

```text
Seguro contra todo riesgo = Precio de venta del bien × TSR
```

```text
STR_t = PV × TSR
```

---

## 4. Fórmula cuando cambia la tasa de interés

Cuando la tasa de interés cambia durante el cronograma, ya no basta con usar una sola cuota para todo el préstamo. En ese caso, se debe recalcular la cuota desde el período en que cambia la tasa, usando el saldo pendiente y el número de cuotas restantes.

```text
R_t = SI_t × [ TEP_t × (1 + TEP_t)^k ] / [ (1 + TEP_t)^k - 1 ]
```

Donde:

| Variable | Significado |
|---|---|
| `R_t` | Nueva cuota desde el período `t`. |
| `SI_t` | Saldo inicial del período en que se recalcula. |
| `TEP_t` | Nueva tasa efectiva del período. |
| `k` | Número de cuotas pendientes, incluyendo la cuota actual. |

Si `n` es el número total de cuotas y `nc` es el número de la cuota actual:

```text
k = n - nc + 1
```

Por tanto:

```text
R = SI × [ TEP × (1 + TEP)^(n - nc + 1) ] / [ (1 + TEP)^(n - nc + 1) - 1 ]
```

---

## 5. Períodos de gracia en el método francés

En un préstamo puede existir un período de gracia o diferimiento. Esto significa que durante ciertos períodos el deudor no paga la cuota completa del préstamo.

### 5.1. Período de gracia total

En la **gracia total**, el deudor no paga intereses ni amortización del préstamo durante el período de gracia. Los intereses generados se capitalizan, es decir, se suman al saldo.

```text
Cuota del préstamo = 0
Amortización = 0
Interés = SI × TEP
Saldo final = SI + Interés
```

```text
SF = SI × (1 + TEP)
```

Este tipo de gracia encarece el préstamo, porque aumenta el saldo pendiente.

---

### 5.2. Período de gracia parcial o normal

En la **gracia parcial**, el deudor no amortiza capital, pero sí paga los intereses del período. Por eso, el saldo no aumenta.

```text
Cuota del préstamo = Interés
Amortización = 0
Interés = SI × TEP
Saldo final = SI
```

Este tipo de gracia evita que el préstamo se encarezca por capitalización de intereses.

---

### 5.3. Costos durante períodos de gracia

Aunque exista gracia total o parcial, los costos periódicos pueden seguir pagándose, según las condiciones del contrato. Por eso, al calcular la **cuota total**, siempre se debe verificar si hay comisiones, seguros, portes u otros gastos aplicables.

---

## 6. Estructura del cronograma de pagos

Un cronograma del método francés debe incluir, como mínimo, las siguientes columnas:

| Columna | Significado |
|---|---|
| Nº | Número de cuota. |
| TEA | Tasa efectiva anual aplicable. |
| TEP | Tasa efectiva del período. |
| Tipo de gracia | `S` sin gracia, `T` gracia total, `P` gracia parcial. |
| Saldo inicial | Deuda al inicio del período. |
| Interés | Costo financiero del período. |
| Cuota del préstamo | Pago financiero antes de costos periódicos. |
| Amortización | Parte de la cuota que reduce capital. |
| Costos periódicos | Seguros, comisiones, portes u otros. |
| Cuota total | Cuota del préstamo más costos periódicos. |
| Saldo final | Deuda pendiente al cierre del período. |

---

## 7. Reglas de cálculo para una hoja de cálculo o sistema

Para cada período `t`:

```text
1. Determinar el saldo inicial.
2. Identificar la TEA vigente.
3. Convertir TEA a TEP.
4. Revisar si el período tiene gracia total, parcial o no tiene gracia.
5. Calcular el interés: I_t = SI_t × TEP_t.
6. Calcular la cuota del préstamo:
   - Si hay gracia total: R_t = 0.
   - Si hay gracia parcial: R_t = I_t.
   - Si no hay gracia: usar la fórmula francesa con saldo y cuotas pendientes.
7. Calcular la amortización:
   - Si hay gracia total: A_t = 0.
   - Si hay gracia parcial: A_t = 0.
   - Si no hay gracia: A_t = R_t - I_t.
8. Calcular el saldo final:
   - Si hay gracia total: SF_t = SI_t + I_t.
   - Si hay gracia parcial: SF_t = SI_t.
   - Si no hay gracia: SF_t = SI_t - A_t.
9. Calcular costos periódicos, si existen.
10. Calcular cuota total: CT_t = R_t + CP_t.
```

---

## 8. Ejemplo 1: Método francés con tasa constante

### Datos

| Dato | Valor |
|---|---:|
| Precio de venta | US$ 1,800,000.00 |
| Cuota inicial | 20% |
| Préstamo | US$ 1,440,000.00 |
| TEA | 9.00% |
| Frecuencia | Semestral |
| Plazo | 4 años |
| Nº de períodos | 8 |
| Método | Francés |

### Cálculo de la tasa semestral

```text
TEP = (1 + 0.09)^(1/2) - 1
TEP = 0.044030651
TEP = 4.4030651%
```

### Cálculo de la cuota

```text
R = 1,440,000 × [0.044030651 × (1 + 0.044030651)^8] / [(1 + 0.044030651)^8 - 1]

R = 217,454.11
```

### Cronograma

| Nº | TEA | TEP | Saldo inicial | Interés | Cuota | Amortización | Saldo final |
|---:|---:|---:|---:|---:|---:|---:|---:|
| 1 | 9.00% | 4.4030651% | 1,440,000.00 | 63,404.14 | 217,454.11 | 154,049.98 | 1,285,950.02 |
| 2 | 9.00% | 4.4030651% | 1,285,950.02 | 56,621.22 | 217,454.11 | 160,832.90 | 1,125,117.13 |
| 3 | 9.00% | 4.4030651% | 1,125,117.13 | 49,539.64 | 217,454.11 | 167,914.47 | 957,202.65 |
| 4 | 9.00% | 4.4030651% | 957,202.65 | 42,146.26 | 217,454.11 | 175,307.86 | 781,894.79 |
| 5 | 9.00% | 4.4030651% | 781,894.79 | 34,427.34 | 217,454.11 | 183,026.78 | 598,868.02 |
| 6 | 9.00% | 4.4030651% | 598,868.02 | 26,368.55 | 217,454.11 | 191,085.57 | 407,782.45 |
| 7 | 9.00% | 4.4030651% | 407,782.45 | 17,954.93 | 217,454.11 | 199,499.19 | 208,283.27 |
| 8 | 9.00% | 4.4030651% | 208,283.27 | 9,170.85 | 217,454.11 | 208,283.27 | 0.00 |

### Lectura del resultado

La cuota permanece constante en US$ 217,454.11. En la primera cuota, la mayor parte corresponde a intereses; hacia el final, casi toda la cuota se convierte en amortización del capital.

---

## 9. Ejemplo 2: Método francés con cambio de tasa

### Datos

| Dato | Valor |
|---|---:|
| Precio de venta | US$ 1,800,000.00 |
| Cuota inicial | 20% |
| Préstamo | US$ 1,440,000.00 |
| TEA inicial | 9.00% durante los primeros 2 años |
| TEA posterior | 8.00% durante los últimos 2 años |
| Frecuencia | Semestral |
| Plazo | 4 años |
| Nº de períodos | 8 |
| Método | Francés |

### Tasas efectivas semestrales

```text
TEP 9% = (1 + 0.09)^(1/2) - 1 = 4.4030651%
TEP 8% = (1 + 0.08)^(1/2) - 1 = 3.9230485%
```

### Criterio de cálculo

Durante las primeras cuatro cuotas se usa la cuota calculada con TEA de 9%. A partir de la cuota 5, se recalcula la cuota usando el saldo pendiente, la nueva tasa de 8% y las cuatro cuotas restantes.

### Cronograma

| Nº | TEA | TEP | Saldo inicial | Interés | Cuota | Amortización | Saldo final |
|---:|---:|---:|---:|---:|---:|---:|---:|
| 1 | 9.00% | 4.4030651% | 1,440,000.00 | 63,404.14 | 217,454.11 | 154,049.98 | 1,285,950.02 |
| 2 | 9.00% | 4.4030651% | 1,285,950.02 | 56,621.22 | 217,454.11 | 160,832.90 | 1,125,117.13 |
| 3 | 9.00% | 4.4030651% | 1,125,117.13 | 49,539.64 | 217,454.11 | 167,914.47 | 957,202.65 |
| 4 | 9.00% | 4.4030651% | 957,202.65 | 42,146.26 | 217,454.11 | 175,307.86 | 781,894.79 |
| 5 | 8.00% | 3.9230485% | 781,894.79 | 30,674.11 | 215,013.72 | 184,339.61 | 597,555.18 |
| 6 | 8.00% | 3.9230485% | 597,555.18 | 23,442.38 | 215,013.72 | 191,571.35 | 405,983.84 |
| 7 | 8.00% | 3.9230485% | 405,983.84 | 15,926.94 | 215,013.72 | 199,086.78 | 206,897.05 |
| 8 | 8.00% | 3.9230485% | 206,897.05 | 8,116.67 | 215,013.72 | 206,897.05 | 0.00 |

### Lectura del resultado

Al bajar la tasa desde el tercer año, la cuota se reduce de US$ 217,454.11 a US$ 215,013.72. El método conserva la lógica francesa, pero la cuota deja de ser única para todo el préstamo porque la tasa cambia.

---

## 10. Ejemplo 3: Método francés con gracia total y gracia parcial

### Datos

| Dato | Valor |
|---|---:|
| Precio de venta | US$ 1,800,000.00 |
| Cuota inicial | 20% |
| Préstamo | US$ 1,440,000.00 |
| TEA inicial | 9.00% durante los primeros 2 años |
| TEA posterior | 8.00% durante los últimos 2 años |
| Frecuencia | Semestral |
| Plazo | 4 años |
| Nº de períodos | 8 |
| Gracia total | 1º período |
| Gracia parcial | 2º y 3º período |
| Método | Francés |

### Criterio de cálculo

| Período | Tipo de gracia | Tratamiento |
|---:|---|---|
| 1 | Gracia total | No se paga cuota; los intereses se capitalizan. |
| 2 y 3 | Gracia parcial | Se pagan solo intereses; no se amortiza capital. |
| 4 al 8 | Sin gracia | Se paga cuota francesa recalculada según saldo, tasa y cuotas restantes. |

### Cronograma

| Nº | TEA | TEP | Gracia | Saldo inicial | Interés | Cuota | Amortización | Saldo final |
|---:|---:|---:|:---:|---:|---:|---:|---:|---:|
| 1 | 9.00% | 4.4030651% | T | 1,440,000.00 | 63,404.14 | 0.00 | 0.00 | 1,503,404.14 |
| 2 | 9.00% | 4.4030651% | P | 1,503,404.14 | 66,195.86 | 66,195.86 | 0.00 | 1,503,404.14 |
| 3 | 9.00% | 4.4030651% | P | 1,503,404.14 | 66,195.86 | 66,195.86 | 0.00 | 1,503,404.14 |
| 4 | 9.00% | 4.4030651% | S | 1,503,404.14 | 66,195.86 | 341,538.35 | 275,342.49 | 1,228,061.65 |
| 5 | 8.00% | 3.9230485% | S | 1,228,061.65 | 48,177.45 | 337,705.42 | 289,527.97 | 938,533.68 |
| 6 | 8.00% | 3.9230485% | S | 938,533.68 | 36,819.13 | 337,705.42 | 300,886.29 | 637,647.40 |
| 7 | 8.00% | 3.9230485% | S | 637,647.40 | 25,015.22 | 337,705.42 | 312,690.20 | 324,957.19 |
| 8 | 8.00% | 3.9230485% | S | 324,957.19 | 12,748.23 | 337,705.42 | 324,957.19 | 0.00 |

### Lectura del resultado

La gracia total del primer período aumenta el saldo de US$ 1,440,000.00 a US$ 1,503,404.14 porque los intereses se capitalizan. En los períodos 2 y 3, la gracia parcial evita que el saldo crezca, ya que se pagan los intereses. Desde el período 4, la cuota aumenta porque queda menos tiempo para amortizar un saldo mayor.

---

## 11. Ejemplo 4: Método francés con costos periódicos

### Datos

| Dato | Valor |
|---|---:|
| Préstamo | US$ 10,000.00 |
| TEA | 12.00% |
| Frecuencia | Mensual |
| Plazo | 12 meses |
| TSD mensual | 0.05% sobre saldo |
| Comisión mensual | US$ 10.00 |

### Paso 1: tasa mensual

```text
TEP = (1 + 0.12)^(1/12) - 1
TEP = 0.009488793
TEP = 0.9488793%
```

### Paso 2: cuota del préstamo

```text
R = 10,000 × [0.009488793 × (1 + 0.009488793)^12] / [(1 + 0.009488793)^12 - 1]

R = 885.62
```

### Primeros períodos

| Nº | Saldo inicial | Interés | Cuota préstamo | Amortización | Seguro desgravamen | Comisión | Cuota total | Saldo final |
|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| 1 | 10,000.00 | 94.89 | 885.62 | 790.73 | 5.00 | 10.00 | 900.62 | 9,209.27 |
| 2 | 9,209.27 | 87.38 | 885.62 | 798.24 | 4.60 | 10.00 | 900.22 | 8,411.03 |
| 3 | 8,411.03 | 79.81 | 885.62 | 805.81 | 4.21 | 10.00 | 899.83 | 7,605.22 |

### Lectura del resultado

La cuota financiera del préstamo es constante: US$ 885.62. Sin embargo, la cuota total cambia ligeramente porque el seguro de desgravamen disminuye junto con el saldo del préstamo.

---

## 12. Fórmulas útiles en Excel o Google Sheets

Supongamos que:

| Celda | Contenido |
|---|---|
| `B2` | TEA |
| `B3` | Frecuencia anual, por ejemplo 12 si es mensual |
| `B4` | Número total de cuotas |
| `B5` | Préstamo |
| `B6` | TSD por período |
| `B7` | Comisión fija por período |

### TEP

```excel
=(1+B2)^(1/B3)-1
```

### Cuota francesa

```excel
=PAGO(TEP; n; -Préstamo)
```

Ejemplo con celdas:

```excel
=PAGO(B8;B4;-B5)
```

También puede escribirse manualmente:

```excel
=B5*(B8*(1+B8)^B4)/((1+B8)^B4-1)
```

### Interés del período

```excel
=SaldoInicial*TEP
```

### Amortización

```excel
=Cuota-Interés
```

### Saldo final

```excel
=SaldoInicial-Amortización
```

### Seguro de desgravamen

```excel
=SaldoInicial*TSD
```

### Cuota total

```excel
=CuotaPrestamo+SeguroDesgravamen+Comision
```

---

## 13. Errores comunes

| Error | Consecuencia | Recomendación |
|---|---|---|
| Usar la TEA directamente como tasa mensual o semestral | La cuota queda mal calculada. | Convertir siempre la TEA a TEP. |
| No recalcular la cuota cuando cambia la tasa | El saldo final puede no llegar a cero. | Recalcular con saldo pendiente y cuotas restantes. |
| Confundir cuota del préstamo con cuota total | Se subestima el pago real. | Agregar costos periódicos. |
| En gracia total, no capitalizar intereses | El saldo queda menor al real. | Sumar los intereses al saldo. |
| En gracia parcial, amortizar capital por error | El saldo baja cuando no debería. | Solo pagar intereses; amortización igual a cero. |
| Redondear demasiado pronto | Aparecen diferencias en la última cuota. | Calcular con varios decimales y redondear al final. |

---

## 14. Resumen operativo

El método francés se trabaja así:

```text
1. Calcular el préstamo.
2. Convertir la TEA a TEP.
3. Calcular la cuota constante con la fórmula de anualidad.
4. Para cada período:
   a. Calcular interés sobre saldo inicial.
   b. Calcular amortización como cuota menos interés.
   c. Restar amortización al saldo.
   d. Agregar costos periódicos si existen.
5. Si cambia la tasa, recalcular la cuota con el saldo pendiente.
6. Si hay gracia total, capitalizar intereses.
7. Si hay gracia parcial, pagar intereses sin amortizar capital.
8. Validar que el saldo final sea cero o muy cercano a cero.
```

En términos financieros, el método francés combina estabilidad de pago con amortización creciente. Es especialmente útil cuando el deudor necesita cuotas previsibles, aunque al inicio pague una proporción mayor de intereses.

---

## 15. Ejemplo 5: Método francés aplicado a la compra de un auto

Este ejemplo muestra cómo construir un cronograma de pagos bajo el método francés cuando se financia la compra de un vehículo. Además, permite calcular el VAN del préstamo usando una tasa de oportunidad del capital y la TIR del financiamiento, que en este contexto representa la TCEA cuando no se agregan costos adicionales.

### Datos del préstamo

| Concepto | Valor |
|---|---:|
| Valor del auto | 15,000.00 |
| Cuota inicial | 20% |
| Cuota inicial en dinero | 3,000.00 |
| Préstamo | 12,000.00 |
| Tasa compensatoria anual, TEA | 9.00% |
| COK anual, TEA | 12.00% |
| Plazo | 3 meses |
| Método | Francés |

### Conversión de tasas anuales a mensuales

La tasa compensatoria mensual se obtiene convirtiendo la TEA de 9% a una tasa efectiva mensual:

```text
TEM = (1 + TEA)^(1/12) - 1
TEM = (1 + 0.09)^(1/12) - 1
TEM = 0.0072073
TEM = 0.72%
```

La COK mensual se obtiene convirtiendo la COK anual de 12%:

```text
COK mensual = (1 + COK anual)^(1/12) - 1
COK mensual = (1 + 0.12)^(1/12) - 1
COK mensual = 0.0094888
COK mensual = 0.95%
```

### Cálculo de la cuota francesa

La cuota se calcula como una anualidad:

```text
R = C × [ i / (1 - (1 + i)^(-n)) ]
```

Donde:

| Variable | Valor |
|---|---:|
| `C` | 12,000.00 |
| `i` | 0.72% mensual |
| `n` | 3 meses |

Sustituyendo:

```text
R = 12,000 × [0.0072073 / (1 - (1 + 0.0072073)^(-3))]
R = 4,057.80
```

Por tanto, la cuota mensual del préstamo es:

```text
Cuota préstamo = 4,057.80
```

En el flujo de caja del deudor, las cuotas se registran como salidas de dinero:

```text
Mes 0 = +12,000.00
Mes 1 = -4,057.80
Mes 2 = -4,057.80
Mes 3 = -4,057.80
```

### Cronograma de pagos

| Concepto / Mes | 0 | 1 | 2 | 3 |
|---|---:|---:|---:|---:|
| Principal o saldo del préstamo | 12,000.00 | 8,028.69 | 4,028.76 | 0.00 |
| Amortización |  | 3,971.31 | 3,999.93 | 4,028.76 |
| Interés |  | 86.49 | 57.87 | 29.04 |
| Cuota préstamo | 12,000.00 | -4,057.80 | -4,057.80 | -4,057.80 |

### Verificación del cronograma

Para cada mes se cumple:

```text
Interés = Saldo inicial × TEM
Amortización = Cuota - Interés
Saldo final = Saldo inicial - Amortización
```

Mes 1:

```text
Interés = 12,000.00 × 0.0072073 = 86.49
Amortización = 4,057.80 - 86.49 = 3,971.31
Saldo final = 12,000.00 - 3,971.31 = 8,028.69
```

Mes 2:

```text
Interés = 8,028.69 × 0.0072073 = 57.87
Amortización = 4,057.80 - 57.87 = 3,999.93
Saldo final = 8,028.69 - 3,999.93 = 4,028.76
```

Mes 3:

```text
Interés = 4,028.76 × 0.0072073 = 29.04
Amortización = 4,057.80 - 29.04 = 4,028.76
Saldo final = 4,028.76 - 4,028.76 = 0.00
```

### VAN aplicado al préstamo

Desde el punto de vista del deudor, el préstamo genera un ingreso inicial y luego pagos futuros. Por eso, el VAN se calcula como:

```text
VAN = Préstamo - Cuota1/(1 + COK)^1 - Cuota2/(1 + COK)^2 - Cuota3/(1 + COK)^3
```

Sustituyendo con COK mensual de 0.95%:

```text
VAN = 12,000 - 4,057.80/(1 + 0.0094888)^1
             - 4,057.80/(1 + 0.0094888)^2
             - 4,057.80/(1 + 0.0094888)^3

VAN = 54.03
```

### Interpretación del VAN

| Resultado | Interpretación |
|---|---|
| `VAN > 0` | El préstamo es conveniente, porque la tasa del préstamo es menor que la COK del deudor. |
| `VAN = 0` | El costo del préstamo es equivalente a la COK. |
| `VAN < 0` | El préstamo no conviene, porque su costo supera la COK. |

En este ejemplo:

```text
VAN = 54.03
```

Como el VAN es positivo, el préstamo resulta financieramente conveniente para el deudor bajo una COK anual de 12%, ya que la TEA compensatoria del préstamo es 9%.

### TIR mensual y TCEA

La TIR del préstamo se obtiene igualando a cero el valor actual de los flujos:

```text
0 = 12,000 - 4,057.80/(1 + TIR)^1 - 4,057.80/(1 + TIR)^2 - 4,057.80/(1 + TIR)^3
```

El resultado es:

```text
TIR mensual = 0.72%
```

Para convertirla a tasa anual:

```text
TCEA = (1 + TIR mensual)^12 - 1
TCEA = (1 + 0.0072073)^12 - 1
TCEA = 9.00%
```

Como no se han incluido costos periódicos ni costos iniciales adicionales, la TCEA coincide con la TEA compensatoria del préstamo.

### Resumen del ejemplo

| Indicador | Resultado |
|---|---:|
| Cuota mensual del préstamo | 4,057.80 |
| VAN | 54.03 |
| TIR mensual | 0.72% |
| TCEA | 9.00% |
| COK mensual | 0.95% |
| COK anual | 12.00% |

### Lectura financiera

El préstamo tiene una TCEA de 9.00%, mientras que la COK del deudor es 12.00%. Esto significa que el financiamiento cuesta menos que el rendimiento exigido por el deudor a su propio capital. Por eso, el VAN del préstamo es positivo y la alternativa de financiarse resulta atractiva bajo estos supuestos.

---

## 16. Ejemplo completo: método francés con costos, seguros y gracia total

Este ejemplo corresponde a un préstamo trabajado bajo el método francés, pero incorpora elementos más realistas que una cuota financiera simple: costos iniciales, seguros, portes, gastos administrativos, períodos de gracia total, VAN, TIR y TCEA.

### Datos de entrada

| Concepto | Valor |
|---|---:|
| Precio de venta del activo | 65,000.00 |
| Cuota inicial | 20.00% |
| Cuota inicial en dinero | 13,000.00 |
| Saldo a financiar del activo | 52,000.00 |
| Costos notariales | 100.00 |
| Costos registrales | 50.00 |
| Tasación | 0.00 |
| Comisión de estudio | 30.00 |
| Comisión de activación | 0.00 |
| Costos iniciales totales | 180.00 |
| Monto del préstamo | 52,180.00 |
| Plazo | 5 años |
| Frecuencia de pago | Cada 30 días |
| Días por año | 360 |
| Cuotas por año | 12 |
| Número total de cuotas | 60 |
| TEA compensatoria | 9.00% |
| TEP mensual | 0.7207323% |
| COK anual | 5.00% |
| COK del período | 0.4074124% |
| Seguro de desgravamen del período, TSD | 0.0450% |
| Seguro contra todo riesgo del período, TSR | 0.008333% |
| Comisión periódica | 0.00 |
| Portes por período | 20.00 |
| Gastos administrativos por período | 40.00 |

### Cálculo del monto del préstamo

El préstamo no solo financia el saldo del activo. También incorpora los costos iniciales de la operación.

```text
Préstamo = Precio del activo - Cuota inicial + Costos iniciales
```

```text
Cuota inicial = 65,000 × 20%
Cuota inicial = 13,000
```

```text
Costos iniciales = 100 + 50 + 0 + 30 + 0
Costos iniciales = 180
```

```text
Préstamo = 65,000 - 13,000 + 180
Préstamo = 52,180
```

### Conversión de tasa

Como la frecuencia de pago es cada 30 días y el año financiero tiene 360 días, existen 12 períodos al año.

```text
TEP = (1 + TEA)^(30/360) - 1
TEP = (1 + 0.09)^(30/360) - 1
TEP = 0.007207323
TEP = 0.7207323%
```

La COK del período se calcula con la misma lógica:

```text
COK período = (1 + 0.05)^(30/360) - 1
COK período = 0.004074124
COK período = 0.4074124%
```

### Tratamiento de la gracia total

En este ejemplo, los tres primeros períodos tienen **gracia total**, representada en el cronograma con la letra `T`.

Durante la gracia total:

```text
Cuota préstamo = 0
Amortización = 0
Interés = Saldo inicial × TEP
Saldo final = Saldo inicial + Interés
```

Por eso, el saldo aumenta durante los tres primeros períodos:

```text
Saldo inicial período 1 = 52,180.00
Saldo final período 1 = 52,556.08
Saldo final período 2 = 52,934.87
Saldo final período 3 = 53,316.39
```

A partir del período 4, el préstamo se paga normalmente bajo el método francés. La cuota financiera del préstamo es:

```text
Cuota préstamo = 1,143.95
```

### Costos periódicos

Además de la cuota del préstamo, el deudor paga seguros, portes y gastos administrativos.

```text
Cuota total = Cuota préstamo + Seguro de desgravamen + Seguro de riesgo + Comisión + Portes + Gastos administrativos
```

El seguro de desgravamen se calcula sobre el saldo inicial del préstamo:

```text
Seguro de desgravamen = Saldo inicial × TSD
```

El seguro contra todo riesgo se calcula sobre el precio de venta del activo:

```text
Seguro de riesgo = Precio del activo × TSR
Seguro de riesgo = 65,000 × 0.000083333
Seguro de riesgo = 5.42
```

### Cronograma resumido

La tabla siguiente muestra los primeros períodos, donde aparece la gracia total, y los últimos períodos, donde se termina de amortizar el préstamo.

| Nº | P.G. | Saldo inicial | Interés | Amortización | Cuota préstamo | Seguro desgrav. | Seguro riesgo | Portes + G. adm. | Saldo final | Flujo |
|---:|:---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| 1 | T | 52,180.00 | 376.08 | 0.00 | 0.00 | 23.48 | 5.42 | 60.00 | 52,556.08 | -88.90 |
| 2 | T | 52,556.08 | 378.79 | 0.00 | 0.00 | 23.65 | 5.42 | 60.00 | 52,934.87 | -89.07 |
| 3 | T | 52,934.87 | 381.52 | 0.00 | 0.00 | 23.82 | 5.42 | 60.00 | 53,316.39 | -89.24 |
| 4 | S | 53,316.39 | 384.27 | 759.68 | 1,143.95 | 23.99 | 5.42 | 60.00 | 52,556.71 | -1,233.36 |
| 5 | S | 52,556.71 | 378.79 | 765.15 | 1,143.95 | 23.65 | 5.42 | 60.00 | 51,791.55 | -1,233.02 |
| 6 | S | 51,791.55 | 373.28 | 770.67 | 1,143.95 | 23.31 | 5.42 | 60.00 | 51,020.88 | -1,232.67 |
| 56 | S | 5,598.12 | 40.35 | 1,103.60 | 1,143.95 | 2.52 | 5.42 | 60.00 | 4,494.52 | -1,211.88 |
| 57 | S | 4,494.52 | 32.39 | 1,111.55 | 1,143.95 | 2.02 | 5.42 | 60.00 | 3,382.96 | -1,211.39 |
| 58 | S | 3,382.96 | 24.38 | 1,119.57 | 1,143.95 | 1.52 | 5.42 | 60.00 | 2,263.40 | -1,210.89 |
| 59 | S | 2,263.40 | 16.31 | 1,127.63 | 1,143.95 | 1.02 | 5.42 | 60.00 | 1,135.76 | -1,210.38 |
| 60 | S | 1,135.76 | 8.19 | 1,135.76 | 1,143.95 | 0.51 | 5.42 | 60.00 | 0.00 | -1,209.88 |

### Resultados financieros del ejemplo

| Indicador | Resultado |
|---|---:|
| Intereses totales | 13,025.03 |
| Amortización total del capital | 53,316.39 |
| Cuotas financieras pagadas | 65,205.03 |
| Seguro de desgravamen total | 813.24 |
| Seguro contra todo riesgo total | 325.00 |
| Portes totales | 1,200.00 |
| Gastos administrativos totales | 2,400.00 |
| Pagos totales del deudor, sin descontar | 69,943.26 |
| TIR del período | 0.9657104% |
| TCEA de la operación | 12.2243% |
| VAN de la operación | -9,420.70 |

### VAN del préstamo

Para evaluar el financiamiento desde la perspectiva del deudor, se considera el préstamo como un flujo positivo inicial y las cuotas totales como flujos negativos.

```text
VAN = Préstamo + Σ [ Flujo_t / (1 + COK período)^t ]
```

Donde:

| Variable | Significado |
|---|---|
| `Préstamo` | Dinero recibido al inicio de la operación. |
| `Flujo_t` | Pago total del período `t`, incluyendo cuota, seguros, portes y gastos. |
| `COK período` | Costo de oportunidad convertido a la frecuencia de pago. |
| `t` | Número del período. |

En este ejemplo:

```text
VAN = -9,420.70
```

La interpretación es directa: el VAN es negativo porque, al descontar todos los pagos con la COK del deudor, el costo económico del financiamiento supera el valor recibido inicialmente. En otras palabras, bajo estas condiciones, el préstamo resulta caro para el deudor.

### TIR y TCEA

La TIR del período es la tasa que hace que el VAN del préstamo sea igual a cero.

```text
0 = Préstamo + Σ [ Flujo_t / (1 + TIR)^t ]
```

En el ejemplo:

```text
TIR del período = 0.9657104%
```

La TCEA se obtiene anualizando la TIR del período:

```text
TCEA = (1 + TIR período)^12 - 1
TCEA = (1 + 0.009657104)^12 - 1
TCEA = 12.2243%
```

La TCEA es mayor que la TEA compensatoria de 9.00% porque incorpora costos adicionales del crédito, como seguros, portes y gastos administrativos.

### Interpretación final

Aunque la TEA compensatoria es 9.00%, la operación completa tiene una TCEA de 12.2243%. Esto ocurre porque el deudor no solo paga intereses, sino también seguros y gastos periódicos. Además, la gracia total inicial capitaliza intereses y aumenta el saldo antes de empezar la amortización normal. Por eso, el ejemplo muestra una diferencia importante entre la **cuota financiera del préstamo** y el **costo real del financiamiento**.

<details>
<summary>Ver cronograma completo de 60 períodos</summary>

| Nº | P.G. | Saldo inicial | Interés | Amortización | Cuota préstamo | Seguro desgrav. | Seguro riesgo | Portes + G. adm. | Saldo final | Flujo |
|---:|:---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| 1 | T | 52,180.00 | 376.08 | 0.00 | 0.00 | 23.48 | 5.42 | 60.00 | 52,556.08 | -88.90 |
| 2 | T | 52,556.08 | 378.79 | 0.00 | 0.00 | 23.65 | 5.42 | 60.00 | 52,934.87 | -89.07 |
| 3 | T | 52,934.87 | 381.52 | 0.00 | 0.00 | 23.82 | 5.42 | 60.00 | 53,316.39 | -89.24 |
| 4 | S | 53,316.39 | 384.27 | 759.68 | 1,143.95 | 23.99 | 5.42 | 60.00 | 52,556.71 | -1,233.36 |
| 5 | S | 52,556.71 | 378.79 | 765.15 | 1,143.95 | 23.65 | 5.42 | 60.00 | 51,791.55 | -1,233.02 |
| 6 | S | 51,791.55 | 373.28 | 770.67 | 1,143.95 | 23.31 | 5.42 | 60.00 | 51,020.88 | -1,232.67 |
| 7 | S | 51,020.88 | 367.72 | 776.22 | 1,143.95 | 22.96 | 5.42 | 60.00 | 50,244.66 | -1,232.32 |
| 8 | S | 50,244.66 | 362.13 | 781.82 | 1,143.95 | 22.61 | 5.42 | 60.00 | 49,462.84 | -1,231.97 |
| 9 | S | 49,462.84 | 356.49 | 787.45 | 1,143.95 | 22.26 | 5.42 | 60.00 | 48,675.39 | -1,231.62 |
| 10 | S | 48,675.39 | 350.82 | 793.13 | 1,143.95 | 21.90 | 5.42 | 60.00 | 47,882.26 | -1,231.27 |
| 11 | S | 47,882.26 | 345.10 | 798.84 | 1,143.95 | 21.55 | 5.42 | 60.00 | 47,083.41 | -1,230.91 |
| 12 | S | 47,083.41 | 339.35 | 804.60 | 1,143.95 | 21.19 | 5.42 | 60.00 | 46,278.81 | -1,230.55 |
| 13 | S | 46,278.81 | 333.55 | 810.40 | 1,143.95 | 20.83 | 5.42 | 60.00 | 45,468.41 | -1,230.19 |
| 14 | S | 45,468.41 | 327.71 | 816.24 | 1,143.95 | 20.46 | 5.42 | 60.00 | 44,652.17 | -1,229.83 |
| 15 | S | 44,652.17 | 321.82 | 822.13 | 1,143.95 | 20.09 | 5.42 | 60.00 | 43,830.04 | -1,229.46 |
| 16 | S | 43,830.04 | 315.90 | 828.05 | 1,143.95 | 19.72 | 5.42 | 60.00 | 43,001.99 | -1,229.09 |
| 17 | S | 43,001.99 | 309.93 | 834.02 | 1,143.95 | 19.35 | 5.42 | 60.00 | 42,167.97 | -1,228.72 |
| 18 | S | 42,167.97 | 303.92 | 840.03 | 1,143.95 | 18.98 | 5.42 | 60.00 | 41,327.94 | -1,228.34 |
| 19 | S | 41,327.94 | 297.86 | 846.08 | 1,143.95 | 18.60 | 5.42 | 60.00 | 40,481.86 | -1,227.96 |
| 20 | S | 40,481.86 | 291.77 | 852.18 | 1,143.95 | 18.22 | 5.42 | 60.00 | 39,629.68 | -1,227.58 |
| 21 | S | 39,629.68 | 285.62 | 858.32 | 1,143.95 | 17.83 | 5.42 | 60.00 | 38,771.35 | -1,227.20 |
| 22 | S | 38,771.35 | 279.44 | 864.51 | 1,143.95 | 17.45 | 5.42 | 60.00 | 37,906.84 | -1,226.81 |
| 23 | S | 37,906.84 | 273.21 | 870.74 | 1,143.95 | 17.06 | 5.42 | 60.00 | 37,036.10 | -1,226.42 |
| 24 | S | 37,036.10 | 266.93 | 877.02 | 1,143.95 | 16.67 | 5.42 | 60.00 | 36,159.09 | -1,226.03 |
| 25 | S | 36,159.09 | 260.61 | 883.34 | 1,143.95 | 16.27 | 5.42 | 60.00 | 35,275.75 | -1,225.64 |
| 26 | S | 35,275.75 | 254.24 | 889.70 | 1,143.95 | 15.87 | 5.42 | 60.00 | 34,386.04 | -1,225.24 |
| 27 | S | 34,386.04 | 247.83 | 896.12 | 1,143.95 | 15.47 | 5.42 | 60.00 | 33,489.93 | -1,224.84 |
| 28 | S | 33,489.93 | 241.37 | 902.58 | 1,143.95 | 15.07 | 5.42 | 60.00 | 32,587.35 | -1,224.43 |
| 29 | S | 32,587.35 | 234.87 | 909.08 | 1,143.95 | 14.66 | 5.42 | 60.00 | 31,678.27 | -1,224.03 |
| 30 | S | 31,678.27 | 228.32 | 915.63 | 1,143.95 | 14.26 | 5.42 | 60.00 | 30,762.64 | -1,223.62 |
| 31 | S | 30,762.64 | 221.72 | 922.23 | 1,143.95 | 13.84 | 5.42 | 60.00 | 29,840.41 | -1,223.21 |
| 32 | S | 29,840.41 | 215.07 | 928.88 | 1,143.95 | 13.43 | 5.42 | 60.00 | 28,911.53 | -1,222.79 |
| 33 | S | 28,911.53 | 208.37 | 935.57 | 1,143.95 | 13.01 | 5.42 | 60.00 | 27,975.96 | -1,222.37 |
| 34 | S | 27,975.96 | 201.63 | 942.32 | 1,143.95 | 12.59 | 5.42 | 60.00 | 27,033.64 | -1,221.95 |
| 35 | S | 27,033.64 | 194.84 | 949.11 | 1,143.95 | 12.17 | 5.42 | 60.00 | 26,084.53 | -1,221.53 |
| 36 | S | 26,084.53 | 188.00 | 955.95 | 1,143.95 | 11.74 | 5.42 | 60.00 | 25,128.58 | -1,221.10 |
| 37 | S | 25,128.58 | 181.11 | 962.84 | 1,143.95 | 11.31 | 5.42 | 60.00 | 24,165.75 | -1,220.67 |
| 38 | S | 24,165.75 | 174.17 | 969.78 | 1,143.95 | 10.87 | 5.42 | 60.00 | 23,195.97 | -1,220.24 |
| 39 | S | 23,195.97 | 167.18 | 976.77 | 1,143.95 | 10.44 | 5.42 | 60.00 | 22,219.20 | -1,219.80 |
| 40 | S | 22,219.20 | 160.14 | 983.81 | 1,143.95 | 10.00 | 5.42 | 60.00 | 21,235.39 | -1,219.36 |
| 41 | S | 21,235.39 | 153.05 | 990.90 | 1,143.95 | 9.56 | 5.42 | 60.00 | 20,244.50 | -1,218.92 |
| 42 | S | 20,244.50 | 145.91 | 998.04 | 1,143.95 | 9.11 | 5.42 | 60.00 | 19,246.46 | -1,218.47 |
| 43 | S | 19,246.46 | 138.72 | 1,005.23 | 1,143.95 | 8.66 | 5.42 | 60.00 | 18,241.23 | -1,218.03 |
| 44 | S | 18,241.23 | 131.47 | 1,012.48 | 1,143.95 | 8.21 | 5.42 | 60.00 | 17,228.75 | -1,217.57 |
| 45 | S | 17,228.75 | 124.17 | 1,019.77 | 1,143.95 | 7.75 | 5.42 | 60.00 | 16,208.97 | -1,217.12 |
| 46 | S | 16,208.97 | 116.82 | 1,027.12 | 1,143.95 | 7.29 | 5.42 | 60.00 | 15,181.85 | -1,216.66 |
| 47 | S | 15,181.85 | 109.42 | 1,034.53 | 1,143.95 | 6.83 | 5.42 | 60.00 | 14,147.32 | -1,216.20 |
| 48 | S | 14,147.32 | 101.96 | 1,041.98 | 1,143.95 | 6.37 | 5.42 | 60.00 | 13,105.34 | -1,215.73 |
| 49 | S | 13,105.34 | 94.45 | 1,049.49 | 1,143.95 | 5.90 | 5.42 | 60.00 | 12,055.84 | -1,215.26 |
| 50 | S | 12,055.84 | 86.89 | 1,057.06 | 1,143.95 | 5.43 | 5.42 | 60.00 | 10,998.79 | -1,214.79 |
| 51 | S | 10,998.79 | 79.27 | 1,064.68 | 1,143.95 | 4.95 | 5.42 | 60.00 | 9,934.11 | -1,214.31 |
| 52 | S | 9,934.11 | 71.60 | 1,072.35 | 1,143.95 | 4.47 | 5.42 | 60.00 | 8,861.76 | -1,213.83 |
| 53 | S | 8,861.76 | 63.87 | 1,080.08 | 1,143.95 | 3.99 | 5.42 | 60.00 | 7,781.68 | -1,213.35 |
| 54 | S | 7,781.68 | 56.09 | 1,087.86 | 1,143.95 | 3.50 | 5.42 | 60.00 | 6,693.82 | -1,212.87 |
| 55 | S | 6,693.82 | 48.24 | 1,095.70 | 1,143.95 | 3.01 | 5.42 | 60.00 | 5,598.12 | -1,212.38 |
| 56 | S | 5,598.12 | 40.35 | 1,103.60 | 1,143.95 | 2.52 | 5.42 | 60.00 | 4,494.52 | -1,211.88 |
| 57 | S | 4,494.52 | 32.39 | 1,111.55 | 1,143.95 | 2.02 | 5.42 | 60.00 | 3,382.96 | -1,211.39 |
| 58 | S | 3,382.96 | 24.38 | 1,119.57 | 1,143.95 | 1.52 | 5.42 | 60.00 | 2,263.40 | -1,210.89 |
| 59 | S | 2,263.40 | 16.31 | 1,127.63 | 1,143.95 | 1.02 | 5.42 | 60.00 | 1,135.76 | -1,210.38 |
| 60 | S | 1,135.76 | 8.19 | 1,135.76 | 1,143.95 | 0.51 | 5.42 | 60.00 | 0.00 | -1,209.88 |

</details>

