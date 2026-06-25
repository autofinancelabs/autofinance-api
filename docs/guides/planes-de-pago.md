# Planes de pago: guía operativa con fórmulas, criterios y ejemplos

> Documento elaborado a partir del material **Finanzas e Ingeniería Económica - Unidad 3: Planes de pago**, cuyo contenido desarrolla los métodos americano o inglés, alemán, francés y peruano o de cuota doble, además de ejercicios de aplicación.

## 1. Propósito del documento

Este documento explica cómo construir, interpretar y comparar planes de pago de préstamos. Está redactado para que pueda ser usado tanto por una persona como por una IA o sistema de cálculo. Incluye conceptos, variables, fórmulas, reglas de decisión, plantillas de cronograma y ejemplos numéricos.

Un **plan de pago** o **cronograma de pagos** muestra cómo se cancela una deuda a lo largo del tiempo. En cada periodo se separa la cuota en dos componentes:

1. **Interés**, que es el costo financiero calculado sobre el saldo pendiente.
2. **Amortización**, que es la parte de la cuota que reduce el capital adeudado.

La estructura básica de cualquier cuota es:

```text
Cuota = Interés + Amortización
```

Y el saldo se actualiza así:

```text
Saldo final = Saldo inicial - Amortización
```

Cuando existe **gracia total**, no se paga nada en el periodo y el interés se capitaliza:

```text
Saldo final = Saldo inicial + Interés
```

Cuando existe **gracia parcial o normal**, se pagan solo los intereses y no se amortiza capital:

```text
Cuota = Interés
Amortización = 0
Saldo final = Saldo inicial
```

---

## 2. Métodos de pago tratados en el material

El PDF enumera varios métodos de pago: americano o inglés, alemán, francés, peruano o de cuota doble, suma de dígitos, progresión aritmética, progresión geométrica y fondo de amortización. Sin embargo, desarrolla con fórmulas y ejemplos los cuatro primeros. Por fidelidad al material, este documento trabaja principalmente esos cuatro métodos.

| Método                | Rasgo principal                                   |                           Comportamiento de la cuota | Comportamiento de la amortización | Uso típico                                                        |
|-----------------------|---------------------------------------------------|-----------------------------------------------------:|----------------------------------:|-------------------------------------------------------------------|
| Americano o inglés    | Se paga capital al vencimiento                    |                   Baja al inicio y muy alta al final |        Cero hasta la última cuota | Deudas con pago final grande o financiamientos puente             |
| Alemán                | Amortización constante                            |                                          Decreciente |                         Constante | Cuando se busca reducir rápido el saldo                           |
| Francés               | Cuotas constantes                                 |            Constante, salvo cambios de tasa o gracia |                         Creciente | Préstamos comerciales e hipotecarios por predictibilidad          |
| Peruano o cuota doble | Cuotas dobles después del 15 de julio y diciembre | Simple en meses normales, doble en julio y diciembre |                          Variable | Casos vinculados a ingresos extraordinarios de mitad y fin de año |

---

## 3. Variables y notación

| Símbolo / campo | Significado                             | Observación operativa                                                |
|-----------------|-----------------------------------------|----------------------------------------------------------------------|
| `PV`            | Precio de venta del bien                | Base para calcular la cuota inicial                                  |
| `CI`            | Cuota inicial                           | Puede expresarse como porcentaje del precio de venta                 |
| `C`             | Monto del préstamo o capital financiado | `C = PV - CI` o `C = PV * (1 - %CI)`                                 |
| `TEA`           | Tasa efectiva anual                     | Tasa anual informada por la institución que otorga el financiamiento |
| `TEP`           | Tasa efectiva del periodo de pago       | Debe coincidir con la frecuencia de pago                             |
| `TES`           | Tasa efectiva semestral                 | Caso particular de TEP cuando los pagos son semestrales              |
| `TEC`           | Tasa efectiva cuatrimestral             | Caso particular de TEP cuando los pagos son cada 4 meses             |
| `TEM`           | Tasa efectiva mensual                   | Caso particular de TEP cuando los pagos son mensuales                |
| `TET`           | Tasa efectiva trimestral                | Caso particular de TEP cuando los pagos son trimestrales             |
| `TEB`           | Tasa efectiva bimestral                 | Caso particular de TEP cuando los pagos son bimestrales              |
| `n`             | Número total de cuotas                  | Depende del plazo y la frecuencia                                    |
| `nc`            | Número de la cuota que se calcula       | Se usa especialmente cuando cambia la tasa                           |
| `SI`            | Saldo inicial del periodo               | Saldo sobre el cual se calculan intereses                            |
| `I`             | Interés del periodo                     | `I = SI * TEP`                                                       |
| `A`             | Amortización del periodo                | Parte de la cuota que reduce capital                                 |
| `R`             | Cuota periódica o anualidad             | En el método francés representa la cuota calculada por fórmula       |
| `SF`            | Saldo final                             | Saldo pendiente después del pago o capitalización                    |
| `T`             | Gracia total                            | No se paga cuota y el interés se capitaliza                          |
| `P`             | Gracia parcial o normal                 | Se pagan intereses, pero no capital                                  |
| `S`             | Sin gracia                              | Se paga según el método correspondiente                              |

---

## 4. Conversión de tasas

Antes de construir un cronograma, la tasa anual debe convertirse a la tasa efectiva del periodo de pago.

Si la TEA es efectiva anual y existen `m` pagos por año:

```text
TEP = (1 + TEA)^(1/m) - 1
```

| Frecuencia de pago | Pagos por año `m` | Fórmula                      |
|--------------------|------------------:|------------------------------|
| Mensual            |                12 | `TEM = (1 + TEA)^(1/12) - 1` |
| Bimestral          |                 6 | `TEB = (1 + TEA)^(1/6) - 1`  |
| Trimestral         |                 4 | `TET = (1 + TEA)^(1/4) - 1`  |
| Cuatrimestral      |                 3 | `TEC = (1 + TEA)^(1/3) - 1`  |
| Semestral          |                 2 | `TES = (1 + TEA)^(1/2) - 1`  |
| Anual              |                 1 | `TEA = (1 + TEA)^(1/1) - 1`  |

Ejemplo con TEA de 9% y pagos semestrales:

```text
TES = (1 + 0.09)^(1/2) - 1
TES = 0.0440306509
TES = 4.4030651%
```

Ejemplo con TEA de 8% y pagos semestrales:

```text
TES = (1 + 0.08)^(1/2) - 1
TES = 0.0392304845
TES = 3.9230485%
```

---

## 5. Estructura estándar de un cronograma de pagos

Una tabla de plan de pagos debe incluir como mínimo los siguientes campos:

| Nº |        TEA |              TEP | Gracia |        Saldo inicial |    Interés |        Cuota |      Amortización | Saldo final |
|---:|-----------:|-----------------:|:------:|---------------------:|-----------:|-------------:|------------------:|------------:|
|  1 | tasa anual | tasa del periodo | S/T/P  | saldo antes del pago | `SI * TEP` | según método | `Cuota - Interés` | según regla |

Reglas generales:

```text
Interés = Saldo inicial * TEP
Cuota = Interés + Amortización
Saldo final = Saldo inicial - Amortización
```

Si hay gracia total:

```text
Cuota = 0
Amortización = 0
Saldo final = Saldo inicial + Interés
```

Si hay gracia parcial:

```text
Cuota = Interés
Amortización = 0
Saldo final = Saldo inicial
```

---

## 6. Método americano o inglés

### 6.1. Definición

El método americano o inglés se caracteriza porque el capital se amortiza totalmente al vencimiento. Durante los periodos previos se pagan únicamente los intereses. Por eso, la deuda permanece constante hasta el último periodo, salvo que exista gracia total y los intereses se capitalicen.

