# Tasa compuesta: explicación, fórmulas y ejemplos

## 1. ¿Qué es la tasa compuesta?

La **tasa compuesta** se usa cuando los intereses generados en un período se **suman al capital** y pasan a formar parte de la base sobre la cual se calculan los intereses del siguiente período. Este proceso se llama **capitalización**.

En otras palabras, en interés compuesto no solo gana intereses el capital inicial, sino también los intereses acumulados anteriormente. Por eso suele decirse que existe un efecto de **interés sobre interés**.

### Idea central

```text
Capital inicial + intereses del período = nuevo capital
Nuevo capital + nuevos intereses = capital mayor
Capital mayor + nuevos intereses = capital todavía mayor
```

La regla clave es:

> En interés compuesto, siempre manda el **período de capitalización**.

Si la tasa nominal dice “capitalizable mensualmente”, entonces los cálculos deben hacerse en meses. Si dice “capitalizable diariamente”, se trabaja en días. Si no se indica la capitalización, en el curso se asume capitalización diaria.

---

## 2. Diferencia entre interés simple e interés compuesto

| Criterio | Interés simple | Interés compuesto |
|---|---:|---:|
| Capital base | Permanece constante | Aumenta con cada capitalización |
| Capitalización | No existe | Sí existe |
| Intereses | Se calculan siempre sobre el capital inicial | Se calculan sobre capital + intereses acumulados |
| Uso típico | Operaciones donde se retiran intereses o se evita acumular deuda | Bancos, inversiones reinvertidas, tarjetas, préstamos con acumulación |
| Fórmula principal | `I = C * i * n` | `S = C * (1 + j/m)^n` |

### Como inversionista

En interés simple, el inversionista puede retirar periódicamente los intereses y mantener intacto el capital inicial. Esto da más liquidez, pero reduce el crecimiento acumulado.

En interés compuesto, el inversionista reinvierte los intereses. Esto genera un efecto acumulativo, porque cada período se gana interés sobre un capital cada vez mayor.

### Como prestatario

En interés simple, el prestatario evita que los intereses se acumulen sobre la deuda. Por eso, en préstamos hipotecarios, vehiculares o personales, las amortizaciones ayudan a reducir el capital pendiente.

En interés compuesto, si los intereses no se pagan o se acumulan, pasan a formar parte de la deuda. Esto puede hacer que el saldo crezca más rápido.

---

## 3. Tasa nominal, capitalización y tasa efectiva del período

La **tasa nominal** es una tasa de referencia. Por sí sola no basta para resolver un problema de interés compuesto, porque debe conocerse también **cada cuánto capitaliza**.

Por ejemplo:

```text
TNA 12% capitalizable mensualmente
```

significa:

```text
Tasa Nominal Anual = 12%
Capitalización = mensual
```

Entonces, la tasa aplicable a cada mes será:

```text
i = j / m = 12% / 12 = 1% mensual
```

Donde:

| Símbolo | Significado |
|---|---|
| `S` | Valor futuro, monto o valor acumulado |
| `C` | Valor actual, capital inicial o valor presente |
| `j` | Tasa nominal contratada, también puede representarse como `TN` |
| `m` | Número de capitalizaciones dentro del período en que está expresada la tasa nominal |
| `i` o `i'` | Tasa efectiva del período de capitalización |
| `n` | Número de períodos de capitalización durante el plazo de la operación |
| `I` | Interés generado |
| `TEP` | Tasa efectiva del período analizado |

---

## 4. Equivalencias de tiempo usadas en el curso

Para trabajar con tasas nominales se suelen usar años comerciales de 360 días.

| Período | Equivalencias |
|---|---|
| 1 año | 2 semestres, 3 cuatrimestres, 4 trimestres, 6 bimestres, 12 meses, 360 días |
| 1 semestre | 2 trimestres, 3 bimestres, 6 meses, 12 quincenas, 180 días |
| 1 cuatrimestre | 4 meses, 8 quincenas, 120 días |
| 1 trimestre | 3 meses, 6 quincenas, 90 días |
| 1 bimestre | 2 meses, 4 quincenas, 60 días |
| 1 mes | 2 quincenas, 30 días |
| 1 quincena | 15 días |

