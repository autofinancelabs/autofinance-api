# Tasa de interés efectiva

## 1. Idea central

La **tasa de interés efectiva** es la tasa que realmente actúa sobre el capital en una operación financiera durante un periodo específico. A diferencia de una tasa nominal, la tasa efectiva sí refleja la acumulación real de los intereses en el tiempo, porque incorpora el efecto de la capitalización.

En términos financieros, si un capital inicial $C$ genera un interés $I$ durante un periodo, la tasa efectiva de ese periodo se obtiene así:

$$
TEP = \frac{I}{C}
$$

Como el interés también puede expresarse como:

$$
I = S - C
$$

entonces:

$$
TEP = \frac{S-C}{C}
$$

$$
TEP = \frac{S}{C} - 1
$$

Donde:

| Símbolo | Significado                                                                         |
|---------|-------------------------------------------------------------------------------------|
| $C$     | Capital inicial, valor presente o monto invertido/prestado al inicio                |
| $S$     | Valor futuro, monto acumulado o monto a pagar al final                              |
| $I$     | Interés generado                                                                    |
| $TEP$   | Tasa efectiva del periodo                                                           |
| $TEA$   | Tasa efectiva anual                                                                 |
| $TES$   | Tasa efectiva semestral                                                             |
| $TET$   | Tasa efectiva trimestral                                                            |
| $TEM$   | Tasa efectiva mensual                                                               |
| $TN$    | Tasa nominal                                                                        |
| $TNA$   | Tasa nominal anual                                                                  |
| $TNS$   | Tasa nominal semestral                                                              |
| $TNB$   | Tasa nominal bimestral                                                              |
| $TNC$   | Tasa nominal cuatrimestral                                                          |
| $m$     | Número de capitalizaciones dentro del periodo en que está expresada la tasa nominal |
| $n$     | Número de capitalizaciones realizadas durante el periodo que se desea evaluar       |

> Regla importante: en las fórmulas, las tasas deben ingresar en forma decimal. Por ejemplo, 8% se usa como 0.08.

---

## 2. Tasa nominal y tasa efectiva

La **tasa nominal** es una tasa declarada para un periodo, pero no necesariamente representa el rendimiento real de la operación si existe capitalización. Para hallar el rendimiento real, se convierte en una tasa efectiva.

La **tasa efectiva** expresa la acumulación real de intereses en un periodo determinado. Por eso, para comparar alternativas financieras, se deben llevar todas las tasas a un mismo tipo y periodo, por ejemplo a TEA.

Ejemplo conceptual:

| Tasa                                 | Interpretación                                                                                   |
|--------------------------------------|--------------------------------------------------------------------------------------------------|
| TNA 8% capitalizable trimestralmente | La tasa nominal anual se divide en 4 capitalizaciones trimestrales. Cada trimestre se aplica 2%. |
| TEA 8.243216%                        | Es el rendimiento real anual resultante de capitalizar 2% cada trimestre durante un año.         |

---

### 2.1. Relación entre interés simple, interés compuesto y tasa efectiva

Antes de trabajar conversiones, conviene distinguir tres ideas que suelen confundirse:

| Concepto                            | Idea central                                           | ¿Qué pasa con los intereses?                                     | Fórmula típica                    | Uso principal                                              |
|-------------------------------------|--------------------------------------------------------|------------------------------------------------------------------|-----------------------------------|------------------------------------------------------------|
| **Interés simple**                  | Se decide no capitalizar los intereses.                | Los intereses se retiran o no se reinvierten.                    | $S=C(1+i\cdot n)$                 | Operaciones donde el interés no se suma al capital.        |
| **Interés compuesto**               | Se capitalizan los intereses.                          | Los intereses se reinvierten y pasan a formar parte del capital. | $S=C(1+i)^n$                      | Operaciones con reinversión periódica de intereses.        |
| **Tasa nominal con capitalización** | Es una forma de construir interés compuesto.           | La tasa nominal se divide entre el número de capitalizaciones.   | $S=C\left(1+\frac{j}{m}\right)^n$ | Convertir una tasa declarada en acumulación real.          |
| **Tasa efectiva**                   | Mide cuánto creció realmente el capital en un periodo. | Ya incluye el efecto de las capitalizaciones ocurridas.          | $TE=\frac{S}{C}-1$                | Comparar tasas, hallar rendimiento real y convertir tasas. |