### 6.2. Fórmulas

Para las cuotas desde la 1 hasta la `n - 1`, cuando no hay gracia:

```text
Interés = Saldo * TEP
Amortización = 0
Cuota = Interés
Saldo final = Saldo inicial
```

En la última cuota:

```text
Interés = Saldo * TEP
Amortización = Saldo
Cuota = Interés + Saldo
Saldo final = 0
```

### 6.3. Interpretación

Este método genera cuotas iniciales bajas, pero una cuota final muy alta. Es útil cuando el deudor espera recibir liquidez importante al final del plazo. Su principal riesgo es la concentración del pago de capital en una sola fecha.

### 6.4. Ejemplo base

Condiciones:

| Dato               |            Valor |
|--------------------|-----------------:|
| Precio de venta    | US$ 1,800,000.00 |
| Cuota inicial      |              20% |
| Préstamo           | US$ 1,440,000.00 |
| TEA                |     9% constante |
| Frecuencia         |        Semestral |
| Plazo              |           4 años |
| Número de periodos |                8 |
| TES                |       4.4030651% |

Cronograma:

| Nº | Saldo inicial |   Interés |        Cuota | Amortización |  Saldo final |
|---:|--------------:|----------:|-------------:|-------------:|-------------:|
|  1 |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  2 |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  3 |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  4 |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  5 |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  6 |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  7 |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  8 |  1,440,000.00 | 63,404.14 | 1,503,404.14 | 1,440,000.00 |         0.00 |

Lectura del resultado: durante siete semestres se pagan solo intereses; en el octavo semestre se paga el interés del periodo más todo el capital.

---

## 7. Método alemán

### 7.1. Definición

El método alemán se caracteriza porque la amortización de capital es constante. Como el saldo pendiente disminuye en cada periodo, los intereses también disminuyen y, por tanto, las cuotas son decrecientes.

También se conoce como:

- Método de amortización constante.
- Método de cuota decreciente.
- Método al rebatir.

### 7.2. Fórmulas

Si no existen periodos de gracia:

```text
Amortización constante = Préstamo / n
Interés del periodo = Saldo inicial * TEP
Cuota = Interés + Amortización constante
Saldo final = Saldo inicial - Amortización constante
```

Si existen periodos de gracia, la amortización constante debe recalcularse sobre el saldo pendiente y el número de periodos restantes con amortización efectiva:

```text
Amortización constante = Saldo pendiente / número de periodos restantes sin gracia
```

### 7.3. Interpretación

Este método reduce el saldo más rápido que el método francés al inicio, pero exige pagos iniciales más altos. Es conveniente cuando el deudor puede asumir cuotas mayores al comienzo y desea pagar menos intereses totales.

### 7.4. Ejemplo base

Condiciones:

| Dato                   |            Valor |
|------------------------|-----------------:|
| Préstamo               | US$ 1,440,000.00 |
| TEA                    |     9% constante |
| Frecuencia             |        Semestral |
| Plazo                  |           4 años |
| Periodos               |                8 |
| TES                    |       4.4030651% |
| Amortización constante |   US$ 180,000.00 |

Cronograma:

| Nº | Saldo inicial |   Interés |      Cuota | Amortización |  Saldo final |
|---:|--------------:|----------:|-----------:|-------------:|-------------:|
|  1 |  1,440,000.00 | 63,404.14 | 243,404.14 |   180,000.00 | 1,260,000.00 |
|  2 |  1,260,000.00 | 55,478.62 | 235,478.62 |   180,000.00 | 1,080,000.00 |
|  3 |  1,080,000.00 | 47,553.10 | 227,553.10 |   180,000.00 |   900,000.00 |
|  4 |    900,000.00 | 39,627.59 | 219,627.59 |   180,000.00 |   720,000.00 |
|  5 |    720,000.00 | 31,702.07 | 211,702.07 |   180,000.00 |   540,000.00 |
|  6 |    540,000.00 | 23,776.55 | 203,776.55 |   180,000.00 |   360,000.00 |
|  7 |    360,000.00 | 15,851.03 | 195,851.03 |   180,000.00 |   180,000.00 |
|  8 |    180,000.00 |  7,925.52 | 187,925.52 |   180,000.00 |         0.00 |

Lectura del resultado: todas las amortizaciones son iguales, pero la cuota baja porque cada periodo se calculan intereses sobre un saldo menor.

---

## 8. Método francés

### 8.1. Definición

El método francés se caracteriza por tener cuotas constantes. Cada cuota conserva el mismo valor mientras no cambie la tasa, el saldo recalculado o las condiciones del crédito. Al inicio la cuota contiene más interés y menos amortización; conforme avanza el cronograma, la proporción se invierte.

### 8.2. Fórmula de la cuota constante

```text
R = C * [TEP * (1 + TEP)^n] / [(1 + TEP)^n - 1]
```

Donde:

| Variable | Significado                       |
|----------|-----------------------------------|
| `R`      | Cuota o anualidad a pagar         |
| `C`      | Monto del préstamo                |
| `TEP`    | Tasa efectiva del periodo de pago |
| `n`      | Número total de cuotas            |

Luego, para cada periodo:

```text
Interés = TEP * Saldo inicial
Amortización = R - Interés
Saldo final = Saldo inicial - Amortización
```

### 8.3. Fórmula cuando cambia la tasa

Cuando cambia la tasa de interés, ya no conviene usar la fórmula inicial para todo el cronograma. Debe recalcularse la cuota sobre el saldo pendiente y los periodos restantes:

```text
R = SI * [TEP * (1 + TEP)^(n - nc + 1)] / [(1 + TEP)^(n - nc + 1) - 1]
```

Donde:

| Variable     | Significado                                        |
|--------------|----------------------------------------------------|
| `SI`         | Saldo al iniciar el periodo en el que se recalcula |
| `TEP`        | Nueva tasa efectiva del periodo                    |
| `n`          | Número total de cuotas                             |
| `nc`         | Número de la cuota que se está calculando          |
| `n - nc + 1` | Número de cuotas restantes, incluida la actual     |

### 8.4. Interpretación

Es el método más fácil de presupuestar porque mantiene cuotas iguales dentro de cada bloque de tasa. Cuando hay cambios de tasa, plazos de gracia o capitalización de intereses, la cuota debe recalcularse para que el saldo llegue a cero al final.

### 8.5. Ejemplo base

Condiciones:

| Dato                |            Valor |
|---------------------|-----------------:|
| Préstamo            | US$ 1,440,000.00 |
| TEA                 |     9% constante |
| Frecuencia          |        Semestral |
| Plazo               |           4 años |
| Periodos            |                8 |
| TES                 |       4.4030651% |
| Cuota constante `R` |   US$ 217,454.11 |

Cronograma:

| Nº | Saldo inicial |   Interés |      Cuota | Amortización |  Saldo final |
|---:|--------------:|----------:|-----------:|-------------:|-------------:|
|  1 |  1,440,000.00 | 63,404.14 | 217,454.11 |   154,049.98 | 1,285,950.02 |
|  2 |  1,285,950.02 | 56,621.22 | 217,454.11 |   160,832.90 | 1,125,117.13 |
|  3 |  1,125,117.13 | 49,539.64 | 217,454.11 |   167,914.47 |   957,202.65 |
|  4 |    957,202.65 | 42,146.26 | 217,454.11 |   175,307.86 |   781,894.79 |
|  5 |    781,894.79 | 34,427.34 | 217,454.11 |   183,026.78 |   598,868.02 |
|  6 |    598,868.02 | 26,368.55 | 217,454.11 |   191,085.57 |   407,782.45 |
|  7 |    407,782.45 | 17,954.93 | 217,454.11 |   199,499.19 |   208,283.27 |
|  8 |    208,283.27 |  9,170.85 | 217,454.11 |   208,283.27 |         0.00 |