---

## 5. Cómo calcular `m`

`m` indica cuántas veces capitaliza la tasa nominal dentro del período en que dicha tasa está expresada.

Una forma práctica es:

```text
m = días del período de la tasa nominal / días del período de capitalización
```

Ejemplos:

| Tasa nominal | Capitalización | Cálculo de `m` | Resultado |
|---|---:|---:|---:|
| TNA capitalizable mensualmente | mensual | 360 / 30 | 12 |
| TNA capitalizable quincenalmente | quincenal | 360 / 15 | 24 |
| TNA capitalizable diariamente | diaria | 360 / 1 | 360 |
| TNS capitalizable mensualmente | mensual | 180 / 30 | 6 |
| TNT capitalizable mensualmente | mensual | 90 / 30 | 3 |
| TNC capitalizable bimestralmente | bimestral | 120 / 60 | 2 |

---

## 6. Cómo calcular `n`

`n` indica cuántos períodos de capitalización hay durante el plazo real de la operación.

```text
n = días del plazo de la operación / días del período de capitalización
```

Ejemplos:

| Plazo de la operación | Capitalización | Cálculo de `n` | Resultado |
|---|---:|---:|---:|
| 3 meses | mensual | 90 / 30 | 3 |
| 1 semestre | mensual | 180 / 30 | 6 |
| 45 días | quincenal | 45 / 15 | 3 |
| 100 días | diaria | 100 / 1 | 100 |
| 1 año | mensual | 360 / 30 | 12 |

---

## 7. Fórmulas principales

### 7.1 Tasa del período de capitalización

```text
i = j / m
```

Donde:

```text
i = tasa efectiva del período de capitalización
j = tasa nominal
m = número de capitalizaciones de la tasa nominal
```

Ejemplo:

```text
TNA 12% capitalizable mensualmente
j = 12% = 0.12
m = 12

i = 0.12 / 12 = 0.01 = 1% mensual
```

---

### 7.2 Valor futuro o monto

```text
S = C * (1 + j/m)^n
```

También puede escribirse como:

```text
S = C * (1 + i)^n
```

porque:

```text
i = j/m
```

---

### 7.3 Valor actual o valor presente

```text
C = S / (1 + j/m)^n
```

También:

```text
C = S * (1 + j/m)^(-n)
```

---

### 7.4 Interés compuesto generado

```text
I = S - C
```

Como `S = C * (1 + j/m)^n`, entonces:

```text
I = C * (1 + j/m)^n - C
```

Factorizando:

```text
I = C * [ (1 + j/m)^n - 1 ]
```

---

### 7.5 Tasa efectiva del período analizado

```text
TEP = S/C - 1
```

Si se quiere expresar en porcentaje:

```text
TEP% = (S/C - 1) * 100
```

---

### 7.6 Plazo o tiempo transcurrido

Partimos de:

```text
S = C * (1 + j/m)^n
```

Dividimos entre `C`:

```text
S/C = (1 + j/m)^n
```

Aplicamos logaritmo natural:

```text
LN(S/C) = LN[(1 + j/m)^n]
```

Usamos la propiedad:

```text
LN[(1 + j/m)^n] = n * LN(1 + j/m)
```

Entonces:

```text
n = LN(S/C) / LN(1 + j/m)
```

---

### 7.7 Tasa nominal buscada

Partimos de:

```text
S = C * (1 + j/m)^n
```

Dividimos entre `C`:

```text
S/C = (1 + j/m)^n
```

Aplicamos raíz enésima:

```text
(S/C)^(1/n) = 1 + j/m
```

Despejamos `j`:

```text
j = m * [ (S/C)^(1/n) - 1 ]
```

También puede escribirse como:

```text
TN = m * [ raíz n de (S/C) - 1 ]
```

