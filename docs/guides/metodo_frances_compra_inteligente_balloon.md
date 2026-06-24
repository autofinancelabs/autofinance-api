# Método francés y compra inteligente con cuota balloon

## 1. Explicación breve

El **método francés** es un sistema de amortización en el que la cuota financiera se mantiene constante durante el tramo ordinario del préstamo. Cada pago se compone de intereses y amortización:

```text
Cuota = Interés + Amortización
```

Al inicio, la cuota contiene una proporción mayor de intereses porque el saldo pendiente es alto. Conforme el saldo disminuye, los intereses bajan y la amortización aumenta.

La **compra inteligente**, también llamada financiamiento con **cuota balloon**, usa la lógica del método francés, pero deja una parte del valor financiado para el final. Esa parte se llama **cuota final**, **cuotón**, **balloon** o **valor residual**. Como una parte importante del capital no se amortiza con las cuotas ordinarias, la cuota periódica baja; sin embargo, al final debe resolverse el saldo diferido mediante pago, refinanciamiento, devolución del activo o recompra, según contrato.

---

## 2. Método francés ordinario vs. compra inteligente

| Criterio | Método francés ordinario | Compra inteligente con cuota balloon |
|---|---|---|
| Cuota regular | Fija durante el tramo ordinario | Fija durante el tramo ordinario, pero menor |
| Amortización | Creciente hasta cancelar todo el préstamo | Creciente, pero solo sobre la parte no diferida |
| Saldo final | Llega a cero con las cuotas ordinarias | Requiere liquidar la cuota balloon |
| Pago final | Similar a las demás cuotas | Alto, porque incluye el cuotón |
| Principal ventaja | Predictibilidad de pagos | Menor cuota mensual |
| Principal cuidado | Revisar tasa, costos y seguros | Revisar la capacidad para cubrir el pago final |

---

## 3. Fórmula del método francés ordinario

```text
C = VA × [ i / (1 - (1 + i)^(-n)) ]
```

Donde:

| Símbolo | Significado |
|---|---|
| `C` | Cuota periódica. |
| `VA` | Valor actual o préstamo a amortizar. |
| `i` | Tasa efectiva del período. |
| `n` | Número de cuotas. |

---

## 4. Fórmula de compra inteligente con cuota balloon

La cuota balloon se descuenta al presente y se resta del valor actual financiado. Luego, la parte restante se amortiza con la fórmula francesa.

```text
C = [ VA - (Cuota Balloon / (1 + i)^n ) ] × ( i / (1 - (1 + i)^(-n) ) )
```

Donde:

| Símbolo | Significado |
|---|---|
| `C` | Cuota periódica de compra inteligente. |
| `VA` | Monto financiado o saldo base. |
| `Cuota Balloon` | Pago final pactado. |
| `i` | Tasa efectiva del período. |
| `n` | Número de períodos hasta el pago final. |

La parte:

```text
Cuota Balloon / (1 + i)^n
```

representa el valor presente del pago final. Por eso, cuanto mayor sea la cuota balloon, menor será la cuota ordinaria; pero mayor será el compromiso al final del cronograma.

---

## 5. Fórmula con seguro de desgravamen incorporado en la cuota

En algunos modelos, como el Excel complementario, la cuota regular se calcula incluyendo el seguro de desgravamen dentro de la tasa de cálculo. Para ello se usa una tasa ajustada:

```text
j = i + TSD
```

Entonces, la cuota regular se calcula así:

```text
C = VA × [ j / (1 - (1 + j)^(-n)) ]
```

Y si se aplica compra inteligente:

```text
C = [ VA - (Cuota Balloon / (1 + j)^n ) ] × [ j / (1 - (1 + j)^(-n)) ]
```

Esta variante permite que la cuota regular ya contenga el efecto del seguro de desgravamen. De todos modos, los demás costos periódicos, como GPS, portes, seguro de riesgo o gastos administrativos, se suman al flujo total.

---

## 6. Períodos de gracia

### 6.1. Gracia total