Lectura del resultado: la cuota permanece constante, los intereses disminuyen y la amortización aumenta en cada periodo.

---

## 9. Manejo de tasas variables

Cuando la tasa cambia durante el préstamo, cada método se ajusta de forma distinta.

| Método    | Qué cambia cuando varía la tasa                                                     | Qué permanece igual                                                                              |
|-----------|-------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------|
| Americano | Cambia el interés de cada periodo y, por tanto, la cuota de intereses               | El capital se sigue pagando al final                                                             |
| Alemán    | Cambia el interés de cada periodo y, por tanto, la cuota                            | La amortización constante se mantiene si no cambia el saldo base ni los periodos de amortización |
| Francés   | Se recalcula la cuota con el saldo pendiente, la nueva TEP y los periodos restantes | La cuota se mantiene constante solo dentro de cada bloque de tasa                                |

### 9.1. Ejemplo con cambio de tasa en método americano

Condiciones principales:

| Dato               |            Valor |
|--------------------|-----------------:|
| Préstamo           | US$ 1,440,000.00 |
| TEA periodos 1 a 4 |               9% |
| TES periodos 1 a 4 |       4.4030651% |
| TEA periodos 5 a 8 |               8% |
| TES periodos 5 a 8 |       3.9230485% |
| Método             |        Americano |

Cronograma resumido:

| Nº | TEA | Saldo inicial |   Interés |        Cuota | Amortización |  Saldo final |
|---:|----:|--------------:|----------:|-------------:|-------------:|-------------:|
|  1 |  9% |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  2 |  9% |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  3 |  9% |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  4 |  9% |  1,440,000.00 | 63,404.14 |    63,404.14 |         0.00 | 1,440,000.00 |
|  5 |  8% |  1,440,000.00 | 56,491.90 |    56,491.90 |         0.00 | 1,440,000.00 |
|  6 |  8% |  1,440,000.00 | 56,491.90 |    56,491.90 |         0.00 | 1,440,000.00 |
|  7 |  8% |  1,440,000.00 | 56,491.90 |    56,491.90 |         0.00 | 1,440,000.00 |
|  8 |  8% |  1,440,000.00 | 56,491.90 | 1,496,491.90 | 1,440,000.00 |         0.00 |

### 9.2. Ejemplo con cambio de tasa en método francés

En los primeros cuatro periodos se mantiene la cuota original de US$ 217,454.11. Al entrar al periodo 5, la tasa baja a 8%, por lo que se recalcula la cuota con el saldo pendiente y las cuatro cuotas restantes.

| Nº | TEA | Saldo inicial |   Interés |      Cuota | Amortización |  Saldo final |
|---:|----:|--------------:|----------:|-----------:|-------------:|-------------:|
|  1 |  9% |  1,440,000.00 | 63,404.14 | 217,454.11 |   154,049.98 | 1,285,950.02 |
|  2 |  9% |  1,285,950.02 | 56,621.22 | 217,454.11 |   160,832.90 | 1,125,117.13 |
|  3 |  9% |  1,125,117.13 | 49,539.64 | 217,454.11 |   167,914.47 |   957,202.65 |
|  4 |  9% |    957,202.65 | 42,146.26 | 217,454.11 |   175,307.86 |   781,894.79 |
|  5 |  8% |    781,894.79 | 30,674.11 | 215,013.72 |   184,339.61 |   597,555.18 |
|  6 |  8% |    597,555.18 | 23,442.38 | 215,013.72 |   191,571.35 |   405,983.84 |
|  7 |  8% |    405,983.84 | 15,926.94 | 215,013.72 |   199,086.78 |   206,897.05 |
|  8 |  8% |    206,897.05 |  8,116.67 | 215,013.72 |   206,897.05 |         0.00 |

---

## 10. Plazos de gracia

Los plazos de gracia alteran el cronograma porque retrasan el pago de capital y, según el tipo de gracia, pueden aumentar el saldo.

| Tipo de gracia     | Qué paga el deudor | Qué pasa con los intereses  | Qué pasa con el saldo           |
|--------------------|-------------------:|-----------------------------|---------------------------------|
| Sin gracia `S`     |       Cuota normal | Se pagan dentro de la cuota | Disminuye según la amortización |
| Gracia total `T`   |       No paga nada | Se capitalizan              | Aumenta                         |
| Gracia parcial `P` |     Solo intereses | Se pagan                    | Se mantiene igual               |

### 10.1. Ejemplo francés con gracia total en el primer periodo

Condiciones:

| Dato               |            Valor |
|--------------------|-----------------:|
| Préstamo inicial   | US$ 1,440,000.00 |
| TEA periodos 1 a 4 |               9% |
| TEA periodos 5 a 8 |               8% |
| Frecuencia         |        Semestral |
| Método             |          Francés |
| Gracia total       |        Periodo 1 |

Cronograma:

| Nº | Gracia | Saldo inicial |   Interés |      Cuota | Amortización |  Saldo final |
|---:|:------:|--------------:|----------:|-----------:|-------------:|-------------:|
|  1 |   T    |  1,440,000.00 | 63,404.14 |       0.00 |         0.00 | 1,503,404.14 |
|  2 |   S    |  1,503,404.14 | 66,195.86 | 254,225.60 |   188,029.74 | 1,315,374.40 |
|  3 |   S    |  1,315,374.40 | 57,916.79 | 254,225.60 |   196,308.81 | 1,119,065.59 |
|  4 |   S    |  1,119,065.59 | 49,273.19 | 254,225.60 |   204,952.41 |   914,113.18 |
|  5 |   S    |    914,113.18 | 35,861.10 | 251,372.54 |   215,511.44 |   698,601.75 |
|  6 |   S    |    698,601.75 | 27,406.48 | 251,372.54 |   223,966.05 |   474,635.69 |
|  7 |   S    |    474,635.69 | 18,620.19 | 251,372.54 |   232,752.35 |   241,883.34 |
|  8 |   S    |    241,883.34 |  9,489.20 | 251,372.54 |   241,883.34 |         0.00 |

Lectura del resultado: como en el primer periodo no se paga nada, el interés se suma al saldo; por eso las cuotas posteriores son más altas que en el caso sin gracia.

### 10.2. Ejemplo francés con gracia total y gracia parcial

Condiciones adicionales:

| Tipo           | Periodo |
|----------------|--------:|
| Gracia total   |       1 |
| Gracia parcial |   2 y 3 |

Cronograma:

| Nº | Gracia | Saldo inicial |   Interés |      Cuota | Amortización |  Saldo final |
|---:|:------:|--------------:|----------:|-----------:|-------------:|-------------:|
|  1 |   T    |  1,440,000.00 | 63,404.14 |       0.00 |         0.00 | 1,503,404.14 |
|  2 |   P    |  1,503,404.14 | 66,195.86 |  66,195.86 |         0.00 | 1,503,404.14 |
|  3 |   P    |  1,503,404.14 | 66,195.86 |  66,195.86 |         0.00 | 1,503,404.14 |
|  4 |   S    |  1,503,404.14 | 66,195.86 | 341,538.35 |   275,342.49 | 1,228,061.65 |
|  5 |   S    |  1,228,061.65 | 48,177.45 | 337,705.42 |   289,527.97 |   938,533.68 |
|  6 |   S    |    938,533.68 | 36,819.13 | 337,705.42 |   300,886.29 |   637,647.40 |
|  7 |   S    |    637,647.40 | 25,015.22 | 337,705.42 |   312,690.20 |   324,957.19 |
|  8 |   S    |    324,957.19 | 12,748.23 | 337,705.42 |   324,957.19 |         0.00 |

Lectura del resultado: la gracia total aumenta el saldo; la gracia parcial impide que el saldo baje; como quedan menos periodos para amortizar, las cuotas normales posteriores suben de forma importante.