---

### 7.8 Capital necesario para generar un interés específico

Si se conoce el interés `I` que se desea ganar:

```text
I = C * [ (1 + j/m)^n - 1 ]
```

Despejando `C`:

```text
C = I / [ (1 + j/m)^n - 1 ]
```

---

## 8. Propiedades matemáticas útiles

Para despejar tiempo o tasa se usan logaritmos y potencias.

```text
LN(A/B) = LN(A) - LN(B)
```

```text
LN[(1 + A)^n] = n * LN(1 + A)
```

```text
raíz n de X = X^(1/n)
```

---

## 9. Demostración de la fórmula de tasa compuesta

Supongamos que:

```text
j = tasa nominal
m = número de capitalizaciones de la tasa nominal
i = j/m
C = capital inicial
n = número de períodos de capitalización
```

La tasa que se aplica en cada período de capitalización es:

```text
i = j/m
```

### Primer período

El interés del primer período es:

```text
I1 = C * j/m * 1
```

El monto al final del primer período será:

```text
S1 = C + I1
S1 = C + C * j/m
S1 = C * (1 + j/m)
```

---

### Segundo período

Ahora el capital ya no es solo `C`, sino:

```text
S1 = C * (1 + j/m)
```

El interés del segundo período se calcula sobre `S1`:

```text
I2 = S1 * j/m * 1
```

Entonces:

```text
S2 = S1 + I2
S2 = C * (1 + j/m) + C * (1 + j/m) * j/m
```

Factorizando:

```text
S2 = C * (1 + j/m) * [1 + j/m]
S2 = C * (1 + j/m)^2
```

---

### Tercer período

Ahora el capital es:

```text
S2 = C * (1 + j/m)^2
```

El interés del tercer período se calcula sobre `S2`:

```text
I3 = S2 * j/m * 1
```

Entonces:

```text
S3 = S2 + I3
S3 = C * (1 + j/m)^2 + C * (1 + j/m)^2 * j/m
```

Factorizando:

```text
S3 = C * (1 + j/m)^2 * [1 + j/m]
S3 = C * (1 + j/m)^3
```

---

### Generalización

Después de `n` períodos de capitalización:

```text
S = C * (1 + j/m)^n
```

Esta es la fórmula general del interés compuesto con tasa nominal capitalizable.

---

## 10. Procedimiento general para resolver ejercicios

1. Identificar el capital inicial `C` o el monto futuro `S`.
2. Identificar la tasa nominal `j`.
3. Identificar el período en que está expresada la tasa nominal.
4. Identificar el período de capitalización.
5. Calcular `m`.
6. Calcular `n`.
7. Convertir la tasa porcentual a decimal.
8. Aplicar la fórmula correspondiente.
9. Interpretar el resultado.

---

## 11. Ejemplo 1: TNA 12% capitalizable mensualmente durante 3 meses

### Enunciado

Se invierte un capital de S/ 1,000 durante 3 meses a una **Tasa Nominal Anual de 12% capitalizable mensualmente**. ¿Cuál es el valor futuro?

### Datos

```text
C = 1,000
j = 12% anual = 0.12
Capitalización = mensual
m = 12
Plazo = 3 meses
n = 3
S = ?
```

### Tasa mensual

```text
i = j/m
i = 0.12/12
i = 0.01 = 1% mensual
```

### Cálculo del valor futuro

```text
S = C * (1 + j/m)^n
S = 1,000 * (1 + 0.12/12)^3
S = 1,000 * (1.01)^3
S = 1,030.30
```

### Interés generado

```text
I = S - C
I = 1,030.30 - 1,000
I = 30.30
```

### Tasa efectiva trimestral

```text
TEP = S/C - 1
TEP = 1,030.30/1,000 - 1
TEP = 0.0303 = 3.03%
```

### Respuesta

Al final de los 3 meses se obtiene:

```text
S = S/ 1,030.30
I = S/ 30.30
Tasa efectiva trimestral = 3.03%
```