En la gracia total no se paga la cuota financiera ordinaria ni se amortiza capital. Los intereses se capitalizan:

```text
Interés = Saldo inicial × i
Cuota = 0
Amortización = 0
Saldo final = Saldo inicial + Interés
```

Aunque la cuota financiera sea cero, ciertos costos periódicos pueden seguir pagándose.

### 6.2. Gracia parcial

En la gracia parcial se pagan los intereses del período, pero no se amortiza capital:

```text
Cuota = Interés
Amortización = 0
Saldo final = Saldo inicial
```

Esto evita que el saldo principal aumente, aunque el deudor todavía asume costos periódicos.

---

## 7. Ejemplo complementario del Excel: Plan 36 con compra inteligente IB

### 7.1. Datos del préstamo

| Concepto | Valor |
|---|---:|
| Precio de venta del activo | 16,000.00 |
| Tipo de plan | Plan 36 |
| Cuota inicial | 20.00% |
| Cuota inicial en dinero | 3,200.00 |
| Cuota final o balloon | 40.00% |
| Cuota final en dinero | 6,400.00 |
| Número de años | 3 |
| Tasa de interés nominal | 15.00% |
| Tipo de tasa | TNA |
| Período de capitalización | Diaria |
| Frecuencia de pago | cada 30 días |
| Días por año | 360 |
| Cuotas por año | 12 |
| Número total de cuotas ordinarias | 36 |

### 7.2. Costos iniciales

| Concepto | Valor |
|---|---:|
| Costos notariales | 100.00 |
| Costos registrales | 75.00 |
| Tasación | 0.00 |
| Comisión de estudio | 0.00 |
| Comisión de activación | 0.00 |
| Costos iniciales totales | 175.00 |

El monto del préstamo se obtiene así:

```text
Cuota inicial = 16,000.00 × 20.00%
Cuota inicial = 3,200.00
```

```text
Saldo del activo a financiar = Precio de venta - Cuota inicial
Saldo del activo a financiar = 16,000.00 - 3,200.00
Saldo del activo a financiar = 12,800.00
```

```text
Préstamo = Saldo del activo a financiar + Costos iniciales
Préstamo = 12,800.00 + 175.00
Préstamo = 12,975.00
```

### 7.3. Tasas del financiamiento

| Concepto | Valor |
|---|---:|
| TEA efectiva del financiamiento | 16.179795% |
| TEM efectiva mensual | 1.2575815% |
| COK anual | 50.00% |
| COK del período | 3.4366083% |
| Seguro de desgravamen por período | 0.0490% |
| Seguro de riesgo periódico | 4.00 |

La tasa efectiva mensual se obtiene a partir de la tasa efectiva anual:

```text
TEM = (1 + TEA)^(30/360) - 1
TEM = (1 + 0.161797946)^(30/360) - 1
TEM = 0.012575815
TEM = 1.2575815%
```

Como el Excel parte de una tasa nominal anual con capitalización diaria, primero obtiene una TEA equivalente y luego la convierte a tasa mensual.

---

### 7.4. Estructura financiera de la compra inteligente

En este ejemplo, el préstamo total se separa en dos partes:

| Componente | Valor |
|---|---:|
| Valor presente asociado al cuotón | 3,959.01 |
| Saldo a financiar con cuotas ordinarias | 9,015.99 |
| Préstamo total | 12,975.00 |

La cuota final pactada es:

```text
Cuota final = Precio de venta × % cuota final
Cuota final = 16,000.00 × 40.00%
Cuota final = 6,400.00
```

La lógica es que una parte del préstamo se paga con cuotas ordinarias y otra parte queda diferida como cuota final. Por eso el cronograma del Excel muestra dos bloques paralelos:

| Bloque | Función |
|---|---|
| Cronograma de la cuota final o cuotón | Controla el crecimiento financiero del valor que se pagará al final. |
| Cronograma de la cuota regular | Calcula las cuotas ordinarias bajo el método francés. |
| Costos de operación | Registra seguro de riesgo, GPS, portes y gastos administrativos. |

