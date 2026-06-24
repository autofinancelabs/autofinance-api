# Tasa efectiva, tasa nominal y tasas equivalentes

## 1. Idea central

La **tasa de interés efectiva** mide cuánto crece realmente un capital en un período determinado. A diferencia de la tasa nominal, la tasa efectiva **ya incorpora el efecto de las capitalizaciones** ocurridas durante el plazo analizado.

En términos simples:

- La **tasa de interés simple** supone que los intereses no se reinvierten. Es decir, la ganancia se retira y no pasa a formar parte del capital.
- La **tasa de interés compuesta** supone que los intereses sí se capitalizan. Es decir, la ganancia se reinvierte y pasa a formar parte del capital para generar nuevos intereses.
- La **tasa nominal con capitalización** es una forma de expresar una tasa compuesta. Indica una tasa referida a un período, pero también señala cada cuánto se capitalizan los intereses.
- La **tasa efectiva** expresa el rendimiento real obtenido en un período específico, después de considerar todas las capitalizaciones.

Por eso, cuando se trabaja con tasas efectivas, la pregunta clave es:

> ¿Cuánto creció efectivamente mi capital en el período analizado?

La fórmula base es:

$$
TEP = \frac{S}{C} - 1
$$

Donde:

| Símbolo | Significado |
|---|---|
| $TEP$ | Tasa efectiva del período |
| $S$ | Monto o valor futuro |
| $C$ | Capital inicial, valor actual o valor presente |

Si el resultado se desea expresar como porcentaje, se multiplica por 100.

---

## 2. Tasa nominal y tasa efectiva

Una **tasa nominal** no representa necesariamente el rendimiento efectivo de la operación, porque depende de la frecuencia de capitalización.

Por ejemplo, una **TNA de 120% capitalizable mensualmente** no significa que en tres meses se gane simplemente 30%. Como la capitalización ocurre cada mes, los intereses de cada período se agregan al capital y también generan intereses.

La tasa periódica de capitalización se obtiene así:

$$
i = \frac{j}{m}
$$

Donde:

| Símbolo | Significado |
|---|---|
| $i$ | Tasa por período de capitalización |
| $j$ | Tasa nominal |
| $m$ | Número de capitalizaciones dentro del período nominal |

Ejemplo:

$$
i = \frac{120\%}{12} = 10\% \text{ mensual}
$$

---

## 3. Tasas equivalentes

Dos tasas son **equivalentes** cuando, aplicadas sobre el mismo capital inicial y durante el mismo plazo, producen el mismo monto final o valor futuro.

Esto puede ocurrir entre:

1. Una tasa nominal con capitalización y una tasa efectiva.
2. Una tasa efectiva de un período y otra tasa efectiva de un período distinto.
3. Una tasa nominal expresada de una forma y otra tasa nominal expresada con otra capitalización.

La equivalencia no depende de que las tasas tengan el mismo porcentaje, sino de que generen el mismo resultado financiero.

> Una TNA de 120% capitalizable mensualmente y una TET de 33.10% son equivalentes para un plazo de tres meses, porque ambas generan el mismo valor futuro sobre el mismo capital.

---

## 4. Convención de días usada

En los ejemplos financieros del curso se usa normalmente el año comercial de **360 días**.

| Período | Días |
|---|---:|
| Diario | 1 |
| Quincenal | 15 |
| Mensual | 30 |
| Bimestral | 60 |
| Trimestral | 90 |
| Cuatrimestral | 120 |
| Semestral | 180 |
| Anual | 360 |

---

## 5. Fórmulas principales

### 5.1. De tasa nominal a tasa efectiva

Se usa cuando se conoce una tasa nominal con capitalización y se desea hallar la tasa efectiva de un período.

$$
TEP = \left(1 + \frac{TN}{m}\right)^n - 1
$$

Donde:

| Símbolo | Significado |
|---|---|
| $TEP$ | Tasa efectiva del período buscado |
| $TN$ | Tasa nominal conocida |
| $m$ | Número de capitalizaciones dentro del período de la tasa nominal |
| $n$ | Número de capitalizaciones dentro del período efectivo buscado |

Para calcular $m$ y $n$:

$$
m = \frac{\text{días de la tasa nominal}}{\text{días de capitalización}}
$$

$$
n = \frac{\text{días de la tasa efectiva buscada}}{\text{días de capitalización}}
$$

---