---

## 12. Ejemplo 2: TNA 120% capitalizable mensualmente durante 3 meses

### Enunciado

Se invierte un capital de S/ 1,000 durante tres períodos mensuales a una **Tasa Nominal Anual de 120% capitalizable mensualmente**. ¿Qué sucede mes a mes y cuál es el valor futuro?

### Datos

```text
C = 1,000
j = 120% anual = 1.20
Capitalización = mensual
m = 12
Plazo = 3 meses
n = 3
S = ?
```

### Tasa mensual

```text
i = j/m
i = 1.20/12
i = 0.10 = 10% mensual
```

### Desarrollo mes a mes

| Mes | Capital inicial del período | Interés del período | Capital acumulado |
|---:|---:|---:|---:|
| 0 | 1,000.00 | — | 1,000.00 |
| 1 | 1,000.00 | 100.00 | 1,100.00 |
| 2 | 1,100.00 | 110.00 | 1,210.00 |
| 3 | 1,210.00 | 121.00 | 1,331.00 |

### Usando la fórmula directa

```text
S = C * (1 + j/m)^n
S = 1,000 * (1 + 1.20/12)^3
S = 1,000 * (1.10)^3
S = 1,331.00
```

### Crecimiento monetario del capital

```text
I = S - C
I = 1,331 - 1,000
I = 331
```

### Crecimiento porcentual o tasa efectiva trimestral

```text
TEP = S/C - 1
TEP = 1,331/1,000 - 1
TEP = 0.331 = 33.10%
```

### Respuesta

Al final del tercer mes se obtiene:

```text
S = S/ 1,331.00
I = S/ 331.00
Tasa efectiva trimestral = 33.10%
```

Aunque la tasa mensual es 10%, el crecimiento trimestral no es 30%, sino 33.10%, porque los intereses se capitalizan cada mes.

---

## 13. Ejemplo 3: Tasa Nominal Semestral de 6% capitalizable mensualmente

### Enunciado

Se invierten S/ 1,000 en un negocio de 1 cuatrimestre que rinde una **Tasa Nominal Semestral de 6% capitalizable mensualmente**. ¿Cuánto se acumula?

### Datos

```text
C = 1,000
j = 6% semestral = 0.06
Capitalización = mensual
m = 6
Plazo = 1 cuatrimestre = 4 meses
n = 4
S = ?
```

### Cálculo

```text
S = C * (1 + j/m)^n
S = 1,000 * (1 + 0.06/6)^4
S = 1,000 * (1.01)^4
S = 1,040.60
```

### Tasa efectiva cuatrimestral

```text
TEP = S/C - 1
TEP = 1,040.60/1,000 - 1
TEP = 0.0406 = 4.06%
```

### Respuesta

```text
S = S/ 1,040.60
Tasa efectiva cuatrimestral = 4.06%
```

---

## 14. Ejemplo 4: Valor actual de una deuda

### Enunciado

Pedro debe pagar US$ 12,000 dentro de 45 días. La deuda fue pactada a una **Tasa Nominal Anual de 15% capitalizable quincenalmente**. ¿Cuánto debería pagar hoy si desea prepagar la deuda?

### Datos

```text
S = 12,000
j = 15% anual = 0.15
Capitalización = quincenal
m = 360 / 15 = 24
Plazo = 45 días
n = 45 / 15 = 3
C = ?
```

### Cálculo

```text
C = S / (1 + j/m)^n
C = 12,000 / (1 + 0.15/24)^3
C = 12,000 / (1.00625)^3
C = 11,777.78
```

### Ahorro por prepago

```text
Ahorro = 12,000 - 11,777.78
Ahorro = 222.22
```

### Respuesta

```text
Debe pagar hoy US$ 11,777.78
Ahorro = US$ 222.22
```

---

## 15. Ejemplo 5: Calcular el tiempo necesario

### Enunciado

Un capital de US$ 1,350 debe acumular por lo menos US$ 1,475. Está expuesto a una **Tasa Nominal Semestral de 4% capitalizable mensualmente**. ¿En cuánto tiempo se logra?