---

## 11. Comparación de métodos con el mismo préstamo base

Para el préstamo de US$ 1,440,000.00, TEA de 9%, frecuencia semestral y plazo de 4 años, los métodos se comportan de manera distinta.

| Método    | Primera cuota | Última cuota | Tipo de cuota                        | Intereses totales aproximados | Observación                             |
|-----------|--------------:|-------------:|--------------------------------------|------------------------------:|-----------------------------------------|
| Americano |     63,404.14 | 1,503,404.14 | Intereses periódicos y capital final |                    507,233.10 | Menor presión inicial, mayor pago final |
| Alemán    |    243,404.14 |   187,925.52 | Decreciente                          |                    285,318.62 | Reduce capital más rápido               |
| Francés   |    217,454.11 |   217,454.11 | Constante                            |                    299,632.91 | Más estable para presupuestar           |

Recomendación interpretativa:

- Si se prioriza **liquidez inicial**, el método americano puede parecer atractivo, pero concentra el riesgo al final.
- Si se prioriza **menor costo financiero total**, el método alemán suele ser más conveniente porque amortiza más capital desde el inicio.
- Si se prioriza **estabilidad de pagos**, el método francés es más fácil de administrar.

---

## 12. Método peruano o de cuota doble

### 12.1. Definición

El método peruano, también llamado método de la cuota doble, se caracteriza porque las cuotas que se cancelan después del 15 de julio o después del 15 de diciembre se pagan como cuotas dobles.

No se calcula con una fórmula directa de cuota constante tradicional. El procedimiento consiste en igualar el valor actual de todas las cuotas al monto del préstamo. Luego se obtiene la cuota simple `R` y las cuotas dobles serán `2R`.

### 12.2. Procedimiento

Para cada cuota se calcula un factor de descuento. Si la cuota es simple:

```text
Factor_t = 1 / (1 + TEP)^t
```

Si la cuota es doble:

```text
Factor_t = 2 / (1 + TEP)^t
```

Luego:

```text
Factor total = suma de todos los factores
R = Préstamo / Factor total
Cuota simple = R
Cuota doble = 2R
```

Después se construye el cronograma normal:

```text
Interés = Saldo inicial * TEP
Amortización = Cuota - Interés
Saldo final = Saldo inicial - Amortización
```

### 12.3. Ejemplo

Condiciones:

| Dato            |                                     Valor |
|-----------------|------------------------------------------:|
| Precio de venta |                          US$ 1,800,000.00 |
| Cuota inicial   |                                       20% |
| Préstamo        |                          US$ 1,440,000.00 |
| TEA             |                              9% constante |
| Frecuencia      |                                   Mensual |
| Plazo           |                                     1 año |
| Método          | Peruano, cuota doble en julio y diciembre |

Tasa mensual:

```text
TEM = (1 + 0.09)^(1/12) - 1
TEM ≈ 0.721%
```

Según el ejemplo del PDF:

```text
Factor total ≈ 13.3246410
R = 1,440,000.00 / 13.3246410
R ≈ 108,070.45
2R ≈ 216,140.91
```

Cronograma:

| Nº | Fecha |     Factor | Saldo inicial |   Interés |      Cuota | Amortización |  Saldo final |
|---:|-------|-----------:|--------------:|----------:|-----------:|-------------:|-------------:|
|  0 | 31/12 | 13.3246410 |               |           |            |              | 1,440,000.00 |
|  1 | 31/01 |  0.9928443 |  1,440,000.00 | 10,378.55 | 108,070.45 |    97,691.91 | 1,342,308.09 |
|  2 | 28/02 |  0.9857397 |  1,342,308.09 |  9,674.45 | 108,070.45 |    98,396.01 | 1,243,912.09 |
|  3 | 31/03 |  0.9786860 |  1,243,912.09 |  8,965.28 | 108,070.45 |    99,105.18 | 1,144,806.91 |
|  4 | 30/04 |  0.9716828 |  1,144,806.91 |  8,250.99 | 108,070.45 |    99,819.46 | 1,044,987.45 |
|  5 | 31/05 |  0.9647296 |  1,044,987.45 |  7,531.56 | 108,070.45 |   100,538.89 |   944,448.56 |
|  6 | 30/06 |  0.9578263 |    944,448.56 |  6,806.95 | 108,070.45 |   101,263.51 |   843,185.05 |
|  7 | 31/07 |  1.9019446 |    843,185.05 |  6,077.11 | 216,140.91 |   210,063.80 |   633,121.25 |
|  8 | 31/08 |  0.9441674 |    633,121.25 |  4,563.11 | 108,070.45 |   103,507.34 |   529,613.90 |
|  9 | 30/09 |  0.9374112 |    529,613.90 |  3,817.10 | 108,070.45 |   104,253.36 |   425,360.55 |
| 10 | 31/10 |  0.9307033 |    425,360.55 |  3,065.71 | 108,070.45 |   105,004.74 |   320,355.80 |
| 11 | 30/11 |  0.9240434 |    320,355.80 |  2,308.91 | 108,070.45 |   105,761.55 |   214,594.26 |
| 12 | 31/12 |  1.8348624 |    214,594.26 |  1,546.65 | 216,140.91 |   214,594.26 |         0.00 |

Nota técnica: en el enunciado del PDF se indica fecha de préstamo 31 de diciembre de 2022, mientras que la tabla del ejemplo muestra 2012/2013. El procedimiento financiero no cambia; lo relevante es la secuencia mensual y la identificación de julio y diciembre como meses de cuota doble.

---

## 13. Ejemplo comparativo con gracia y tasas variables

El PDF presenta un caso adicional con un precio de venta de US$ 18,500.00, cuota inicial de 25%, plazo de 2 años, pagos cada 4 meses, TEA de 9.5% durante el primer año y 11.5% durante el segundo, además de un periodo de gracia total y uno de gracia parcial al inicio.

Datos base:

| Dato            |         Valor |
|-----------------|--------------:|
| Precio de venta | US$ 18,500.00 |
| Cuota inicial   |           25% |
| Préstamo        | US$ 13,875.00 |
| Frecuencia      | Cuatrimestral |
| Plazo           |        2 años |
| Periodos        |             6 |
| TEA año 1       |          9.5% |
| TEC año 1       |    3.0713679% |
| TEA año 2       |         11.5% |
| TEC año 2       |    3.6951130% |
| Gracia total    |     Periodo 1 |
| Gracia parcial  |     Periodo 2 |

### 13.1. Resultado con método americano

| Nº | Gracia | Saldo inicial | Interés |     Cuota | Amortización | Saldo final |
|---:|:------:|--------------:|--------:|----------:|-------------:|------------:|
|  1 |   T    |     13,875.00 |  426.15 |      0.00 |         0.00 |   14,301.15 |
|  2 |   P    |     14,301.15 |  439.24 |    439.24 |         0.00 |   14,301.15 |
|  3 |   S    |     14,301.15 |  439.24 |    439.24 |         0.00 |   14,301.15 |
|  4 |   S    |     14,301.15 |  528.44 |    528.44 |         0.00 |   14,301.15 |
|  5 |   S    |     14,301.15 |  528.44 |    528.44 |         0.00 |   14,301.15 |
|  6 |   S    |     14,301.15 |  528.44 | 14,829.60 |    14,301.15 |        0.00 |

### 13.2. Resultado con método alemán