### 5.2. De tasa efectiva a tasa nominal

Se usa cuando se conoce una tasa efectiva y se desea expresarla como tasa nominal con cierta capitalización.

$$
TN = m \left[(1 + TEP)^{\frac{1}{n}} - 1\right]
$$

Donde:

| Símbolo | Significado |
|---|---|
| $TN$ | Tasa nominal buscada |
| $TEP$ | Tasa efectiva conocida |
| $m$ | Número de capitalizaciones dentro del período nominal buscado |
| $n$ | Número de capitalizaciones dentro del período efectivo conocido |

Para calcular $m$ y $n$:

$$
m = \frac{\text{días de la tasa nominal buscada}}{\text{días de capitalización}}
$$

$$
n = \frac{\text{días de la tasa efectiva conocida}}{\text{días de capitalización}}
$$

---

### 5.3. De una tasa efectiva a otra tasa efectiva

Se usa cuando se conoce una tasa efectiva de un período y se desea hallar una tasa efectiva equivalente de otro período.

$$
TEP_2 = (1 + TEP_1)^{\frac{n_2}{n_1}} - 1
$$

Donde:

| Símbolo | Significado |
|---|---|
| $TEP_1$ | Tasa efectiva conocida |
| $TEP_2$ | Tasa efectiva buscada |
| $n_1$ | Días o unidades de tiempo de la tasa efectiva conocida |
| $n_2$ | Días o unidades de tiempo de la tasa efectiva buscada |

Importante: $n_1$ y $n_2$ deben estar expresados en la misma unidad de tiempo. Por ejemplo, ambos en días o ambos en meses.

---

### 5.4. Valor futuro con tasa efectiva

Permite hallar el monto acumulado al final de una operación.

$$
S = C(1 + TE)^n
$$

Si la tasa efectiva está dada para un período específico y la operación dura cierta cantidad de días:

$$
S = C(1 + TEP)^{\frac{\text{N.° días trasladar}}{\text{N.° días TEP}}}
$$

Donde:

| Símbolo | Significado |
|---|---|
| $S$ | Valor futuro o monto |
| $C$ | Capital inicial o valor actual |
| $TEP$ | Tasa efectiva conocida |
| N.° días trasladar | Plazo de la operación |
| N.° días TEP | Días correspondientes a la tasa efectiva conocida |

---

### 5.5. Valor presente con tasa efectiva

Permite hallar cuánto vale hoy un monto futuro.

$$
C = \frac{S}{(1 + TEP)^{\frac{\text{N.° días trasladar}}{\text{N.° días TEP}}}}
$$

---

### 5.6. Tasa efectiva del período a partir de capital y monto

Permite hallar la tasa efectiva de un período específico cuando se conocen el capital inicial y el monto final.

$$
TEP = \left(\frac{S}{C}\right)^{\frac{\text{N.° días TEP}}{\text{N.° días trasladar}}} - 1
$$

Si el período de la tasa buscada coincide con el plazo de la operación, la fórmula se reduce a:

$$
TEP = \frac{S}{C} - 1
$$

---

### 5.7. Tiempo o plazo con tasa efectiva

Primero se calcula el número de períodos:

$$
k = \frac{\ln(S/C)}{\ln(1 + TEP)}
$$

Si se desea el resultado en días:

$$
n = \frac{\ln(S/C)}{\ln(1 + TEP)} \times \text{N.° días TEP}
$$

Donde:

| Símbolo | Significado |
|---|---|
| $k$ | Número de períodos de la tasa efectiva |
| $n$ | Tiempo expresado en días |
| $TEP$ | Tasa efectiva conocida |
| N.° días TEP | Días correspondientes a la tasa conocida |

---

### 5.8. Capital necesario para generar un interés determinado

Si se conoce el interés que se desea ganar, se puede despejar el capital requerido.

Partimos de:

$$
I = S - C
$$

Y como:

$$
S = C(1 + TEP)^{\frac{\text{N.° días trasladar}}{\text{N.° días TEP}}}
$$

Entonces:

$$
I = C\left[(1 + TEP)^{\frac{\text{N.° días trasladar}}{\text{N.° días TEP}}} - 1\right]
$$

Finalmente:

$$
C = \frac{I}{(1 + TEP)^{\frac{\text{N.° días trasladar}}{\text{N.° días TEP}}} - 1}
$$

---

## 6. Resumen de fórmulas