La tasa efectiva responde esta pregunta: **¿en cuánto porcentaje creció realmente mi capital durante el periodo analizado?** Por eso, si ya se conoce el capital inicial $C$ y el valor futuro $S$, la tasa efectiva del periodo se obtiene directamente con:

$$
TE=\frac{S}{C}-1
$$

Esto significa que la tasa efectiva no es simplemente la tasa nominal dividida entre el tiempo. La tasa efectiva incorpora la capitalización. Por eso, cuando los intereses se reinvierten, el crecimiento porcentual final puede ser mayor que la suma aritmética de las tasas periódicas.

#### Tasas equivalentes

Dos tasas son **equivalentes** cuando, aplicadas al mismo capital inicial y durante el mismo plazo, producen el mismo valor futuro $S$.

Pueden ser equivalentes:

- Una **tasa nominal con capitalización** y una **tasa efectiva**.
- Dos **tasas efectivas de periodos distintos**, como una TEM y una TEA.
- Dos **tasas nominales con distintas capitalizaciones**, siempre que generen el mismo monto final para el plazo considerado.

La equivalencia no significa que las tasas tengan el mismo número porcentual, sino que producen el mismo resultado financiero.

#### Ejemplo integrador: TNA 120% capitalizable mensualmente durante 3 meses

**Datos del problema**

| Dato                                     |               Valor |
|------------------------------------------|--------------------:|
| Capital inicial $C$                      |         S/ 1,000.00 |
| Tasa nominal anual $j$                   |         120% = 1.20 |
| Capitalización                           |             Mensual |
| Número de capitalizaciones anuales $m$   |                  12 |
| Tasa periódica mensual $j/m$             | $1.20/12=0.10=10\%$ |
| Plazo                                    |             3 meses |
| Número de capitalizaciones del plazo $n$ |                   3 |

La tasa nominal anual de 120% capitalizable mensualmente no significa que en 3 meses se gane simplemente 30% sobre el capital inicial. Como hay capitalización mensual, cada mes los intereses se suman al capital y generan nuevos intereses.

**Evolución mes a mes**

| Mes | Capital al inicio del mes | Interés mensual 10% | Capital al final del mes |
|----:|--------------------------:|--------------------:|-------------------------:|
|   0 |               S/ 1,000.00 |                   — |              S/ 1,000.00 |
|   1 |               S/ 1,000.00 |           S/ 100.00 |              S/ 1,100.00 |
|   2 |               S/ 1,100.00 |           S/ 110.00 |              S/ 1,210.00 |
|   3 |               S/ 1,210.00 |           S/ 121.00 |              S/ 1,331.00 |

**Valor futuro usando tasa nominal con capitalización**

$$
S=C\left(1+\frac{j}{m}\right)^n
$$

$$
S=1{,}000\left(1+\frac{1.20}{12}\right)^3
$$

$$
S=1{,}000(1.10)^3
$$

$$
S=1{,}331.00
$$

**Interés generado en términos monetarios**

$$
I=S-C
$$

$$
I=1{,}331-1{,}000
$$

$$
I=331
$$

**Crecimiento del capital en términos porcentuales**

$$
TET=\frac{S}{C}-1
$$

$$
TET=\frac{1{,}331}{1{,}000}-1
$$

$$
TET=0.331=33.10\%
$$

**Conclusión del ejemplo**

Una inversión de S/ 1,000 colocada a una **TNA de 120% capitalizable mensualmente** durante 3 meses acumula un valor futuro de **S/ 1,331.00**. El crecimiento monetario es **S/ 331.00** y el crecimiento efectivo trimestral es **33.10%**.

Por tanto, para ese plazo de 3 meses, la **TNA 120% capitalizable mensualmente** y la **TET 33.10%** son tasas equivalentes, porque ambas generan el mismo valor futuro de S/ 1,331.00 sobre el mismo capital inicial. La tasa efectiva trimestral no es 30%, aunque existan tres meses con 10%, porque el interés del segundo mes se calcula sobre S/ 1,100 y el del tercer mes sobre S/ 1,210.