| Nº | Gracia | Saldo inicial | Interés |    Cuota | Amortización | Saldo final |
|---:|:------:|--------------:|--------:|---------:|-------------:|------------:|
|  1 |   T    |     13,875.00 |  426.15 |     0.00 |         0.00 |   14,301.15 |
|  2 |   P    |     14,301.15 |  439.24 |   439.24 |         0.00 |   14,301.15 |
|  3 |   S    |     14,301.15 |  439.24 | 4,014.53 |     3,575.29 |   10,725.86 |
|  4 |   S    |     10,725.86 |  396.33 | 3,971.62 |     3,575.29 |    7,150.58 |
|  5 |   S    |      7,150.58 |  264.22 | 3,839.51 |     3,575.29 |    3,575.29 |
|  6 |   S    |      3,575.29 |  132.11 | 3,707.40 |     3,575.29 |        0.00 |

### 13.3. Resultado con método francés

| Nº | Gracia | Saldo inicial | Interés |    Cuota | Amortización | Saldo final |
|---:|:------:|--------------:|--------:|---------:|-------------:|------------:|
|  1 |   T    |     13,875.00 |  426.15 |     0.00 |         0.00 |   14,301.15 |
|  2 |   P    |     14,301.15 |  439.24 |   439.24 |         0.00 |   14,301.15 |
|  3 |   S    |     14,301.15 |  439.24 | 3,853.97 |     3,414.72 |   10,886.43 |
|  4 |   S    |     10,886.43 |  402.27 | 3,900.23 |     3,497.96 |    7,388.46 |
|  5 |   S    |      7,388.46 |  273.01 | 3,900.23 |     3,627.22 |    3,761.25 |
|  6 |   S    |      3,761.25 |  138.98 | 3,900.23 |     3,761.25 |        0.00 |

Interpretación comparativa:

- El método americano mantiene la deuda casi intacta hasta el final.
- El método alemán reduce el saldo con una amortización constante después de la gracia.
- El método francés estabiliza las cuotas de amortización regular dentro de cada tramo de tasa.

---

## 14. Algoritmo general para construir un plan de pagos

Este procedimiento sirve para implementar el cálculo manualmente, en Excel, Python o cualquier sistema.

```text
Entrada:
  PV: precio de venta
  porcentaje_CI: porcentaje de cuota inicial
  TEA_t: tasa anual por periodo o tramo
  frecuencia: mensual, semestral, cuatrimestral, etc.
  años: plazo total
  método: americano, alemán, francés o peruano
  gracia_t: S, T o P por periodo

Proceso:
  1. Calcular cuota inicial:
       CI = PV * porcentaje_CI

  2. Calcular préstamo:
       C = PV - CI

  3. Calcular número de periodos:
       n = años * pagos_por_año

  4. Convertir TEA a TEP para cada periodo:
       TEP_t = (1 + TEA_t)^(1 / pagos_por_año) - 1

  5. Inicializar saldo:
       saldo = C

  6. Para cada periodo t desde 1 hasta n:
       interés = saldo * TEP_t

       Si gracia_t = T:
           cuota = 0
           amortización = 0
           saldo_final = saldo + interés

       Si gracia_t = P:
           cuota = interés
           amortización = 0
           saldo_final = saldo

       Si gracia_t = S:
           aplicar regla del método:

           Americano:
              Si t < n:
                 amortización = 0
                 cuota = interés
              Si t = n:
                 amortización = saldo
                 cuota = interés + amortización

           Alemán:
              amortización = saldo_base / periodos_restantes_de_amortización
              cuota = interés + amortización

           Francés:
              Si es primer periodo normal o cambia la tasa:
                 R = saldo * [TEP_t * (1 + TEP_t)^periodos_restantes] / [(1 + TEP_t)^periodos_restantes - 1]
              cuota = R
              amortización = cuota - interés

           Peruano:
              cuota = R o 2R según fecha
              amortización = cuota - interés

           saldo_final = saldo - amortización

       Guardar fila del cronograma.
       saldo = saldo_final

Salida:
  Tabla con saldo inicial, interés, cuota, amortización y saldo final por periodo.
```

---

## 15. Fórmulas útiles en Excel

Supuestos:

- `B2`: precio de venta.
- `B3`: porcentaje de cuota inicial.
- `B4`: TEA.
- `B5`: pagos por año.
- `B6`: años.
- `B7`: préstamo.
- `B8`: número de periodos.
- `B9`: TEP.

### 15.1. Datos base

```excel
B7 = B2 * (1 - B3)
B8 = B5 * B6
B9 = (1 + B4)^(1 / B5) - 1
```

### 15.2. Interés

Si el saldo inicial del periodo está en `E12` y la TEP en `C12`:

```excel
F12 = E12 * C12
```

### 15.3. Método alemán

Si el préstamo está en `$B$7` y el número de periodos en `$B$8`:

```excel
H12 = $B$7 / $B$8
G12 = F12 + H12
I12 = E12 - H12
```

Con gracia, conviene calcular la amortización como:

```excel
H12 = saldo_pendiente / periodos_restantes_sin_gracia
```

### 15.4. Método francés

Cuota constante:

```excel
R = C * (i * (1 + i)^n) / ((1 + i)^n - 1)
```

En Excel:

```excel
= C * (i * (1 + i)^n) / ((1 + i)^n - 1)
```

También puede usarse la función financiera `PAGO`, teniendo cuidado con los signos:

```excel
=PAGO(i, n, -C)
```

Cuando cambia la tasa y debe recalcularse desde una cuota específica:

```excel
= SI * (i * (1 + i)^periodos_restantes) / ((1 + i)^periodos_restantes - 1)
```

### 15.5. Método peruano

Para una cuota simple en el periodo `t`:

```excel
= 1 / (1 + i)^t
```

Para una cuota doble en el periodo `t`:

```excel
= 2 / (1 + i)^t
```

Cuota simple:

```excel
R = Préstamo / SUMA(factores)
```

---

## 16. Reglas de validación del cronograma

Al terminar el cálculo, se debe revisar lo siguiente:

| Validación         | Criterio esperado                                                 | Qué significa si falla                               |
|--------------------|-------------------------------------------------------------------|------------------------------------------------------|
| Saldo final último | Debe ser 0 o muy cercano a 0                                      | Hay error de fórmula, redondeo o cuota mal calculada |
| Interés            | Debe ser `Saldo inicial * TEP`                                    | La tasa usada no coincide con el periodo             |
| Cuota              | Debe ser `Interés + Amortización`                                 | Hay inconsistencia en la fila                        |
| Saldo final        | Debe ser `Saldo inicial - Amortización`, salvo gracia total       | Se aplicó mal la amortización                        |
| Gracia total       | El saldo debe aumentar por intereses                              | No se capitalizó el interés                          |
| Gracia parcial     | El saldo debe mantenerse                                          | Se amortizó capital por error                        |
| Método alemán      | La amortización debe ser constante en periodos normales           | Se mezcló con método francés                         |
| Método francés     | La cuota debe ser constante dentro de cada tramo de tasa          | No se recalculó correctamente o cambió la tasa       |
| Método americano   | La amortización debe ser cero hasta el último periodo             | Se amortizó capital antes del vencimiento            |
| Método peruano     | Julio y diciembre deben tener cuota doble si la fecha corresponde | No se aplicó el factor 2                             |

---

## 17. Errores comunes

| Error                                                                     | Consecuencia                   | Corrección                                                |
|---------------------------------------------------------------------------|--------------------------------|-----------------------------------------------------------|
| Usar TEA directamente como tasa del periodo                               | Intereses sobreestimados       | Convertir TEA a TEP                                       |
| Olvidar la cuota inicial                                                  | Se financia más de lo correcto | Calcular primero el préstamo neto                         |
| No recalcular la cuota francesa cuando cambia la tasa                     | Saldo final distinto de cero   | Recalcular con saldo pendiente y periodos restantes       |
| Tratar la gracia total como gracia parcial                                | El saldo queda subestimado     | Capitalizar el interés en gracia total                    |
| Aplicar amortización constante sobre el préstamo original luego de gracia | Puede no cerrar el cronograma  | Dividir el saldo pendiente entre periodos restantes       |
| No considerar fechas en el método peruano                                 | Cuotas dobles mal ubicadas     | Identificar pagos posteriores al 15 de julio y diciembre  |
| Redondear en cada paso de manera excesiva                                 | Diferencias acumuladas         | Mantener decimales internos y redondear solo para mostrar |

