# VAN y TIR: guía práctica con fórmulas y ejemplos

Esta guía resume dos indicadores centrales de rentabilidad usados en evaluación de proyectos: el Valor Actual Neto (VAN) y la Tasa Interna de Retorno (TIR). Ambos trabajan con flujos de caja proyectados, pero responden preguntas distintas.

| Indicador | Pregunta que responde                               |                         Resultado | Criterio principal   |
|-----------|-----------------------------------------------------|----------------------------------:|----------------------|
| VAN       | ¿Cuánto valor monetario crea el proyecto hoy?       | Un monto en S/, US$ u otra moneda | Aceptar si VAN > 0   |
| TIR       | ¿Qué porcentaje de rentabilidad genera el proyecto? |               Una tasa porcentual | Aceptar si TIR > COK |

---

## 1. Conceptos básicos antes de calcular

### 1.1 Flujo de caja

Para evaluar un proyecto se usan los flujos de caja esperados, no la utilidad contable. El flujo de caja representa entradas y salidas reales de dinero durante la vida del proyecto.

La estructura básica es:

```text
Año 0: inversión inicial
Año 1: flujo de caja 1
Año 2: flujo de caja 2
...
Año n: flujo de caja n
```

Por ejemplo:

```text
Año 0: -1,000
Año 1: 400
Año 2: 500
Año 3: 600
```

El año 0 suele ser negativo porque representa la inversión inicial.

### 1.2 Costo de oportunidad del capital (COK)

El COK es la rentabilidad mínima que exige el inversionista para aceptar un proyecto. También puede entenderse como la tasa que se deja de ganar en una alternativa de riesgo similar.

Si el proyecto no supera esa tasa mínima, no crea valor suficiente para justificar la inversión.

---

## 2. Valor Actual Neto (VAN)

### 2.1 ¿Qué es el VAN?

El Valor Actual Neto mide el valor monetario que crea o destruye un proyecto al traer sus flujos de caja futuros al presente usando el COK, y luego restar la inversión inicial.

En palabras simples:

```text
VAN = valor presente de los flujos futuros - inversión inicial
```

El VAN responde a la pregunta:

> ¿Cuánto dinero gano hoy, por encima de lo que exigía como rentabilidad mínima?

---

### 2.2 Fórmula del VAN

Si la inversión inicial ocurre en el momento 0 y los flujos se reciben desde el período 1 hasta el período n:

$$
VAN = -I_0 + \frac{FC_1}{(1+COK)^1} + \frac{FC_2}{(1+COK)^2} + \cdots + \frac{FC_n}{(1+COK)^n}
$$

Donde:

| Símbolo | Significado                                          |
|---------|------------------------------------------------------|
| $I_0$   | Inversión inicial                                    |
| $FC_t$  | Flujo de caja del período t                          |
| $COK$   | Costo de oportunidad del capital o tasa de descuento |
| $n$     | Número de períodos del proyecto                      |

También puede escribirse así:

$$
VAN = \sum_{t=1}^{n} \frac{FC_t}{(1+COK)^t} - I_0
$$

---

### 2.3 Criterios de decisión del VAN

| Resultado | Interpretación                                 | Decisión                         |
|----------:|------------------------------------------------|----------------------------------|
|   VAN > 0 | El proyecto genera valor por encima del COK    | Aceptar                          |
|   VAN = 0 | El proyecto solo cubre la rentabilidad exigida | Indiferente, aceptar o postergar |
|   VAN < 0 | El proyecto no cubre la rentabilidad exigida   | Rechazar                         |

El VAN suele considerarse el criterio más confiable porque mide creación de riqueza en unidades monetarias.

---

### 2.4 Fórmula en Excel o Google Sheets

Si la inversión inicial está en el año 0 y los flujos futuros están desde el año 1:

```excel
=VNA(tasa, flujo1, flujo2, ..., flujon) - inversion_inicial
```

Ejemplo:

```excel
=VNA(10%, 400, 500, 600) - 1000
```

En Excel en inglés:

```excel
=NPV(10%, 400, 500, 600) - 1000
```

Importante: en Excel, la función `VNA` o `NPV` descuenta desde el período 1. Por eso la inversión inicial del período 0 se resta fuera de la función.

---

### 2.5 Ejemplo resuelto de VAN

#### Datos del proyecto