| Caso | Fórmula | Uso |
|---|---|---|
| Tasa efectiva del período | $TEP = \frac{S}{C} - 1$ | Hallar el crecimiento porcentual del capital |
| De TN a TE | $TEP = \left(1 + \frac{TN}{m}\right)^n - 1$ | Convertir tasa nominal con capitalización en tasa efectiva |
| De TE a TN | $TN = m[(1 + TEP)^{1/n} - 1]$ | Convertir tasa efectiva en tasa nominal con capitalización |
| De TE a TE | $TEP_2 = (1 + TEP_1)^{n_2/n_1} - 1$ | Convertir una tasa efectiva en otra efectiva equivalente |
| Valor futuro | $S = C(1 + TEP)^{d/D}$ | Hallar el monto final |
| Valor presente | $C = \frac{S}{(1 + TEP)^{d/D}}$ | Hallar el valor actual de un monto futuro |
| Tiempo | $n = \frac{\ln(S/C)}{\ln(1 + TEP)} \times D$ | Hallar el plazo en días |
| Capital con interés deseado | $C = \frac{I}{(1 + TEP)^{d/D} - 1}$ | Hallar cuánto depositar para ganar cierto interés |

Donde $d$ es el plazo de la operación y $D$ es el número de días de la tasa efectiva conocida.

---

## 7. Ejemplo 1: TNA 120% capitalizable mensualmente durante 3 meses

### Datos

| Dato | Valor |
|---|---:|
| Capital inicial | S/ 1,000 |
| Tasa nominal anual | 120% |
| Capitalización | Mensual |
| Plazo | 3 meses |

La tasa mensual de capitalización es:

$$
i = \frac{j}{m} = \frac{120\%}{12} = 10\%
$$

### Evolución mes a mes

| Mes | Capital inicial del mes | Interés del mes | Capital acumulado |
|---:|---:|---:|---:|
| 0 | S/ 1,000.00 | — | S/ 1,000.00 |
| 1 | S/ 1,000.00 | S/ 100.00 | S/ 1,100.00 |
| 2 | S/ 1,100.00 | S/ 110.00 | S/ 1,210.00 |
| 3 | S/ 1,210.00 | S/ 121.00 | S/ 1,331.00 |

### Cálculo con fórmula de tasa nominal capitalizable

$$
S = C\left(1 + \frac{j}{m}\right)^n
$$

$$
S = 1,000\left(1 + \frac{1.20}{12}\right)^3
$$

$$
S = 1,000(1.10)^3
$$

$$
S = 1,331
$$

### Interés ganado

$$
I = S - C
$$

$$
I = 1,331 - 1,000
$$

$$
I = 331
$$

### Tasa efectiva trimestral

$$
TET = \frac{S}{C} - 1
$$

$$
TET = \frac{1,331}{1,000} - 1
$$

$$
TET = 0.331 = 33.10\%
$$

### Conclusión

Un capital inicial de **S/ 1,000** expuesto a una **TNA de 120% capitalizable mensualmente** se convierte, luego de tres meses, en **S/ 1,331**.

Por lo tanto, el crecimiento efectivo del capital en tres meses fue de **S/ 331**, equivalente a una **Tasa Efectiva Trimestral de 33.10%**.

No se ganó 30%, aunque parezca que tres meses a 10% mensual suman 30%. Se ganó 33.10% porque los intereses se capitalizaron mensualmente.

---

## 8. Ejemplo 2: De TNA a TEA y TES

Se deposita S/ 1,000 a una **TNA de 8% capitalizable trimestralmente**.

### Datos

| Dato | Valor |
|---|---:|
| Capital | S/ 1,000 |
| Tasa nominal anual | 8% |
| Capitalización | Trimestral |
| Plazo para TEA | 1 año |
| Plazo para TES | 1 semestre |

Como la capitalización es trimestral:

$$
m = \frac{360}{90} = 4
$$

Para hallar la TEA:

$$
n = \frac{360}{90} = 4
$$

$$
TEA = \left(1 + \frac{8\%}{4}\right)^4 - 1
$$

$$
TEA = (1.02)^4 - 1
$$

$$
TEA = 8.243216\%
$$

Para hallar la TES:

$$
n = \frac{180}{90} = 2
$$

$$
TES = \left(1 + \frac{8\%}{4}\right)^2 - 1
$$

$$
TES = (1.02)^2 - 1
$$