---

### 7.5. Gracia total y gracia parcial del ejemplo

El cronograma incluye:

| Tipo de período | Períodos | Tratamiento |
|---|---:|---|
| Gracia total `T` | 3 | No se paga cuota ordinaria; los intereses se capitalizan. |
| Gracia parcial `P` | 3 | Se pagan intereses; no se amortiza capital. |
| Pago ordinario `S` | 30 | Se paga cuota francesa regular. |
| Liquidación final del cuotón | 1 | Se liquida la cuota balloon. |

Durante los tres primeros períodos, el saldo regular pasa de 9,015.99 a 9,360.44 por la capitalización de intereses:

```text
Saldo después de gracia total = Saldo inicial × (1 + TEM)^3
Saldo después de gracia total = 9,015.99 × (1 + 0.012575815)^3
Saldo después de gracia total = 9,360.44
```

Durante la gracia parcial, el saldo regular se mantiene en 9,360.44 porque se pagan los intereses, pero no se amortiza capital.

---

### 7.6. Cuota regular del tramo ordinario

En el tramo ordinario, la cuota regular del Excel es:

```text
Cuota regular = 379.16
```

Esta cuota se calcula con una tasa ajustada que incorpora la tasa de interés y el seguro de desgravamen:

```text
j = TEM + TSD
j = 0.012575815 + 0.000490000
j = 0.013065815
```

Luego se aplica la fórmula francesa al saldo regular después de los períodos de gracia:

```text
C = VA × [ j / (1 - (1 + j)^(-n)) ]
```

En el Excel:

```text
VA = 9,360.44
n = 30
C = 379.16
```

---

### 7.7. Costos periódicos del ejemplo

| Concepto | Valor por período |
|---|---:|
| GPS | 20.00 |
| Portes | 3.50 |
| Gastos administrativos | 3.50 |
| Seguro contra todo riesgo | 4.00 |
| Seguro de desgravamen | Variable, según saldo |

El flujo pagado por el deudor no es solo la cuota financiera. En los períodos ordinarios se calcula así:

```text
Flujo = Cuota regular + Seguro de riesgo + GPS + Portes + Gastos administrativos
```

En los períodos de gracia, aunque no exista amortización, el Excel sigue registrando pagos por seguros y costos operativos.

---

### 7.8. Cronograma resumido

La tabla siguiente muestra los primeros períodos y la parte final del cronograma. Los valores se presentan en positivo para facilitar la lectura, aunque en el Excel los pagos aparecen como salidas de caja.