| Año | Flujo de caja |
|----:|--------------:|
|   0 |        -1,000 |
|   1 |           400 |
|   2 |           500 |
|   3 |           600 |

COK = 10%

#### Desarrollo

$$
VAN = -1000 + \frac{400}{(1+0.10)^1} + \frac{500}{(1+0.10)^2} + \frac{600}{(1+0.10)^3}
$$

$$
VAN = -1000 + \frac{400}{1.10} + \frac{500}{1.21} + \frac{600}{1.331}
$$

$$
VAN = -1000 + 363.64 + 413.22 + 450.79
$$

$$
VAN = 227.65
$$

#### Interpretación

El proyecto tiene un VAN positivo de 227.65. Esto significa que, después de recuperar la inversión inicial y cumplir con la rentabilidad mínima exigida del 10%, el proyecto todavía crea 227.65 unidades monetarias de valor.

Decisión: aceptar el proyecto.

---

## 3. Tasa Interna de Retorno (TIR)

### 3.1 ¿Qué es la TIR?

La Tasa Interna de Retorno es la tasa de descuento que hace que el VAN sea igual a cero. Representa la rentabilidad porcentual implícita del proyecto según sus flujos de caja.

En palabras simples:

```text
La TIR es el porcentaje de rentabilidad que genera el proyecto.
```

La TIR responde a la pregunta:

> ¿Qué porcentaje gano con este proyecto?

---

### 3.2 Fórmula de la TIR

La TIR se obtiene resolviendo la siguiente igualdad:

$$
0 = -I_0 + \frac{FC_1}{(1+TIR)^1} + \frac{FC_2}{(1+TIR)^2} + \cdots + \frac{FC_n}{(1+TIR)^n}
$$

También puede expresarse así:

$$
VAN = \sum_{t=1}^{n} \frac{FC_t}{(1+TIR)^t} - I_0 = 0
$$

A diferencia del VAN, la TIR no se calcula directamente con una fórmula simple cuando hay varios períodos. Normalmente se obtiene mediante calculadora financiera, Excel, Google Sheets o métodos iterativos.

---

### 3.3 Criterios de decisión de la TIR

| Resultado | Interpretación                                    | Decisión                         |
|----------:|---------------------------------------------------|----------------------------------|
| TIR > COK | El proyecto supera la rentabilidad mínima exigida | Aceptar                          |
| TIR = COK | El proyecto solo iguala la rentabilidad exigida   | Indiferente, aceptar o postergar |
| TIR < COK | El proyecto no alcanza la rentabilidad exigida    | Rechazar                         |

---

### 3.4 Fórmula en Excel o Google Sheets

Cuando se tienen todos los flujos, incluyendo la inversión inicial en el año 0:

```excel
=TIR(rango_de_flujos)
```

Ejemplo:

```excel
=TIR(A2:A5)
```

Si los flujos se escriben directamente:

```excel
=TIR({-1000;400;500;600})
```

En Excel en inglés:

```excel
=IRR(A2:A5)
```

---

### 3.5 Ejemplo resuelto de TIR

#### Datos del proyecto

| Año | Flujo de caja |
|----:|--------------:|
|   0 |        -1,000 |
|   1 |           400 |
|   2 |           500 |
|   3 |           600 |

#### Planteamiento

La TIR es la tasa que cumple:

$$
0 = -1000 + \frac{400}{(1+TIR)^1} + \frac{500}{(1+TIR)^2} + \frac{600}{(1+TIR)^3}
$$

Usando Excel, Google Sheets o calculadora financiera:

```excel
=TIR({-1000;400;500;600})
```

Resultado:

$$
TIR \approx 21.65\%
$$

#### Interpretación

Si el COK del proyecto es 10%, entonces:

```text
TIR = 21.65% > COK = 10%
```

El proyecto genera una rentabilidad superior a la mínima exigida. Por lo tanto, se acepta.

---

## 4. Ejemplo combinado: VAN y TIR en un mismo proyecto

### 4.1 Proyecto A

| Año | Flujo de caja |
|----:|--------------:|
|   0 |        -5,000 |
|   1 |         1,200 |
|   2 |         1,500 |
|   3 |         1,600 |
|   4 |         1,700 |

COK = 12%

---

### 4.2 Cálculo del VAN

$$
VAN = -5000 + \frac{1200}{1.12} + \frac{1500}{1.12^2} + \frac{1600}{1.12^3} + \frac{1700}{1.12^4}
$$