---

## 3. Convención de tiempo usada

El material trabaja con año financiero de 360 días. Por ello:

| Periodo       | Días usados |
|---------------|------------:|
| Diario        |           1 |
| Quincenal     |          15 |
| Mensual       |          30 |
| Bimestral     |          60 |
| Trimestral    |          90 |
| Cuatrimestral |         120 |
| Semestral     |         180 |
| Anual         |         360 |

Si un problema usa otra convención, como año calendario de 365 días, se debe adaptar la cantidad de días.

---

## 4. Cómo convertir tasas nominales en tasas efectivas

Cuando se tiene una tasa nominal $TN$ con una frecuencia de capitalización, la tasa efectiva del periodo deseado se calcula con:

$$
TEP = \left(1 + \frac{TN}{m}\right)^n - 1
$$

Donde:

| Variable | Cómo se interpreta                                                          |
|----------|-----------------------------------------------------------------------------|
| $TN$     | Tasa nominal expresada en decimal                                           |
| $m$      | Número de capitalizaciones dentro del periodo de la tasa nominal            |
| $n$      | Número de capitalizaciones dentro del periodo efectivo que se quiere hallar |

### Ejemplo rápido

Convertir una **TNA 8% capitalizable trimestralmente** a **TEA**.

Datos:

$$
TN = 0.08
$$

$$
m = 4
$$

$$
n = 4
$$

Aplicación:

$$
TEA = \left(1 + \frac{0.08}{4}\right)^4 - 1
$$

$$
TEA = (1.02)^4 - 1
$$

$$
TEA = 0.08243216
$$

$$
TEA = 8.243216\%
$$

---

## 5. Cómo convertir tasas efectivas en tasas nominales

Cuando se tiene una tasa efectiva y se desea expresarla como tasa nominal con una determinada capitalización, se usa:

$$
TN = m \times \left[(1+TEP)^{1/n} - 1\right]
$$

Donde:

| Variable | Cómo se interpreta                                                         |
|----------|----------------------------------------------------------------------------|
| $TEP$    | Tasa efectiva conocida                                                     |
| $m$      | Número de capitalizaciones dentro del periodo nominal buscado              |
| $n$      | Número de capitalizaciones dentro del periodo de la tasa efectiva conocida |

### Ejemplo rápido

Convertir una **TEM 4%** en una **TNA capitalizable diariamente**.

Datos:

$$
TEP = TEM = 0.04
$$

$$
m = 360
$$

$$
n = 30
$$

Aplicación:

$$
TNA = 360 \times \left[(1+0.04)^{1/30} - 1\right]
$$

$$
TNA = 0.4709563448
$$

$$
TNA = 47.09563448\%
$$

---

## 6. Cómo convertir una tasa nominal en otra tasa nominal

Para convertir una tasa nominal en otra tasa nominal, se usa una tasa efectiva como puente.

Procedimiento:

1. Convertir la tasa nominal original a una tasa efectiva conveniente.
2. Convertir esa tasa efectiva en la tasa nominal solicitada.

### Fórmula de apoyo

Primero:

$$
TEP = \left(1+\frac{TN_1}{m_1}\right)^{n_1} - 1
$$

Luego:

$$
TN_2 = m_2 \times \left[(1+TEP)^{1/n_2} - 1\right]
$$

---

## 7. Cómo convertir una tasa efectiva en otra tasa efectiva

Cuando se tiene una tasa efectiva de un periodo y se desea expresar en otro periodo, se usa:

$$
TEP_2 = (1+TEP_1)^{\frac{n_2}{n_1}} - 1
$$

Donde:

| Variable | Significado                                       |
|----------|---------------------------------------------------|
| $TEP_1$  | Tasa efectiva conocida                            |
| $TEP_2$  | Tasa efectiva buscada                             |
| $n_1$    | Duración del periodo de la tasa efectiva conocida |
| $n_2$    | Duración del periodo de la tasa efectiva buscada  |

La unidad de $n_1$ y $n_2$ debe ser la misma. Puede usarse días, meses, quincenas u otra unidad, siempre que ambas tasas se midan con la misma base.

### Ejemplo rápido