| Nº | P.G. | Saldo inicial cuotón | Interés cuotón | Seg. desgrav. cuotón | Saldo final cuotón | Saldo inicial cuota | Interés cuota | Cuota regular | Amortización | Seg. desgrav. cuota | Seg. riesgo | GPS | Portes | G. adm. | Saldo final cuota | Flujo |
|---:|:---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| 1 | T | 3,959.01 | 49.79 | 1.94 | 4,010.74 | 9,015.99 | 113.38 | 0.00 | 0.00 | 4.42 | 4.00 | 20.00 | 3.50 | 3.50 | 9,129.37 | 35.42 |
| 2 | T | 4,010.74 | 50.44 | 1.97 | 4,063.14 | 9,129.37 | 114.81 | 0.00 | 0.00 | 4.47 | 4.00 | 20.00 | 3.50 | 3.50 | 9,244.18 | 35.47 |
| 3 | T | 4,063.14 | 51.10 | 1.99 | 4,116.23 | 9,244.18 | 116.25 | 0.00 | 0.00 | 4.53 | 4.00 | 20.00 | 3.50 | 3.50 | 9,360.44 | 35.53 |
| 4 | P | 4,116.23 | 51.76 | 2.02 | 4,170.01 | 9,360.44 | 117.72 | 117.72 | 0.00 | 4.59 | 4.00 | 20.00 | 3.50 | 3.50 | 9,360.44 | 153.30 |
| 5 | P | 4,170.01 | 52.44 | 2.04 | 4,224.50 | 9,360.44 | 117.72 | 117.72 | 0.00 | 4.59 | 4.00 | 20.00 | 3.50 | 3.50 | 9,360.44 | 153.30 |
| 6 | P | 4,224.50 | 53.13 | 2.07 | 4,279.69 | 9,360.44 | 117.72 | 117.72 | 0.00 | 4.59 | 4.00 | 20.00 | 3.50 | 3.50 | 9,360.44 | 153.30 |
| 7 | S | 4,279.69 | 53.82 | 2.10 | 4,335.61 | 9,360.44 | 117.72 | 379.16 | 256.86 | 4.59 | 4.00 | 20.00 | 3.50 | 3.50 | 9,103.58 | 410.16 |
| 8 | S | 4,335.61 | 54.52 | 2.12 | 4,392.26 | 9,103.58 | 114.48 | 379.16 | 260.21 | 4.46 | 4.00 | 20.00 | 3.50 | 3.50 | 8,843.37 | 410.16 |
| 33 | S | 5,997.80 | 75.43 | 2.94 | 6,076.16 | 1,468.36 | 18.47 | 379.16 | 359.97 | 0.72 | 4.00 | 20.00 | 3.50 | 3.50 | 1,108.39 | 410.16 |
| 34 | S | 6,076.16 | 76.41 | 2.98 | 6,155.55 | 1,108.39 | 13.94 | 379.16 | 364.68 | 0.54 | 4.00 | 20.00 | 3.50 | 3.50 | 743.71 | 410.16 |
| 35 | S | 6,155.55 | 77.41 | 3.02 | 6,235.98 | 743.71 | 9.35 | 379.16 | 369.44 | 0.36 | 4.00 | 20.00 | 3.50 | 3.50 | 374.27 | 410.16 |
| 36 | S | 6,235.98 | 78.42 | 3.06 | 6,317.46 | 374.27 | 4.71 | 379.16 | 374.27 | 0.18 | 4.00 | 20.00 | 3.50 | 3.50 | 0.00 | 410.16 |
| 37 | S | 6,317.46 | 79.45 | 3.10 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 4.00 | 20.00 | 3.50 | 3.50 | 0.00 | 6,431.00 |

---

### 7.9. Resultados acumulados

| Indicador | Resultado |
|---|---:|
| Intereses totales | 2,264.74 |
| Amortización total del capital | 15,760.44 |
| Seguro de desgravamen total | 102.72 |
| Seguro contra todo riesgo total | 148.00 |
| GPS total | 740.00 |
| Portes totales | 129.50 |
| Gastos administrativos totales | 129.50 |
| TIR de la operación por período | 1.5861749% |
| TCEA de la operación | 20.7856% |
| VAN de la operación | 4,436.18 |

---

### 7.10. VAN, TIR y TCEA

El VAN del financiamiento se calcula comparando el préstamo recibido con los pagos futuros descontados a la COK del período:

```text
VAN = Préstamo + Σ [ Flujo_t / (1 + COK período)^t ]
```

En este ejemplo:

```text
VAN = 4,436.18
```

Como el VAN es positivo, el financiamiento resulta conveniente frente a una COK anual de 50%, bajo los flujos modelados en el Excel.

La TIR del período es la tasa que hace que el VAN sea igual a cero:

```text
0 = Préstamo + Σ [ Flujo_t / (1 + TIR)^t ]
```

El Excel obtiene:

```text
TIR período = 1.5861749%
```

La TCEA se obtiene anualizando la TIR periódica:

```text
TCEA = (1 + TIR período)^12 - 1
TCEA = 20.7856%
```

La TCEA es superior a la tasa efectiva del crédito porque incluye costos adicionales y seguros. Por eso, para comparar alternativas de financiamiento, debe usarse la TCEA y no solo la tasa compensatoria.

---

## 8. Lectura financiera del ejemplo