$$
TES = 4.04\%
$$

### Resultado

| Tasa buscada | Resultado |
|---|---:|
| TEA | 8.243216% |
| TES | 4.04% |

---

## 9. Ejemplo 3: De tasa nominal cuatrimestral a tasa efectiva anual

¿Cuál es la **TEA** equivalente a una **TNC de 6% capitalizable mensualmente**?

### Datos

| Dato | Valor |
|---|---:|
| Tasa nominal cuatrimestral | 6% |
| Capitalización | Mensual |
| Tasa buscada | TEA |

La tasa nominal está expresada en un cuatrimestre, es decir, 120 días. La capitalización mensual equivale a 30 días.

$$
m = \frac{120}{30} = 4
$$

La TEA corresponde a 360 días:

$$
n = \frac{360}{30} = 12
$$

Aplicamos:

$$
TEA = \left(1 + \frac{6\%}{4}\right)^{12} - 1
$$

$$
TEA = 19.56181715\%
$$

### Resultado

La tasa efectiva anual equivalente es:

$$
TEA = 19.56181715\%
$$

---

## 10. Ejemplo 4: De TNA a TET

¿Cuál es la **Tasa Efectiva Trimestral** equivalente a una **TNA de 18% capitalizable quincenalmente**?

### Datos

| Dato | Valor |
|---|---:|
| Tasa nominal anual | 18% |
| Capitalización | Quincenal |
| Tasa buscada | TET |

Como la capitalización es quincenal:

$$
m = \frac{360}{15} = 24
$$

Para una tasa efectiva trimestral:

$$
n = \frac{90}{15} = 6
$$

Aplicamos:

$$
TET = \left(1 + \frac{18\%}{24}\right)^6 - 1
$$

$$
TET = 4.58522351\%
$$

### Interpretación

La operación rinde aproximadamente **4.59 por cada 100 invertidos en un trimestre**.

---

## 11. Ejemplo 5: De TEM a TNA con capitalización diaria

¿Cuál es la **TNA con capitalización diaria** equivalente a una **TEM de 4%**?

### Datos

| Dato | Valor |
|---|---:|
| Tasa efectiva mensual | 4% |
| Capitalización buscada | Diaria |
| Tasa nominal buscada | TNA |

Para la tasa nominal anual con capitalización diaria:

$$
m = \frac{360}{1} = 360
$$

Como la TEM corresponde a 30 días:

$$
n = \frac{30}{1} = 30
$$

Aplicamos:

$$
TNA = 360\left[(1 + 4\%)^{1/30} - 1\right]
$$

$$
TNA = 47.09563448\%
$$

### Resultado

La tasa nominal anual equivalente con capitalización diaria es:

$$
TNA = 47.09563448\% \text{ c.d.}
$$

---

## 12. Ejemplo 6: De TEA a TNS con capitalización bimestral

¿Cuál es la **TNS con capitalización bimestral** equivalente a una **TEA de 36%**?

### Datos

| Dato | Valor |
|---|---:|
| Tasa efectiva anual | 36% |
| Tasa nominal buscada | TNS |
| Capitalización | Bimestral |

Para la tasa nominal semestral:

$$
m = \frac{180}{60} = 3
$$

Como la TEA corresponde a 360 días:

$$
n = \frac{360}{60} = 6
$$

Aplicamos:

$$
TNS = 3\left[(1 + 36\%)^{1/6} - 1\right]
$$

$$
TNS = 15.77499683\%
$$

### Resultado

La tasa nominal semestral equivalente es:

$$
TNS = 15.77499683\% \text{ c.b.}
$$

---

## 13. Ejemplo 7: De una tasa efectiva mensual a una tasa efectiva anual

¿Cuál es la **TEA** equivalente a una **TEM de 4%**?

### Datos

| Dato | Valor |
|---|---:|
| Tasa efectiva conocida | TEM = 4% |
| Tasa efectiva buscada | TEA |

Usamos:

$$
TEP_2 = (1 + TEP_1)^{n_2/n_1} - 1
$$

En días:

$$
TEA = (1 + TEM)^{360/30} - 1
$$

$$
TEA = (1 + 4\%)^{12} - 1
$$

$$
TEA = 60.10322186\%
$$

### Resultado

Una **TEM de 4%** equivale a una **TEA de 60.10322186%**.

---

## 14. Ejemplo 8: De TEA a tasa efectiva de 45 días