Convertir una **TEM 4%** en **TEA**.

$$
TEA = (1+TEM)^{12/1} - 1
$$

$$
TEA = (1+0.04)^{12} - 1
$$

$$
TEA = 60.10322186\%
$$

---

## 8. Fórmulas de interés efectivo

Cuando se trabaja directamente con tasas efectivas, se pueden usar las siguientes fórmulas.

### 8.1 Valor futuro

$$
S = C \times (1+TEP)^{\frac{D_t}{D_{TEP}}}
$$

Donde:

| Símbolo   | Significado                                    |
|-----------|------------------------------------------------|
| $S$       | Valor futuro                                   |
| $C$       | Capital inicial                                |
| $TEP$     | Tasa efectiva conocida                         |
| $D_t$     | Número de días que se traslada el dinero       |
| $D_{TEP}$ | Número de días del periodo de la tasa efectiva |

### 8.2 Valor presente

$$
C = \frac{S}{(1+TEP)^{\frac{D_t}{D_{TEP}}}}
$$

### 8.3 Tiempo transcurrido

$$
t = \frac{\ln\left(\frac{S}{C}\right) \times D_{TEP}}{\ln(1+TEP)}
$$

### 8.4 Tasa efectiva

$$
TEP = \left(\frac{S}{C}\right)^{\frac{D_{TEP}}{D_t}} - 1
$$

### 8.5 Capital necesario para generar un interés determinado

Si se conoce el interés que se desea generar, se puede despejar el capital:

$$
C = \frac{I}{(1+TEP)^{\frac{D_t}{D_{TEP}}} - 1}
$$

---

## 9. Metodología práctica para resolver problemas

Para trabajar correctamente con tasas efectivas:

| Paso | Acción                                       | Recomendación                                                                                             |
|-----:|----------------------------------------------|-----------------------------------------------------------------------------------------------------------|
|    1 | Identificar si la tasa es nominal o efectiva | Si dice “capitalizable”, normalmente es nominal.                                                          |
|    2 | Identificar el periodo de la tasa            | Anual, semestral, mensual, trimestral, etc.                                                               |
|    3 | Identificar la capitalización                | Diaria, mensual, trimestral, etc.                                                                         |
|    4 | Convertir la tasa a decimal                  | 8% se usa como 0.08.                                                                                      |
|    5 | Elegir la fórmula correcta                   | Nominal a efectiva, efectiva a nominal, efectiva a efectiva, valor futuro, valor presente, tiempo o tasa. |
|    6 | Homogeneizar unidades de tiempo              | No mezclar meses con días sin convertirlos.                                                               |
|    7 | Redondear solo al final                      | Evita diferencias por redondeo prematuro.                                                                 |
|    8 | Interpretar el resultado                     | Una TEA de 8.24% significa que por cada 100 invertidos se gana 8.24 en un año.                            |

---

## 10. Ejemplos desarrollados

### Ejemplo 1: TNA capitalizable trimestralmente a TEA y TES

Se deposita S/. 1,000 a una **TNA 8% capitalizable trimestralmente**.

#### Parte a: interés generado en un año y TEA equivalente

Datos:

$$
C = 1000
$$

$$
TNA = 8\% = 0.08
$$

$$
m = 4
$$

$$
n = 4
$$

Tasa por trimestre:

$$
i' = \frac{0.08}{4} = 0.02
$$

Valor futuro:

$$
S = 1000(1+0.02)^4
$$

$$
S = 1082.43216
$$

Interés:

$$
I = S - C
$$

$$
I = 1082.43216 - 1000
$$

$$
I = 82.43216
$$

TEA:

$$
TEA = \left(1+\frac{0.08}{4}\right)^4 - 1
$$

$$
TEA = 8.243216\%
$$

Respuesta:

| Concepto         |    Resultado |
|------------------|-------------:|
| Valor futuro     | S/. 1,082.43 |
| Interés generado |    S/. 82.43 |
| TEA equivalente  |    8.243216% |

#### Parte b: TES equivalente

Datos:

$$
m = 4
$$

$$
n = 2
$$

$$
TES = \left(1+\frac{0.08}{4}\right)^2 - 1
$$

