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

R = 887.49
```

### Primeros períodos

| Nº | Saldo inicial | Interés | Cuota préstamo | Amortización | Seguro desgravamen | Comisión | Cuota total | Saldo final |
|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| 1 | 10,000.00 | 94.89 | 887.49 | 792.60 | 5.00 | 10.00 | 902.49 | 9,207.40 |
| 2 | 9,207.40 | 87.37 | 887.49 | 800.12 | 4.60 | 10.00 | 902.09 | 8,407.28 |
| 3 | 8,407.28 | 79.77 | 887.49 | 807.72 | 4.20 | 10.00 | 901.69 | 7,599.56 |

### Lectura del resultado

La cuota financiera del préstamo es constante: US$ 887.49. Sin embargo, la cuota total cambia ligeramente porque el seguro de desgravamen disminuye junto con el saldo del préstamo.

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