El ejemplo muestra una compra inteligente donde el préstamo inicial asciende a 12,975.00, pero no todo se amortiza mediante cuotas ordinarias. Una parte se difiere como cuota final de 6,400.00. Durante los primeros períodos existe gracia total y parcial, lo cual reduce temporalmente el pago de capital, pero altera el saldo sobre el que luego se calculan las cuotas. En el tramo ordinario, la cuota regular es de 379.16, a la que se agregan costos como GPS, seguro de riesgo, portes y gastos administrativos. Finalmente, se liquida la cuota balloon en una fila separada.

La ventaja principal del esquema es que reduce el pago periódico respecto a un préstamo ordinario sin balloon. Su desventaja es que obliga a planificar el pago final. Si el usuario no considera la cuota balloon, puede interpretar erróneamente que el crédito es más barato de lo que realmente es.

---

## 9. Fórmulas útiles para Excel

### Préstamo

```excel
=PrecioVenta-(PrecioVenta*PorcentajeCuotaInicial)+CostosIniciales
```

### Cuota final

```excel
=PrecioVenta*PorcentajeCuotaFinal
```

### Tasa efectiva del período

```excel
=(1+TEA)^(DiasPeriodo/DiasAño)-1
```

### Cuota francesa ordinaria

```excel
=VA*(i/(1-(1+i)^-n))
```

### Cuota de compra inteligente con balloon

```excel
=(VA-(CuotaBalloon/(1+i)^n))*(i/(1-(1+i)^-n))
```

### Cuota con seguro de desgravamen incorporado

```excel
=VA*((i+TSD)/(1-(1+i+TSD)^-n))
```

### Interés

```excel
=SaldoInicial*i
```

### Amortización

```excel
=Cuota-Interes
```

### Saldo final

```excel
=SaldoInicial-Amortizacion
```

### Flujo total

```excel
=Cuota+SeguroRiesgo+GPS+Portes+GastosAdministrativos
```

---

## 10. Cronograma completo del ejemplo

<details>
<summary>Ver cronograma completo</summary>