---

## 18. Guía rápida para elegir un método

| Situación                                                    | Método más conveniente | Justificación                                                   |
|--------------------------------------------------------------|------------------------|-----------------------------------------------------------------|
| Se necesita pagar poco al inicio y se espera liquidez futura | Americano              | Solo se pagan intereses antes del vencimiento                   |
| Se desea reducir rápido el capital                           | Alemán                 | La amortización constante baja el saldo desde el primer periodo |
| Se necesita una cuota estable para presupuestar              | Francés                | La cuota se mantiene constante dentro de cada tramo             |
| Los ingresos fuertes llegan en julio y diciembre             | Peruano                | La estructura de cuota doble se adapta a esos meses             |
| Se quiere pagar menos intereses totales                      | Usualmente alemán      | Al amortizar más capital temprano, baja la base de intereses    |
| Se quiere facilidad de comunicación al cliente               | Francés                | La cuota constante es más comprensible                          |

---

## 19. Conceptos financieros ampliados para trabajar un préstamo completo

Las secciones anteriores explican la estructura de los métodos de amortización. Sin embargo, en un caso real no basta con calcular capital e intereses: también deben incorporarse costos iniciales, costos periódicos, seguros y métricas de costo efectivo. Esta sección complementa el cronograma para que pueda usarse en ejercicios más completos o en una hoja de cálculo financiera.

### 19.1. Préstamo o capital financiado

El **préstamo** es el monto total de dinero solicitado a una institución financiera para financiar la adquisición de un activo. No siempre equivale solo al precio del activo menos la cuota inicial, porque en muchos créditos se financian también costos iniciales de la operación.

```text
Préstamo = Precio del activo - Cuota inicial + Costos iniciales
```

Donde:

```text
Cuota inicial = Precio del activo * % cuota inicial
```

Si los costos iniciales no son financiados por el banco y se pagan al contado, no se suman al préstamo; en ese caso se consideran como desembolso propio del cliente. Por ello, antes de construir el cronograma debe identificarse si cada gasto se **financia** o se **paga directamente**.

### 19.2. Costos o gastos iniciales

Los **costos iniciales** son gastos asociados a la formalización, evaluación o activación del préstamo. Cuando el contrato indica que se financian, se agregan al capital del préstamo.

```text
Costos iniciales = Costo notarial
                  + Costo de registros públicos
                  + Costo de tasación
                  + Costo por estudio de títulos
                  + Comisión de activación
```

| Costo inicial          | Descripción                                        | Tratamiento en el cronograma         |
|------------------------|----------------------------------------------------|--------------------------------------|
| Costo notarial         | Gasto para elevar la operación a escritura pública | Se suma al préstamo si es financiado |
| Registros públicos     | Inscripción del activo, garantía o transferencia   | Se suma al préstamo si es financiado |
| Tasación               | Evaluación del valor del bien                      | Se suma al préstamo si es financiado |
| Estudio de títulos     | Revisión legal del bien o garantía                 | Se suma al préstamo si es financiado |
| Comisión de activación | Cobro por apertura o activación del crédito        | Se suma al préstamo si es financiado |

### 19.3. Cuota del préstamo

La **cuota del préstamo** es el pago periódico calculado por el método de amortización elegido. Contiene dos componentes principales:

```text
Cuota del préstamo = Amortización del capital + Intereses
```

No debe confundirse con la **cuota total**, porque la cuota total incluye también costos periódicos como seguros, portes o gastos administrativos.

### 19.4. Amortización del capital

La **amortización del capital** es la parte de la cuota destinada a reducir el saldo del préstamo.

| Método    | Regla de amortización                                                      | Fórmula o criterio                                                             |
|-----------|----------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| Americano | El capital se amortiza íntegramente al vencimiento                         | `Amortización = 0` hasta la última cuota; en la última: `Amortización = Saldo` |
| Alemán    | La amortización es fija en los periodos normales                           | `Amortización = Préstamo / número de periodos`                                 |
| Francés   | La amortización es creciente                                               | `Amortización = Cuota - Intereses`                                             |
| Peruano   | La amortización es variable y tiende a crecer, con saltos en cuotas dobles | `Amortización = Cuota - Intereses`                                             |

En el método alemán, si existen periodos de gracia, puede ser necesario recalcular la amortización fija usando el saldo pendiente y los periodos restantes de amortización efectiva.

### 19.5. Intereses

Los **intereses** representan el costo de usar dinero prestado. En cada periodo se calculan sobre el saldo pendiente del préstamo y con la tasa efectiva correspondiente a la frecuencia de pago.

```text
Intereses = Saldo del préstamo * i%
```

Donde `i%` es la tasa efectiva del periodo: mensual, bimestral, trimestral, cuatrimestral, semestral, anual, etc.

| Método    | Comportamiento de los intereses                                                 | Razón financiera                                      |
|-----------|---------------------------------------------------------------------------------|-------------------------------------------------------|
| Americano | Intereses fijos, si la tasa no cambia y no hay gracia total                     | El capital permanece intacto hasta el vencimiento     |
| Alemán    | Intereses decrecientes                                                          | El saldo baja por amortizaciones fijas                |
| Francés   | Intereses decrecientes                                                          | El saldo baja progresivamente                         |
| Peruano   | Intereses decrecientes, aunque con reducciones más fuertes en julio y diciembre | Las cuotas dobles amortizan más capital en esos meses |

### 19.6. Costos o gastos periódicos

Los **costos periódicos** son pagos adicionales que se cobran junto con la cuota del préstamo. No amortizan capital y, por tanto, no reducen el saldo de la deuda.

```text
Costos periódicos = Comisiones
                  + Portes
                  + Gastos de administración
                  + Seguro de desgravamen
                  + Seguro contra todo riesgo
```

La regla operativa más importante es la siguiente: **los costos o gastos periódicos se pagan siempre, incluso durante los periodos de gracia**. La gracia puede suspender o reducir el pago del préstamo, pero no necesariamente elimina seguros, comisiones o gastos administrativos.

### 19.7. Seguro de desgravamen: TSD

La **TSD** es la tasa del seguro de desgravamen. Este seguro cubre la deuda frente a riesgos como fallecimiento o invalidez del deudor, según las condiciones de la póliza. Suele contratarse en préstamos hipotecarios, vehiculares, de consumo y tarjetas de crédito.

```text
Seguro de desgravamen = Saldo del préstamo * TSD%
```

Como se calcula sobre el saldo del préstamo, normalmente disminuye cuando el saldo baja.

### 19.8. Seguro contra todo riesgo: TSR

La **TSR** es la tasa del seguro contra todo riesgo. Este seguro cubre una gama amplia de riesgos asociados al bien financiado, según la póliza. En ejercicios financieros suele calcularse sobre el precio de venta del activo asegurado.

```text
Seguro contra todo riesgo = Precio de venta del bien * TSR%
```

Como se calcula sobre el precio del bien, puede mantenerse constante durante todo el cronograma si el precio base no cambia.

### 19.9. Cuota total

La **cuota total** es el pago completo que realiza el deudor en cada periodo. Incluye la cuota del préstamo y los costos periódicos.

```text
Cuota total = Cuota del préstamo + Costos periódicos
```

En una tabla completa conviene separar ambos niveles:

| Campo              | Qué representa                       | ¿Reduce el saldo del préstamo?                 |
|--------------------|--------------------------------------|------------------------------------------------|
| Intereses          | Costo financiero del periodo         | No                                             |
| Amortización       | Devolución del capital               | Sí                                             |
| Cuota del préstamo | Intereses + amortización             | Parcialmente, solo por la amortización         |
| Costos periódicos  | Seguros, comisiones, portes y gastos | No                                             |
| Cuota total        | Pago completo del cliente            | Solo reduce saldo por la parte de amortización |

---

## 20. Costo financiero y comparación entre métodos

El **costo financiero** de un préstamo se observa principalmente en los intereses pagados. Si dos préstamos tienen la misma tasa, el mismo plazo y el mismo capital, el método de amortización puede cambiar el total de intereses porque modifica la velocidad con la que baja el saldo.

### 20.1. Orden típico de costo financiero

| Método    | Costo financiero relativo                               | Justificación                                                                                                                     |
|-----------|---------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| Americano | Más costoso                                             | El capital se mantiene intacto hasta el final; por eso se pagan intereses sobre el saldo completo durante casi todo el cronograma |
| Peruano   | Más costoso que el francés, pero menos que el americano | Mantiene una lógica parecida a cuotas fijas, pero las cuotas dobles concentran amortización en julio y diciembre                  |
| Francés   | Intermedio                                              | La cuota es constante; amortiza poco al inicio y más al final                                                                     |
| Alemán    | Menos costoso                                           | Amortiza capital de forma constante desde la primera cuota normal, reduciendo antes la base sobre la cual se calculan intereses   |

Este orden es una regla práctica cuando se comparan préstamos con condiciones equivalentes. Puede variar si existen cambios de tasa, comisiones, seguros, periodos de gracia, penalidades o estructuras especiales de pago.

### 20.2. Ejemplo comparativo simple de costo financiero

Supuestos:

| Dato                         |          Valor |
|------------------------------|---------------:|
| Préstamo                     |       1,200.00 |
| Tasa efectiva mensual        |             1% |
| Plazo                        |       12 meses |
| Gracia                       |             No |
| Costos periódicos            |             No |
| Cuotas dobles método peruano | Mes 7 y mes 12 |

| Método    | Total pagado | Intereses totales | Lectura                                                   |
|-----------|-------------:|------------------:|-----------------------------------------------------------|
| Americano |     1,344.00 |            144.00 | Paga intereses sobre todo el capital durante los 12 meses |
| Alemán    |     1,278.00 |             78.00 | Reduce el saldo de forma constante desde el primer mes    |
| Francés   |     1,279.42 |             79.42 | Mantiene cuota fija y amortización creciente              |
| Peruano   |     1,284.87 |             84.87 | Usa cuota simple y cuotas dobles en julio y diciembre     |

La comparación confirma la lógica financiera: a mayor velocidad de amortización, menor interés acumulado; a mayor postergación del capital, mayor costo financiero.

---

## 21. Periodo de diferimiento o de gracia

Un **periodo de gracia** permite postergar total o parcialmente el pago del préstamo. En el cronograma se representa con una marca de estado: `T` para gracia total, `P` para gracia parcial o normal y `S` para periodo sin gracia.

### 21.1. Gracia total

En la **gracia total** no se amortiza capital y tampoco se pagan los intereses del préstamo. Los intereses se capitalizan; es decir, se suman al saldo y encarecen el préstamo.

```text
Cuota del préstamo = 0
Amortización = 0
Interés = Saldo inicial * TEP
Saldo final = Saldo inicial + Interés
```

### 21.2. Gracia parcial o normal

En la **gracia parcial** no se amortiza capital, pero sí se pagan los intereses. Como los intereses no se capitalizan, el saldo del préstamo no aumenta.

```text
Cuota del préstamo = Intereses
Amortización = 0
Interés = Saldo inicial * TEP
Saldo final = Saldo inicial
```

### 21.3. Costos periódicos durante la gracia

Los costos periódicos se agregan al pago del cliente aunque exista gracia.

| Tipo de periodo |       Cuota del préstamo | Costos periódicos |                              Cuota total |                         Saldo final |
|-----------------|-------------------------:|------------------:|-----------------------------------------:|------------------------------------:|
| Gracia total    |                        0 |          Se pagan |                  `0 + costos periódicos` | Aumenta por intereses capitalizados |
| Gracia parcial  |                Intereses |          Se pagan |          `intereses + costos periódicos` |                         Se mantiene |
| Sin gracia      | Intereses + amortización |          Se pagan | `cuota del préstamo + costos periódicos` |                           Disminuye |

### 21.4. Ejemplo breve de gracia con costos periódicos

Supuestos:

| Dato              |     Valor |
|-------------------|----------:|
| Saldo inicial     | 10,000.00 |
| TEP               |        2% |
| Costos periódicos |     50.00 |

| Caso                            | Interés | Cuota del préstamo | Costos periódicos | Cuota total pagada | Saldo final |
|---------------------------------|--------:|-------------------:|------------------:|-------------------:|------------:|
| Gracia total                    |  200.00 |               0.00 |             50.00 |              50.00 |   10,200.00 |
| Gracia parcial                  |  200.00 |             200.00 |             50.00 |             250.00 |   10,000.00 |
| Sin gracia, amortización de 800 |  200.00 |           1,000.00 |             50.00 |           1,050.00 |    9,200.00 |

La diferencia central es que en la gracia total el cliente paga menos hoy, pero el préstamo aumenta; en la gracia parcial paga los intereses y evita que la deuda crezca.

---

## 22. Tasa de Costo Efectivo Anual, VAN y TIR aplicados al préstamo

Cuando se comparan préstamos reales, la tasa de interés no es suficiente. La **TCEA** incorpora el efecto conjunto de intereses, comisiones, gastos, seguros y demás pagos asociados al crédito.

### 22.1. Factores que afectan el costo efectivo

| Factor                      | Ejemplos                                                                                   | Efecto en el préstamo                                                    |
|-----------------------------|--------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| Comisiones                  | Evaluación, activación, desembolso, prepago, cobranza, renovación                          | Aumentan el costo total del crédito                                      |
| Gastos                      | Gastos administrativos, fotocopias, portes, mantenimiento de cuenta, protesto, notariales  | Elevan los pagos iniciales o periódicos                                  |
| Seguros                     | Desgravamen, seguro del bien financiado                                                    | Se agregan a la cuota total                                              |
| Retenciones                 | Retenciones remuneradas o no remuneradas, saldos mínimos, fondos en garantía inmovilizados | Reducen el dinero efectivamente disponible o aumentan el costo económico |
| Penalidades                 | Interés moratorio por pago fuera de fecha                                                  | Castigan atrasos y elevan el costo                                       |
| Forma de cobro de intereses | Adelantados o vencidos                                                                     | Cambia el costo financiero real                                          |
| Tipo de cambio              | Créditos en moneda extranjera                                                              | Puede aumentar o reducir el pago efectivo en moneda local                |
| Modificación de plazos      | Reprogramaciones, ampliaciones o reducciones                                               | Cambia el valor financiero del crédito                                   |

### 22.2. TCEA y tasa efectiva del periodo

La **TCEA** es la Tasa de Costo Efectivo Anual. Resume el costo anual real del crédito considerando todos los pagos obligatorios asociados.

Si se conoce la TCEA y se necesita la tasa efectiva del periodo para un cronograma con `c` pagos por año:

```text
TEP = (1 + TCEA)^(1/c) - 1
```

Donde `c` es el número de periodos de pago por año.

| Frecuencia    | `c` |
|---------------|----:|
| Mensual       |  12 |
| Bimestral     |   6 |
| Trimestral    |   4 |
| Cuatrimestral |   3 |
| Semestral     |   2 |
| Anual         |   1 |

Si se calcula primero una TIR periódica del préstamo, la TCEA equivalente puede obtenerse así:

```text
TCEA = (1 + TIR periódica)^c - 1
```

### 22.3. VAN aplicado al préstamo

Desde la perspectiva del deudor, el préstamo genera un flujo positivo al inicio porque recibe financiamiento, y luego genera flujos negativos por las cuotas totales pagadas.

```text
VAN = Desembolso inicial - Σ [Cuota total_t / (1 + r)^t]
```

Donde:

| Variable             | Significado                                                                                             |
|----------------------|---------------------------------------------------------------------------------------------------------|
| `Cuota total_t`      | Pago total del periodo `t`, incluyendo capital, intereses y costos periódicos                           |
| `r`                  | Tasa de descuento del deudor, también entendida como COK o tasa mínima de rendimiento del dinero propio |
| `Desembolso inicial` | Monto del préstamo recibido o financiamiento efectivamente aprovechado                                  |
| `t`                  | Número de periodo                                                                                       |
| `n`                  | Plazo total del préstamo                                                                                |

Interpretación en el contexto de un préstamo:

| Resultado | Interpretación                                                               | Decisión financiera                                                            |
|-----------|------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| `VAN = 0` | El costo del préstamo es equivalente al rendimiento exigido al dinero propio | Es indiferente usar capital propio o deuda, desde el punto de vista financiero |
| `VAN > 0` | El préstamo es barato porque su costo efectivo es menor que el COK           | Conviene endeudarse si el dinero propio puede rendir más                       |
| `VAN < 0` | El préstamo es caro porque su costo efectivo supera el COK                   | Endeudarse destruye valor frente al uso de capital propio                      |

### 22.4. TIR aplicada al préstamo

En proyectos de inversión, la TIR mide rentabilidad; en préstamos, desde la perspectiva del deudor, mide el costo financiero efectivo. Cuando se anualiza e incluye todos los pagos obligatorios, se interpreta como TCEA.

La ecuación general es:

```text
0 = Préstamo - FC1/(1 + TIR)^1 - FC2/(1 + TIR)^2 - ... - FCn/(1 + TIR)^n
```

Donde `FC1`, `FC2`, ..., `FCn` son las cuotas totales pagadas en cada periodo.

En Excel, si el primer flujo es el financiamiento positivo y luego se registran las cuotas totales como flujos negativos:

```excel
=TIR(rango_de_flujos)
```

Ejemplo de estructura:

```excel
=TIR({+Financiamiento; -FC1; -FC2; ...; -FCn})
```

Si la TIR obtenida es mensual, debe anualizarse para obtener la TCEA:

```excel
TCEA = (1 + TIR_mensual)^12 - 1
```

---

## 23. Ejemplo integral con costos iniciales, seguros y cuota total

Este ejemplo muestra cómo pasar de un cronograma de préstamo a un cronograma de pago total.

### 23.1. Datos del crédito

| Concepto                     |      Valor |
|------------------------------|-----------:|
| Precio del activo            | 100,000.00 |
| Cuota inicial                |        20% |
| Costo notarial               |     800.00 |
| Registros públicos           |     400.00 |
| Tasación                     |     300.00 |
| Estudio de títulos           |     200.00 |
| Comisión de activación       |     100.00 |
| Costos iniciales financiados |   1,800.00 |

Cálculo:

```text
Cuota inicial = 100,000.00 * 20% = 20,000.00
Costos iniciales = 800 + 400 + 300 + 200 + 100 = 1,800.00
Préstamo = 100,000.00 - 20,000.00 + 1,800.00 = 81,800.00
```

### 23.2. Costos periódicos del primer periodo

Supuestos:

| Concepto                 |             Valor |
|--------------------------|------------------:|
| Saldo del préstamo       |         81,800.00 |
| TSD                      | 0.05% por periodo |
| TSR                      | 0.03% por periodo |
| Portes y administración  |             50.00 |
| Precio de venta del bien |        100,000.00 |

Cálculo:

```text
Seguro de desgravamen = 81,800.00 * 0.05% = 40.90
Seguro contra todo riesgo = 100,000.00 * 0.03% = 30.00
Costos periódicos = 40.90 + 30.00 + 50.00 = 120.90
```

Si la cuota del préstamo del periodo fuera 2,650.00:

```text
Cuota total = Cuota del préstamo + Costos periódicos
Cuota total = 2,650.00 + 120.90 = 2,770.90
```

### 23.3. Fila recomendada para cronograma completo

| Nº | Saldo inicial |   Interés | Amortización |         Cuota préstamo | Seguro desgravamen | Seguro todo riesgo | Otros costos |             Cuota total |        Saldo final |
|---:|--------------:|----------:|-------------:|-----------------------:|-------------------:|-------------------:|-------------:|------------------------:|-------------------:|
|  1 |     81,800.00 | según TEP | según método | interés + amortización |              40.90 |              30.00 |        50.00 | cuota préstamo + 120.90 | según amortización |

Esta estructura permite distinguir entre el saldo financiero del préstamo y el monto real que el cliente paga en cada periodo.

---

## 24. Reglas operativas para una IA o sistema de cálculo

Para que un humano, una IA o una hoja de cálculo construyan correctamente el plan de pagos, se recomienda seguir estas reglas:

| Paso | Regla                                                           | Control de calidad                                                                |
|-----:|-----------------------------------------------------------------|-----------------------------------------------------------------------------------|
|    1 | Identificar precio del activo, cuota inicial y costos iniciales | Verificar si los costos iniciales se financian o se pagan al contado              |
|    2 | Calcular el préstamo                                            | `Préstamo = precio - cuota inicial + costos iniciales financiados`                |
|    3 | Convertir la tasa anual a tasa del periodo                      | Usar `TEP = (1 + TEA)^(1/c) - 1` o `TEP = (1 + TCEA)^(1/c) - 1` según corresponda |
|    4 | Calcular intereses                                              | Siempre sobre el saldo inicial del periodo                                        |
|    5 | Aplicar el método de amortización                               | Americano, alemán, francés o peruano                                              |
|    6 | Aplicar gracia si existe                                        | Gracia total capitaliza intereses; gracia parcial paga intereses                  |
|    7 | Calcular costos periódicos                                      | Incluir seguros, comisiones, portes y gastos administrativos                      |
|    8 | Calcular cuota total                                            | `Cuota total = cuota del préstamo + costos periódicos`                            |
|    9 | Actualizar saldo                                                | Solo la amortización reduce saldo; los costos periódicos no reducen deuda         |
|   10 | Calcular TIR/TCEA si se comparan alternativas                   | Usar flujos con financiamiento positivo y cuotas totales negativas                |
|   11 | Evaluar VAN si existe COK                                       | Comparar costo del préstamo contra rendimiento del dinero propio                  |

---

## 25. Resumen conceptual final actualizado

Un plan de pagos no es solo una tabla de cuotas; es una representación financiera del modo en que se distribuye el costo del dinero, la devolución del capital y los costos asociados al crédito. La diferencia entre métodos no está únicamente en la tasa, sino en el momento en que se amortiza el capital y en los pagos adicionales que acompañan la operación.

El método americano posterga la amortización hasta el final y suele ser el más costoso en intereses. El método alemán amortiza capital de manera constante y normalmente genera el menor costo financiero. El método francés estabiliza la cuota y reparte la amortización de manera creciente. El método peruano conserva una lógica de cuota simple, pero duplica pagos en julio y diciembre para aprovechar ingresos extraordinarios.

Para trabajar correctamente con cualquier método se deben cumplir seis principios: calcular bien el préstamo inicial, convertir la tasa anual a tasa del periodo, calcular intereses sobre saldos reales, separar interés y amortización, añadir costos periódicos para obtener la cuota total y verificar el costo efectivo mediante TCEA, TIR o VAN cuando se comparan alternativas de financiamiento.