$$
TES = 4.04\%
$$

---

### Ejemplo 2: TNC capitalizable mensualmente a TEA

¿Cuál es la **TEA** equivalente a una **TNC 6% capitalizable mensualmente**?

Datos:

$$
TNC = 6\% = 0.06
$$

La tasa nominal está expresada en un periodo cuatrimestral de 120 días y capitaliza mensualmente cada 30 días:

$$
m = \frac{120}{30} = 4
$$

Para hallar la TEA, se evalúa un año de 360 días:

$$
n = \frac{360}{30} = 12
$$

Aplicación:

$$
TEA = \left(1+\frac{0.06}{4}\right)^{12} - 1
$$

$$
TEA = 19.56181715\%
$$

Respuesta:

$$
TEA = 19.56181715\%
$$

---

### Ejemplo 3: TNA capitalizable quincenalmente a TET

¿Cuál es la **TET** equivalente a una **TNA 18% capitalizable quincenalmente**?

Datos:

$$
TNA = 18\% = 0.18
$$

Como capitaliza cada 15 días:

$$
m = \frac{360}{15} = 24
$$

Para un trimestre de 90 días:

$$
n = \frac{90}{15} = 6
$$

Aplicación:

$$
TET = \left(1+\frac{0.18}{24}\right)^6 - 1
$$

$$
TET = 4.58522351\%
$$

Interpretación:

Por cada 100 invertidos, la operación rinde aproximadamente 4.59 durante un trimestre.

---

### Ejemplo 4: TEM a TNA con capitalización diaria y mensual

¿Cuál es la **TNA capitalizable diariamente** equivalente a una **TEM 4%**?

Datos:

$$
TEM = 4\% = 0.04
$$

Para TNA con capitalización diaria:

$$
m = \frac{360}{1} = 360
$$

$$
n = \frac{30}{1} = 30
$$

Aplicación:

$$
TNA = 360\left[(1+0.04)^{1/30}-1\right]
$$

$$
TNA = 47.09563448\%
$$

Si la capitalización fuera mensual:

$$
m = 12
$$

$$
n = 1
$$

$$
TNA = 12\left[(1+0.04)^{1/1}-1\right]
$$

$$
TNA = 48\%
$$

Resultado comparativo:

| Tasa buscada                   |    Resultado |
|--------------------------------|-------------:|
| TNA capitalizable diariamente  | 47.09563448% |
| TNA capitalizable mensualmente | 48.00000000% |

---

### Ejemplo 5: TEA a TNS capitalizable bimestralmente

¿Cuál es la **TNS capitalizable bimestralmente** equivalente a una **TEA 36%**?

Datos:

$$
TEA = 36\% = 0.36
$$

Para TNS capitalizable bimestralmente:

$$
m = \frac{180}{60} = 3
$$

Como la TEA cubre 360 días:

$$
n = \frac{360}{60} = 6
$$

Aplicación:

$$
TNS = 3\left[(1+0.36)^{1/6}-1\right]
$$

$$
TNS = 15.77499683\%
$$

Si la TNS fuera capitalizable diariamente:

$$
m = 180
$$

$$
n = 360
$$

$$
TNS = 180\left[(1+0.36)^{1/360}-1\right]
$$

$$
TNS = 15.38080261\%
$$

Resultado comparativo:

| Tasa buscada                     |    Resultado |
|----------------------------------|-------------:|
| TNS capitalizable bimestralmente | 15.77499683% |
| TNS capitalizable diariamente    | 15.38080261% |

---

### Ejemplo 6: TNA capitalizable diariamente a TNB capitalizable mensualmente

¿Cuál es la **TNB capitalizable mensualmente** equivalente a una **TNA 18% capitalizable diariamente**?

Este tipo de problema se resuelve usando una tasa efectiva como puente.

#### Paso 1: convertir TNA 18% c.d. a TEA

Datos:

$$
TNA = 18\% = 0.18
$$

$$
m = 360
$$

$$
n = 360
$$

Aplicación:

$$
TEA = \left(1+\frac{0.18}{360}\right)^{360} - 1
$$

$$
TEA = 19.71635075\%
$$

#### Paso 2: convertir TEA a TNB capitalizable mensualmente