| Nº | P.G. | Saldo inicial cuotón | Interés cuotón | Seg. desgrav. cuotón | Saldo final cuotón | Saldo inicial cuota | Interés cuota | Cuota regular | Amortización | Seg. desgrav. cuota | Seg. riesgo | GPS | Portes | G. adm. | Saldo final cuota | Flujo |
|---:|:---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| 0 |  | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 12,975.00 |
| 1 | T | 3,959.01 | 49.79 | 1.94 | 4,010.74 | 9,015.99 | 113.38 | 0.00 | 0.00 | 4.42 | 4.00 | 20.00 | 3.50 | 3.50 | 9,129.37 | 35.42 |
| 2 | T | 4,010.74 | 50.44 | 1.97 | 4,063.14 | 9,129.37 | 114.81 | 0.00 | 0.00 | 4.47 | 4.00 | 20.00 | 3.50 | 3.50 | 9,244.18 | 35.47 |
| 3 | T | 4,063.14 | 51.10 | 1.99 | 4,116.23 | 9,244.18 | 116.25 | 0.00 | 0.00 | 4.53 | 4.00 | 20.00 | 3.50 | 3.50 | 9,360.44 | 35.53 |
| 4 | P | 4,116.23 | 51.76 | 2.02 | 4,170.01 | 9,360.44 | 117.72 | 117.72 | 0.00 | 4.59 | 4.00 | 20.00 | 3.50 | 3.50 | 9,360.44 | 153.30 |
| 5 | P | 4,170.01 | 52.44 | 2.04 | 4,224.50 | 9,360.44 | 117.72 | 117.72 | 0.00 | 4.59 | 4.00 | 20.00 | 3.50 | 3.50 | 9,360.44 | 153.30 |
| 6 | P | 4,224.50 | 53.13 | 2.07 | 4,279.69 | 9,360.44 | 117.72 | 117.72 | 0.00 | 4.59 | 4.00 | 20.00 | 3.50 | 3.50 | 9,360.44 | 153.30 |
| 7 | S | 4,279.69 | 53.82 | 2.10 | 4,335.61 | 9,360.44 | 117.72 | 379.16 | 256.86 | 4.59 | 4.00 | 20.00 | 3.50 | 3.50 | 9,103.58 | 410.16 |
| 8 | S | 4,335.61 | 54.52 | 2.12 | 4,392.26 | 9,103.58 | 114.48 | 379.16 | 260.21 | 4.46 | 4.00 | 20.00 | 3.50 | 3.50 | 8,843.37 | 410.16 |
| 9 | S | 4,392.26 | 55.24 | 2.15 | 4,449.65 | 8,843.37 | 111.21 | 379.16 | 263.61 | 4.33 | 4.00 | 20.00 | 3.50 | 3.50 | 8,579.75 | 410.16 |
| 10 | S | 4,449.65 | 55.96 | 2.18 | 4,507.78 | 8,579.75 | 107.90 | 379.16 | 267.06 | 4.20 | 4.00 | 20.00 | 3.50 | 3.50 | 8,312.70 | 410.16 |
| 11 | S | 4,507.78 | 56.69 | 2.21 | 4,566.68 | 8,312.70 | 104.54 | 379.16 | 270.55 | 4.07 | 4.00 | 20.00 | 3.50 | 3.50 | 8,042.15 | 410.16 |
| 12 | S | 4,566.68 | 57.43 | 2.24 | 4,626.35 | 8,042.15 | 101.14 | 379.16 | 274.08 | 3.94 | 4.00 | 20.00 | 3.50 | 3.50 | 7,768.07 | 410.16 |
| 13 | S | 4,626.35 | 58.18 | 2.27 | 4,686.80 | 7,768.07 | 97.69 | 379.16 | 277.66 | 3.81 | 4.00 | 20.00 | 3.50 | 3.50 | 7,490.41 | 410.16 |
| 14 | S | 4,686.80 | 58.94 | 2.30 | 4,748.03 | 7,490.41 | 94.20 | 379.16 | 281.29 | 3.67 | 4.00 | 20.00 | 3.50 | 3.50 | 7,209.12 | 410.16 |
| 15 | S | 4,748.03 | 59.71 | 2.33 | 4,810.07 | 7,209.12 | 90.66 | 379.16 | 284.97 | 3.53 | 4.00 | 20.00 | 3.50 | 3.50 | 6,924.15 | 410.16 |
| 16 | S | 4,810.07 | 60.49 | 2.36 | 4,872.92 | 6,924.15 | 87.08 | 379.16 | 288.69 | 3.39 | 4.00 | 20.00 | 3.50 | 3.50 | 6,635.46 | 410.16 |
| 17 | S | 4,872.92 | 61.28 | 2.39 | 4,936.59 | 6,635.46 | 83.45 | 379.16 | 292.46 | 3.25 | 4.00 | 20.00 | 3.50 | 3.50 | 6,343.00 | 410.16 |
| 18 | S | 4,936.59 | 62.08 | 2.42 | 5,001.09 | 6,343.00 | 79.77 | 379.16 | 296.28 | 3.11 | 4.00 | 20.00 | 3.50 | 3.50 | 6,046.72 | 410.16 |
| 19 | S | 5,001.09 | 62.89 | 2.45 | 5,066.43 | 6,046.72 | 76.04 | 379.16 | 300.15 | 2.96 | 4.00 | 20.00 | 3.50 | 3.50 | 5,746.57 | 410.16 |
| 20 | S | 5,066.43 | 63.71 | 2.48 | 5,132.63 | 5,746.57 | 72.27 | 379.16 | 304.07 | 2.82 | 4.00 | 20.00 | 3.50 | 3.50 | 5,442.49 | 410.16 |
| 21 | S | 5,132.63 | 64.55 | 2.51 | 5,199.69 | 5,442.49 | 68.44 | 379.16 | 308.05 | 2.67 | 4.00 | 20.00 | 3.50 | 3.50 | 5,134.45 | 410.16 |
| 22 | S | 5,199.69 | 65.39 | 2.55 | 5,267.63 | 5,134.45 | 64.57 | 379.16 | 312.07 | 2.52 | 4.00 | 20.00 | 3.50 | 3.50 | 4,822.37 | 410.16 |
| 23 | S | 5,267.63 | 66.24 | 2.58 | 5,336.45 | 4,822.37 | 60.65 | 379.16 | 316.15 | 2.36 | 4.00 | 20.00 | 3.50 | 3.50 | 4,506.22 | 410.16 |
| 24 | S | 5,336.45 | 67.11 | 2.61 | 5,406.18 | 4,506.22 | 56.67 | 379.16 | 320.28 | 2.21 | 4.00 | 20.00 | 3.50 | 3.50 | 4,185.94 | 410.16 |
| 25 | S | 5,406.18 | 67.99 | 2.65 | 5,476.81 | 4,185.94 | 52.64 | 379.16 | 324.47 | 2.05 | 4.00 | 20.00 | 3.50 | 3.50 | 3,861.48 | 410.16 |
| 26 | S | 5,476.81 | 68.88 | 2.68 | 5,548.37 | 3,861.48 | 48.56 | 379.16 | 328.71 | 1.89 | 4.00 | 20.00 | 3.50 | 3.50 | 3,532.77 | 410.16 |
| 27 | S | 5,548.37 | 69.78 | 2.72 | 5,620.87 | 3,532.77 | 44.43 | 379.16 | 333.00 | 1.73 | 4.00 | 20.00 | 3.50 | 3.50 | 3,199.77 | 410.16 |
| 28 | S | 5,620.87 | 70.69 | 2.75 | 5,694.31 | 3,199.77 | 40.24 | 379.16 | 337.35 | 1.57 | 4.00 | 20.00 | 3.50 | 3.50 | 2,862.42 | 410.16 |
| 29 | S | 5,694.31 | 71.61 | 2.79 | 5,768.71 | 2,862.42 | 36.00 | 379.16 | 341.76 | 1.40 | 4.00 | 20.00 | 3.50 | 3.50 | 2,520.66 | 410.16 |
| 30 | S | 5,768.71 | 72.55 | 2.83 | 5,844.08 | 2,520.66 | 31.70 | 379.16 | 346.22 | 1.24 | 4.00 | 20.00 | 3.50 | 3.50 | 2,174.44 | 410.16 |
| 31 | S | 5,844.08 | 73.49 | 2.86 | 5,920.44 | 2,174.44 | 27.35 | 379.16 | 350.75 | 1.07 | 4.00 | 20.00 | 3.50 | 3.50 | 1,823.69 | 410.16 |
| 32 | S | 5,920.44 | 74.45 | 2.90 | 5,997.80 | 1,823.69 | 22.93 | 379.16 | 355.33 | 0.89 | 4.00 | 20.00 | 3.50 | 3.50 | 1,468.36 | 410.16 |
| 33 | S | 5,997.80 | 75.43 | 2.94 | 6,076.16 | 1,468.36 | 18.47 | 379.16 | 359.97 | 0.72 | 4.00 | 20.00 | 3.50 | 3.50 | 1,108.39 | 410.16 |
| 34 | S | 6,076.16 | 76.41 | 2.98 | 6,155.55 | 1,108.39 | 13.94 | 379.16 | 364.68 | 0.54 | 4.00 | 20.00 | 3.50 | 3.50 | 743.71 | 410.16 |
| 35 | S | 6,155.55 | 77.41 | 3.02 | 6,235.98 | 743.71 | 9.35 | 379.16 | 369.44 | 0.36 | 4.00 | 20.00 | 3.50 | 3.50 | 374.27 | 410.16 |
| 36 | S | 6,235.98 | 78.42 | 3.06 | 6,317.46 | 374.27 | 4.71 | 379.16 | 374.27 | 0.18 | 4.00 | 20.00 | 3.50 | 3.50 | 0.00 | 410.16 |
| 37 | S | 6,317.46 | 79.45 | 3.10 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 0.00 | 4.00 | 20.00 | 3.50 | 3.50 | 0.00 | 6,431.00 |

</details>