¿Cuál es la **tasa efectiva a 45 días** equivalente a una **TEA de 35%**?

### Datos

| Dato | Valor |
|---|---:|
| Tasa efectiva anual | 35% |
| Tasa buscada | TE45d |

Aplicamos:

$$
TE45d = (1 + TEA)^{45/360} - 1
$$

$$
TE45d = (1 + 35\%)^{45/360} - 1
$$

$$
TE45d = 3.822557081\%
$$

### Interpretación

Por cada S/ 100 de deuda, en 45 días se pagarían aproximadamente S/ 3.82 adicionales por intereses.

---

## 15. Ejemplo 9: Valor futuro con TEA

¿Cuál es el monto que se obtendrá por un depósito de S/ 5,000 si se mantiene por un semestre en una cuenta que remunera una **TEA de 6%**?

### Datos

| Dato | Valor |
|---|---:|
| Capital | S/ 5,000 |
| Tasa efectiva anual | 6% |
| Plazo | 180 días |

Aplicamos:

$$
S = C(1 + TEP)^{d/D}
$$

$$
S = 5,000(1 + 6\%)^{180/360}
$$

$$
S = 5,147.81507
$$

$$
S \approx 5,147.82
$$

### Tasa efectiva semestral equivalente

$$
TES = (1 + 6\%)^{180/360} - 1
$$

$$
TES = 2.956301409\%
$$

### Resultado

| Concepto | Resultado |
|---|---:|
| Monto final | S/ 5,147.82 |
| TES equivalente | 2.956301409% |

---

## 16. Ejemplo 10: Valor futuro con TEM por 100 días

¿Cuál es el monto que se obtendrá por un depósito de € 20,000 a una **TEM de 0.2%** durante 100 días?

### Datos

| Dato | Valor |
|---|---:|
| Capital | € 20,000 |
| Tasa efectiva mensual | 0.2% |
| Plazo | 100 días |

Aplicamos:

$$
S = 20,000(1 + 0.2\%)^{100/30}
$$

$$
S = 20,133.64
$$

### Resultado

El monto final será:

$$
S = € 20,133.64
$$

---

## 17. Ejemplo 11: Valor presente con TEA

Pedro debe pagar US$ 12,000 dentro de 45 días. La deuda fue contratada a una **TEA de 15%**. ¿Cuánto debe pagar hoy si desea prepagar la deuda?

### Datos

| Dato | Valor |
|---|---:|
| Valor futuro | US$ 12,000 |
| Tasa efectiva anual | 15% |
| Plazo | 45 días |

Aplicamos:

$$
C = \frac{S}{(1 + TEP)^{d/D}}
$$

$$
C = \frac{12,000}{(1 + 15\%)^{45/360}}
$$

$$
C = 11,792.18
$$

### Resultado

Pedro debe cancelar hoy:

$$
C = US\$ 11,792.18
$$

El ahorro por prepago sería:

$$
12,000 - 11,792.18 = US\$ 207.82
$$

---

## 18. Ejemplo 12: Tiempo necesario para alcanzar un monto

¿En cuánto tiempo un capital de US$ 1,350 acumulará al menos US$ 1,475 si está expuesto a una **TES de 4%**?

### Datos

| Dato | Valor |
|---|---:|
| Capital | US$ 1,350 |
| Monto deseado | US$ 1,475 |
| Tasa efectiva semestral | 4% |
| Días de la tasa | 180 días |

Aplicamos:

$$
n = \frac{\ln(S/C)}{\ln(1 + TEP)} \times \text{N.° días TEP}
$$

$$
n = \frac{\ln(1,475/1,350)}{\ln(1 + 4\%)} \times 180
$$

$$
n = 406.4079982 \text{ días}
$$

Como se necesita alcanzar por lo menos el monto indicado, se redondea hacia arriba:

$$
n = 407 \text{ días}
$$

---

## 19. Ejemplo 13: Tasa efectiva anual implícita

Juan presta S/ 5,000 a Pedro durante 180 días. Pedro devuelve el dinero y, como agradecimiento, le regala una parrilla eléctrica valorizada en S/ 450. Si se analiza como operación financiera, ¿cuál es la **TEA implícita**?

### Datos

| Dato | Valor |
|---|---:|
| Capital | S/ 5,000 |
| Interés implícito | S/ 450 |
| Monto equivalente | S/ 5,450 |
| Plazo | 180 días |