### Datos

```text
C = 1,350
S = 1,475
j = 4% semestral = 0.04
Capitalización = mensual
m = 6
n = ?
```

### Cálculo del número de períodos mensuales

```text
n = LN(S/C) / LN(1 + j/m)
n = LN(1,475/1,350) / LN(1 + 0.04/6)
n = 13.3272 meses
```

### Conversión a días

```text
Tiempo = 13.3272 * 30
Tiempo = 399.82 días
```

Como se requiere acumular por lo menos US$ 1,475, se redondea hacia arriba:

```text
Tiempo = 400 días
```

### Respuesta

```text
Se requieren 400 días aproximadamente.
```

---

## 16. Ejemplo 6: Calcular la tasa nominal

### Enunciado

Juan prestó S/ 5,000 y después de 180 días recibió S/ 5,450. Si se analiza como una operación financiera con capitalización diaria, ¿cuál fue la Tasa Nominal Anual?

### Datos

```text
C = 5,000
S = 5,450
Capitalización = diaria
m = 360
Plazo = 180 días
n = 180
j = ?
```

### Fórmula

```text
j = m * [ (S/C)^(1/n) - 1 ]
```

### Cálculo

```text
j = 360 * [ (5,450/5,000)^(1/180) - 1 ]
j = 0.172396656
j = 17.2396656%
```

### Respuesta

```text
TNA = 17.2396656% capitalizable diariamente
```

---

## 17. Fórmulas en Excel

Supongamos que:

```text
C = capital
S = monto
j = tasa nominal en decimal
m = número de capitalizaciones
n = número de períodos
```

| Cálculo | Fórmula matemática | Fórmula en Excel |
|---|---|---|
| Valor futuro | `S = C*(1+j/m)^n` | `=C*(1+j/m)^n` |
| Valor actual | `C = S/(1+j/m)^n` | `=S/(1+j/m)^n` |
| Interés | `I = S-C` | `=S-C` |
| Tasa efectiva | `TEP = S/C-1` | `=S/C-1` |
| Tiempo | `n = LN(S/C)/LN(1+j/m)` | `=LN(S/C)/LN(1+j/m)` |
| Tasa nominal | `j = m*((S/C)^(1/n)-1)` | `=m*((S/C)^(1/n)-1)` |
| Capital para interés objetivo | `C = I/((1+j/m)^n-1)` | `=I/((1+j/m)^n-1)` |

---

## 18. Errores comunes

| Error | Corrección |
|---|---|
| Usar la tasa nominal directamente sin dividir entre `m` | Primero calcular `i = j/m` |
| Confundir `m` con `n` | `m` depende de la tasa nominal; `n` depende del plazo de la operación |
| Colocar la tasa como 12 en vez de 0.12 | En fórmulas, la tasa debe ir en decimal |
| Creer que 10% mensual por 3 meses siempre es 30% | En interés compuesto es `(1.10)^3 - 1 = 33.10%` |
| Ignorar la capitalización | La capitalización define la unidad de cálculo |
| No redondear hacia arriba cuando se pide alcanzar “por lo menos” un monto | Si el resultado es 399.82 días, se responde 400 días |

---

## 19. Resumen rápido

```text
1. La tasa nominal j es una referencia.
2. En interés compuesto, j necesita una capitalización.
3. La tasa del período es i = j/m.
4. El monto futuro es S = C*(1+j/m)^n.
5. El interés generado es I = S-C.
6. La tasa efectiva del período es TEP = S/C-1.
7. A menor período de capitalización, mayor crecimiento efectivo del capital.
```

---

## 20. Plantilla general para resolver

```text
Datos:
C =
S =
j =
Capitalización =
Plazo =
m =
n =

Tasa del período:
i = j/m

Valor futuro:
S = C*(1+j/m)^n

Interés:
I = S-C

Tasa efectiva:
TEP = S/C-1
```