Para TNB capitalizable mensualmente:

$$
m = \frac{60}{30} = 2
$$

Como la TEA cubre 360 días:

$$
n = \frac{360}{30} = 12
$$

Aplicación:

$$
TNB = 2\left[(1+0.1971635075)^{1/12}-1\right]
$$

$$
TNB = 3.02185184\%
$$

Respuesta:

$$
TNB = 3.02185184\% \text{ capitalizable mensualmente}
$$

---

### Ejemplo 7: TEM a TEA

¿Cuál es la **TEA** equivalente a una **TEM 4%**?

Datos:

$$
TEM = 4\% = 0.04
$$

Aplicación:

$$
TEA = (1+0.04)^{360/30} - 1
$$

$$
TEA = (1+0.04)^{12} - 1
$$

$$
TEA = 60.10322186\%
$$

Respuesta:

$$
TEA = 60.10322186\%
$$

---

### Ejemplo 8: TEA a tasa efectiva de 45 días

¿Cuál es la tasa efectiva de 45 días equivalente a una **TEA 35%**?

Datos:

$$
TEA = 35\% = 0.35
$$

$$
D_t = 45
$$

$$
D_{TEA} = 360
$$

Aplicación:

$$
TE_{45d} = (1+0.35)^{45/360} - 1
$$

$$
TE_{45d} = 3.82255708\%
$$

Interpretación:

Por cada 100 de deuda, en 45 días se pagan aproximadamente 3.82 adicionales.

---

### Ejemplo 9: valor futuro con TEA durante un semestre

¿Cuál es el monto que se obtendrá por un depósito de S/. 5,000 si se mantiene en una cuenta que remunera una **TEA 6%** durante un semestre?

Datos:

$$
C = 5000
$$

$$
TEA = 6\% = 0.06
$$

$$
D_t = 180
$$

$$
D_{TEA} = 360
$$

Aplicación:

$$
S = 5000(1+0.06)^{180/360}
$$

$$
S = 5147.81507
$$

$$
S = 5147.82
$$

Cálculo de la TES:

$$
TES = (1+0.06)^{180/360} - 1
$$

$$
TES = 2.95630141\%
$$

Respuesta:

| Concepto        |    Resultado |
|-----------------|-------------:|
| Monto final     | S/. 5,147.82 |
| TES equivalente |  2.95630141% |

---

### Ejemplo 10: valor futuro con TEM durante 100 días

¿Cuál es el monto que se obtendrá por un depósito de € 20,000 a una **TEM 0.2%** durante 100 días?

Datos:

$$
C = 20000
$$

$$
TEM = 0.2\% = 0.002
$$

$$
D_t = 100
$$

$$
D_{TEM} = 30
$$

Aplicación:

$$
S = 20000(1+0.002)^{100/30}
$$

$$
S = 20133.64
$$

Respuesta:

$$
S = €20,133.64
$$

---

### Ejemplo 11: comparación de alternativas de depósito

Pedro desea invertir US\$ 100,000 durante dos años. La oferta pasiva relevante es:

| Año | Banco de Fomento | Banco de América |
|----:|------------------|------------------|
|   1 | TEA 5.7%         | TNA 5.5% c.d.    |
|   2 | TNS 3% c.m.      | TNA 6% c.q.      |

#### Año 1

Banco de Fomento:

$$
S = 100000(1+0.057)
$$

$$
S = 105700
$$

Banco de América:

$$
S = 100000\left(1+\frac{0.055}{360}\right)^{360}
$$

$$
S = 105653.62
$$

Decisión del año 1:

| Banco            |    Valor futuro |
|------------------|----------------:|
| Banco de Fomento | US\$ 105,700.00 |
| Banco de América | US\$ 105,653.62 |

Conviene elegir el **Banco de Fomento**.

#### Año 2

Se comparan ambas alternativas como TEA.

Banco de Fomento:

$$
TEA = \left(1+\frac{0.03}{6}\right)^{12} - 1
$$

$$
TEA = 6.16778119\%
$$

Banco de América:

$$
TEA = \left(1+\frac{0.06}{24}\right)^{24} - 1
$$

$$
TEA = 6.17570443\%
$$

Decisión del año 2:

| Banco            | TEA equivalente |
|------------------|----------------:|
| Banco de Fomento |     6.16778119% |
| Banco de América |     6.17570443% |

Conviene elegir el **Banco de América**.

Valor final:

$$
S = 105700\left(1+\frac{0.06}{24}\right)^{24}
$$

$$
S = 112227.72
$$

TEA promedio de la operación completa:

$$
TEA = \left(\frac{112227.72}{100000}\right)^{360/720} - 1
$$

$$
TEA = 5.9375852\%
$$

Respuesta:

| Concepto                        |       Resultado |
|---------------------------------|----------------:|
| Monto final después de dos años | US\$ 112,227.72 |
| TEA de la operación             |      5.9375852% |

---

### Ejemplo 12: valor presente para prepagar una deuda

¿Cuál es el monto que Pedro debe cancelar hoy por una deuda de US\$ 12,000 que vence dentro de 45 días, si fue contratada a una **TEA 15%**?

Datos:

$$
S = 12000
$$

$$
TEA = 15\% = 0.15
$$

$$
D_t = 45
$$

$$
D_{TEA} = 360
$$

Aplicación:

$$
C = \frac{12000}{(1+0.15)^{45/360}}
$$

$$
C = 11792.18
$$

Ahorro por prepago:

$$
Ahorro = 12000 - 11792.18
$$

$$
Ahorro = 207.82
$$

Respuesta:

| Concepto             |      Resultado |
|----------------------|---------------:|
| Monto a cancelar hoy | US\$ 11,792.18 |
| Ahorro financiero    |    US\$ 207.82 |

---

### Ejemplo 13: tiempo necesario para alcanzar un valor futuro

¿En cuánto tiempo un capital de US\$ 1,350 acumulará por lo menos US\$ 1,475 si está expuesto a una **TES 4%**?

Datos:

$$
C = 1350
$$

$$
S = 1475
$$

$$
TES = 4\% = 0.04
$$

$$
D_{TES} = 180
$$

Aplicación:

$$
t = \frac{\ln(1475/1350)\times 180}{\ln(1+0.04)}
$$

$$
t = 406.4079982 \text{ días}
$$

Como se necesita alcanzar por lo menos el valor futuro solicitado, se redondea hacia arriba:

$$
t = 407 \text{ días}
$$

Respuesta:

$$
407 \text{ días}
$$

---

### Ejemplo 14: tasa efectiva implícita

Juan presta S/. 5,000 a Pedro. Luego de 180 días, Pedro devuelve el dinero y le regala una parrilla eléctrica valorizada en S/. 450. Si se analiza como operación financiera, ¿cuál es la **TEA implícita**?

Datos:

$$
C = 5000
$$

$$
I = 450
$$

$$
S = 5450
$$

$$
D_t = 180
$$

$$
D_{TEA} = 360
$$

Aplicación:

$$
TEA = \left(\frac{5450}{5000}\right)^{360/180} - 1
$$

$$
TEA = 18.81\%
$$

Respuesta:

$$
TEA = 18.81\%
$$

---

### Ejemplo 15: capital necesario para generar un interés objetivo

¿Cuál es el capital necesario para generar intereses de por lo menos US\$ 150 en dos meses, si la cuenta remunera una **TET 2%**?

Datos:

$$
I = 150
$$

$$
TET = 2\% = 0.02
$$

$$
D_t = 60
$$

$$
D_{TET} = 90
$$

Fórmula:

$$
C = \frac{I}{(1+TEP)^{D_t/D_{TEP}} - 1}
$$

Aplicación:

$$
C = \frac{150}{(1+0.02)^{60/90}-1}
$$

$$
C = 11287.29
$$

Respuesta:

$$
C = US\$ 11,287.29
$$

---

## 11. Ejercicios propuestos del material, resueltos por aplicación de fórmula

### Ejercicio 1

Calcular la **TEA** para depósitos que ofrecen una **TNA 6%**, considerando distintas capitalizaciones.

Fórmula:

$$
TEA = \left(1+\frac{0.06}{m}\right)^m - 1
$$