Aplicamos:

$$
TEA = \left(\frac{S}{C}\right)^{360/180} - 1
$$

$$
TEA = \left(\frac{5,450}{5,000}\right)^2 - 1
$$

$$
TEA = 18.81\%
$$

### Resultado

La tasa efectiva anual implícita es:

$$
TEA = 18.81\%
$$

---

## 20. Ejemplo 14: Capital necesario para generar un interés

¿Cuál es el capital que se necesita depositar en una cuenta que remunera una **TET de 2%**, si se desea esperar dos meses y generar intereses de al menos US$ 150?

### Datos

| Dato | Valor |
|---|---:|
| Interés deseado | US$ 150 |
| Tasa efectiva trimestral | 2% |
| Plazo | 60 días |
| Días de la tasa | 90 días |

Aplicamos:

$$
C = \frac{I}{(1 + TEP)^{d/D} - 1}
$$

$$
C = \frac{150}{(1 + 2\%)^{60/90} - 1}
$$

$$
C = 11,287.29
$$

### Resultado

Se necesita depositar aproximadamente:

$$
C = US\$ 11,287.29
$$

---

## 21. Método práctico para resolver ejercicios

### Paso 1: Identificar qué tipo de tasa se tiene

| Tasa dada | Qué significa |
|---|---|
| TNA, TNS, TNC, TNB, TNM | Tasa nominal |
| TEA, TES, TET, TEM | Tasa efectiva |
| c.d., c.q., c.m., c.b., c.t. | Frecuencia de capitalización |

### Paso 2: Identificar qué se pide

| Si se pide | Usar |
|---|---|
| TE a partir de TN | $TEP = (1 + TN/m)^n - 1$ |
| TN a partir de TE | $TN = m[(1 + TEP)^{1/n} - 1]$ |
| TE de otro período | $TEP_2 = (1 + TEP_1)^{n_2/n_1} - 1$ |
| Monto final | $S = C(1 + TEP)^{d/D}$ |
| Valor presente | $C = S/(1 + TEP)^{d/D}$ |
| Tiempo | $n = [\ln(S/C)/\ln(1+TEP)]D$ |
| Capital para ganar un interés | $C = I/[(1+TEP)^{d/D}-1]$ |

### Paso 3: Convertir porcentajes a decimales

En las fórmulas, las tasas deben ingresar como decimales.

| Porcentaje | Decimal |
|---|---:|
| 6% | 0.06 |
| 18% | 0.18 |
| 120% | 1.20 |
| 0.2% | 0.002 |

### Paso 4: Interpretar el resultado

Una tasa efectiva siempre debe interpretarse junto con su período.

No es lo mismo:

- 4% mensual
- 4% trimestral
- 4% anual

Cada una produce un crecimiento distinto del capital.

---

## 22. Errores frecuentes

| Error | Corrección |
|---|---|
| Sumar tasas efectivas de distintos períodos | Convertir usando equivalencia de tasas |
| Confundir tasa nominal con tasa efectiva | La tasa nominal requiere conocer la capitalización |
| Usar porcentajes sin convertir a decimales | 18% debe ingresarse como 0.18 |
| Pensar que 10% mensual por 3 meses es 30% efectivo | Con capitalización mensual es 33.10% |
| Comparar tasas en períodos distintos | Llevar ambas tasas al mismo período |
| Redondear demasiado pronto | Mantener decimales hasta el resultado final |

---

## 23. Conclusión general

La tasa efectiva es la tasa que muestra el crecimiento real del capital en un período determinado. Su importancia está en que permite comparar alternativas financieras de manera correcta, porque incorpora el efecto de la capitalización.

La tasa nominal con capitalización y la tasa efectiva pueden ser equivalentes si producen el mismo valor futuro bajo el mismo capital y plazo. Del mismo modo, dos tasas efectivas de períodos distintos también pueden ser equivalentes si generan el mismo resultado financiero.

Por eso, para trabajar correctamente con tasas de interés, siempre se debe identificar:

1. El tipo de tasa dada.
2. El período al que pertenece.
3. La frecuencia de capitalización, si se trata de una tasa nominal.
4. El período de la tasa que se desea obtener.
5. El plazo real de la operación.

La regla central es:

> Dos tasas son equivalentes cuando generan el mismo valor futuro para el mismo capital y el mismo plazo.