Valores presentes:

| Año | Flujo | Factor de descuento | Valor presente |
|----:|------:|--------------------:|---------------:|
|   1 | 1,200 |            1 / 1.12 |       1,071.43 |
|   2 | 1,500 |           1 / 1.12² |       1,195.79 |
|   3 | 1,600 |           1 / 1.12³ |       1,138.83 |
|   4 | 1,700 |           1 / 1.12⁴ |       1,080.40 |

Suma de valores presentes:

$$
VA = 4,486.45
$$

Entonces:

$$
VAN = 4,486.45 - 5,000 = -513.55
$$

---

### 4.3 Cálculo de la TIR

La TIR cumple:

$$
0 = -5000 + \frac{1200}{(1+TIR)^1} + \frac{1500}{(1+TIR)^2} + \frac{1600}{(1+TIR)^3} + \frac{1700}{(1+TIR)^4}
$$

En Excel:

```excel
=TIR({-5000;1200;1500;1600;1700})
```

Resultado:

$$
TIR \approx 7.29\%
$$

---

### 4.4 Decisión

| Indicador | Resultado | Criterio         | Decisión |
|-----------|----------:|------------------|----------|
| VAN       |   -513.55 | VAN < 0          | Rechazar |
| TIR       |     7.29% | TIR < COK de 12% | Rechazar |

Conclusión: el proyecto debe rechazarse porque no genera suficiente valor y su rentabilidad porcentual es menor que la tasa mínima exigida.

---

## 5. Diferencia clave entre VAN y TIR

| Criterio             | VAN                                     | TIR                                                                          |
|----------------------|-----------------------------------------|------------------------------------------------------------------------------|
| Mide                 | Valor monetario creado                  | Rentabilidad porcentual                                                      |
| Resultado            | S/, US$ u otra moneda                   | Porcentaje                                                                   |
| Usa COK directamente | Sí                                      | Se compara contra el COK                                                     |
| Principal fortaleza  | Mide creación de riqueza absoluta       | Es fácil de interpretar como porcentaje                                      |
| Principal cuidado    | Depende de una buena estimación del COK | Puede generar problemas con flujos no convencionales o proyectos excluyentes |

---

## 6. Recomendación práctica

Para evaluar un solo proyecto, VAN y TIR suelen llevar a la misma decisión si los flujos son convencionales. Sin embargo, cuando se comparan proyectos excluyentes, el VAN debe tener prioridad porque mide cuánto valor monetario crea cada alternativa.

Regla práctica:

```text
Primero mira el VAN.
Luego usa la TIR como complemento.
```

---

## 7. Nota: VAN y TIR aplicados a un préstamo (perspectiva del deudor)

Esta guía presenta el VAN y la TIR para un **proyecto de inversión**: la inversión inicial es una salida (signo negativo en el período 0) y los flujos siguientes son entradas. Sin embargo, en este sistema el VAN y la TIR se calculan **desde el punto de vista del deudor de un préstamo**, donde los signos se invierten:

- En el período 0 el deudor **recibe** el préstamo → entrada (signo positivo).
- En los períodos siguientes **paga** las cuotas → salidas (signo negativo).

Por eso, para un préstamo, el VAN se escribe así:

$$
VAN = P_0 - \sum_{t=1}^{n} \frac{C_t}{(1+COK)^t}
$$

donde $P_0$ es el préstamo recibido en el período 0 y $C_t$ es la cuota total del período $t$.

El criterio se interpreta al revés que en un proyecto de inversión:

| Resultado | Proyecto de inversión     | Préstamo (deudor)                                             |
|-----------|---------------------------|---------------------------------------------------------------|
| VAN > 0   | Crea valor → aceptar      | El préstamo es **barato** frente al COK → conviene endeudarse |
| VAN < 0   | Destruye valor → rechazar | El préstamo es **caro** frente al COK                         |

La TIR del préstamo, anualizada e incluyendo todos los costos obligatorios (seguros, comisiones, portes), es la **TCEA** (Tasa de Costo Efectivo Anual). El desarrollo detallado, con cronograma y ejemplos, está en [`metodo-frances.md`](metodo-frances.md), [`planes-de-pago.md`](planes-de-pago.md) (§22) y [`metodo-frances-compra-inteligente-balloon.md`](metodo-frances-compra-inteligente-balloon.md).