| Capitalización | $m$ |         TEA |
|----------------|----:|------------:|
| Diaria         | 360 | 6.18312380% |
| Mensual        |  12 | 6.16778119% |
| Bimestral      |   6 | 6.15201506% |
| Trimestral     |   4 | 6.13635506% |
| Semestral      |   2 | 6.09000000% |
| Anual          |   1 | 6.00000000% |

Observación: para una misma TNA, mientras más frecuente sea la capitalización, mayor será la TEA.

---

### Ejercicio 2

Calcular la **TNA** equivalente a una **TEA 27%**, considerando distintas capitalizaciones.

Fórmula:

$$
TNA = m\left[(1+0.27)^{1/m}-1\right]
$$

| Capitalización | $m$ | TNA equivalente |
|----------------|----:|----------------:|
| Diaria         | 360 |    23.90962640% |
| Mensual        |  12 |    24.14131619% |
| Bimestral      |   6 |    24.38415084% |
| Trimestral     |   4 |    24.63024234% |
| Semestral      |   2 |    25.38855339% |
| Anual          |   1 |    27.00000000% |

Observación: para una misma TEA, mientras más frecuente sea la capitalización nominal solicitada, menor será la TNA equivalente.

---

## 12. Errores frecuentes

| Error                                        | Por qué está mal                                           | Corrección                                            |
|----------------------------------------------|------------------------------------------------------------|-------------------------------------------------------|
| Comparar TNA con TEA directamente            | No están en el mismo tipo de tasa                          | Convertir ambas a una misma tasa efectiva             |
| Dividir una TEA entre 12 para hallar una TEM | La capitalización compuesta no funciona linealmente        | Usar $TEM=(1+TEA)^{30/360}-1$                         |
| Redondear en pasos intermedios               | Puede alterar el resultado final                           | Mantener decimales y redondear al final               |
| Mezclar días, meses y años sin convertir     | La fórmula exige unidades consistentes                     | Usar la misma unidad de tiempo                        |
| Confundir $m$ con $n$                        | $m$ depende del periodo nominal y $n$ del periodo evaluado | Dibujar una línea de tiempo ayuda a identificar ambos |
| Usar porcentaje en lugar de decimal          | 8 en vez de 0.08 cambia totalmente el cálculo              | Dividir la tasa entre 100 antes de operar             |

---

## 13. Resumen de fórmulas esenciales

| Caso                             | Fórmula                                        |
|----------------------------------|------------------------------------------------|
| TEP desde interés                | $TEP = \frac{I}{C}$                            |
| TEP desde valor futuro y capital | $TEP = \frac{S}{C}-1$                          |
| Nominal a efectiva               | $TEP = \left(1+\frac{TN}{m}\right)^n - 1$      |
| Efectiva a nominal               | $TN = m[(1+TEP)^{1/n}-1]$                      |
| Efectiva a efectiva              | $TEP_2=(1+TEP_1)^{n_2/n_1}-1$                  |
| Valor futuro                     | $S=C(1+TEP)^{D_t/D_{TEP}}$                     |
| Valor presente                   | $C=\frac{S}{(1+TEP)^{D_t/D_{TEP}}}$            |
| Tiempo                           | $t=\frac{\ln(S/C)D_{TEP}}{\ln(1+TEP)}$         |
| Tasa efectiva implícita          | $TEP=\left(\frac{S}{C}\right)^{D_{TEP}/D_t}-1$ |
| Capital para interés objetivo    | $C=\frac{I}{(1+TEP)^{D_t/D_{TEP}}-1}$          |

---

## 14. Recomendación para humanos o IA

Para resolver cualquier problema de tasa efectiva, conviene seguir este patrón:

1. Extraer datos: capital, monto, interés, tasa, periodo, capitalización y tiempo.
2. Determinar si la tasa es nominal o efectiva.
3. Convertir todo a una misma base temporal.
4. Elegir la fórmula según la incógnita.
5. Sustituir tasas como decimales.
6. Resolver sin redondeos intermedios.
7. Interpretar el resultado en unidades monetarias o porcentuales.

La idea clave es que una tasa efectiva siempre representa una acumulación real en un periodo concreto. Por eso, cuando se comparan alternativas financieras, se debe convertir todo a una tasa efectiva comparable, normalmente TEA.
