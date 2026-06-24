# Tasa simple: explicación, fórmulas y ejemplos resueltos

## 1. ¿Qué es la tasa simple?

La **tasa simple** es una forma de calcular intereses en la que el interés se obtiene siempre sobre el **capital inicial**. Es decir, los intereses generados no se suman al capital para producir nuevos intereses.

En una operación de tasa simple, el capital original permanece constante durante todo el plazo. Por eso, el interés crece de manera **lineal** con el tiempo: si el plazo se duplica, el interés también se duplica, siempre que la tasa y el capital se mantengan iguales.

Se usa para analizar préstamos, inversiones, descuentos, pagos anticipados, deudas a corto plazo y operaciones donde no existe capitalización de intereses.

---

## 2. Conceptos clave

| Símbolo | Nombre | Significado |
|---|---|---|
| `C` | Capital, valor actual o valor presente | Dinero inicial invertido, prestado o recibido. |
| `I` | Interés | Ganancia o costo generado por el uso del dinero. |
| `S` | Stock, monto o valor futuro | Dinero total acumulado al final de la operación. |
| `i` | Tasa de interés simple | Porcentaje aplicado al capital. Debe expresarse en forma decimal dentro de las fórmulas. |
| `n` | Plazo o tiempo | Duración de la operación. Debe estar en la misma unidad temporal que la tasa. |

Ejemplo de conversión de tasa:

```text
12% = 12 / 100 = 0.12
6% = 6 / 100 = 0.06
1.5% = 1.5 / 100 = 0.015
```

---

## 3. Regla fundamental de la tasa simple

La regla más importante es:

> Las variables `i` y `n` deben estar expresadas en la misma equivalencia de tiempo.

Esto significa que:

| Si la tasa está expresada como... | Entonces el tiempo debe expresarse en... |
|---|---|
| Tasa simple anual | Años |
| Tasa simple mensual | Meses |
| Tasa simple trimestral | Trimestres |
| Tasa simple diaria | Días |

Ejemplo:

Si se tiene una **tasa simple anual de 18%** y el plazo es de **9 meses**, no se debe usar directamente `n = 9`, porque la tasa está en años y el plazo en meses. Primero se convierte el plazo a años:

```text
n = 9 meses / 12 meses = 0.75 años
```

También puede expresarse con año comercial:

```text
n = 9 × 30 / 360 = 270 / 360 = 0.75 años
```

---

## 4. Fórmula del interés simple

La fórmula principal para calcular los intereses ganados o pagados es:

$$
I = C \times i \times n
$$

Donde:

```text
I = intereses
C = capital, inversión, préstamo o valor actual
i = tasa de interés simple
n = plazo o tiempo
```

### Interpretación

El interés depende directamente de tres factores:

1. **Capital (`C`)**: a mayor capital, mayor interés.
2. **Tasa (`i`)**: a mayor tasa, mayor interés.
3. **Tiempo (`n`)**: a mayor plazo, mayor interés.

---

## 5. Fórmula del valor futuro

El valor futuro representa el monto total que se tendrá o deberá pagar al final de la operación.

Parte de esta relación básica:

$$
S = C + I
$$

Como:

$$
I = C \times i \times n
$$

Entonces:

$$
S = C + C \times i \times n
$$

Factorizando el capital:

$$
S = C(1 + i \times n)
$$

Por tanto, la fórmula del valor futuro a tasa simple es:

$$
S = C \times (1 + i \times n)
$$

---

## 6. Fórmula del valor presente

Si se conoce el valor futuro y se desea encontrar el capital inicial, se despeja `C` de la fórmula anterior:

$$
S = C(1 + i \times n)
$$

$$
C = \frac{S}{1 + i \times n}
$$

También puede escribirse como:

$$
C = S(1 + i \times n)^{-1}
$$

Esta fórmula se usa cuando se desea traer un monto futuro al presente, por ejemplo, para calcular cuánto se debe pagar hoy por una deuda que vence más adelante.

---

## 7. Fórmula para hallar la tasa de interés simple

Cuando se conoce el capital, el valor futuro y el tiempo, se puede hallar la tasa:

$$
S = C(1 + i \times n)
$$

Dividiendo entre `C`:

$$
\frac{S}{C} = 1 + i \times n
$$

Restando 1:

$$
\frac{S}{C} - 1 = i \times n
$$

Despejando `i`:

$$
i = \frac{\frac{S}{C} - 1}{n}
$$

También, si se conoce directamente el interés:

$$
i = \frac{I}{C \times n}
$$

---

## 8. Fórmula para hallar el tiempo

Cuando se conoce el capital, el valor futuro y la tasa, se puede hallar el plazo:

$$
S = C(1 + i \times n)
$$

$$
\frac{S}{C} = 1 + i \times n
$$

$$
\frac{S}{C} - 1 = i \times n
$$

Despejando `n`:

$$
n = \frac{\frac{S}{C} - 1}{i}
$$

También, si se conoce directamente el interés:

$$
n = \frac{I}{C \times i}
$$

---

## 9. Resumen general de fórmulas

| Variable buscada | Fórmula |
|---|---|
| Interés | $I = C \times i \times n$ |
| Valor futuro | $S = C(1 + i \times n)$ |
| Valor presente | $C = \frac{S}{1 + i \times n}$ |
| Tasa de interés | $i = \frac{\frac{S}{C} - 1}{n}$ |
| Tasa de interés, usando interés | $i = \frac{I}{C \times n}$ |
| Tiempo | $n = \frac{\frac{S}{C} - 1}{i}$ |
| Tiempo, usando interés | $n = \frac{I}{C \times i}$ |

---

## 10. Año ordinario y año exacto

En operaciones financieras, el tiempo puede calcularse usando diferentes bases anuales.

| Tipo de año | Días considerados | Uso frecuente |
|---|---:|---|
| Año ordinario o comercial | 360 días | Usado comúnmente en operaciones bancarias y comerciales. Cada mes se considera de 30 días. |
| Año calendario o exacto | 365 días | Usa la duración real del año. Puede variar a 366 días si se considera año bisiesto. |

### Fórmulas para expresar el tiempo en años

Si se usa año ordinario:

$$
n = \frac{\text{días transcurridos}}{360}
$$

Si se usa año exacto:

$$
n = \frac{\text{días transcurridos}}{365}
$$

### Observación importante

Si no se especifica el tipo de año, normalmente se asume **año ordinario o comercial de 360 días**, salvo que el problema indique lo contrario.

---

## 11. Procedimiento recomendado para resolver ejercicios

Para trabajar correctamente un problema de tasa simple, se recomienda seguir cuatro pasos:

### Paso 1: Identificación de datos

Se reconocen los datos de entrada del problema:

```text
C = capital
S = valor futuro
I = interés
i = tasa de interés
n = tiempo
```

No siempre aparecen todos los datos. La variable faltante es la que se debe calcular.

### Paso 2: Planteo de la fórmula adecuada

Se elige la fórmula según la variable solicitada.

Ejemplos:

```text
Si piden interés:       I = C × i × n
Si piden valor futuro:  S = C(1 + i × n)
Si piden valor actual:  C = S / (1 + i × n)
Si piden tasa:          i = [(S/C) - 1] / n
Si piden tiempo:        n = [(S/C) - 1] / i
```

### Paso 3: Resultados parciales

Se muestran los cálculos intermedios:

```text
Conversión de porcentaje a decimal
Conversión del tiempo
Sustitución de datos
Operaciones previas al resultado final
```

### Paso 4: Resultado final

Se presenta la respuesta con unidades monetarias, porcentaje o tiempo, según corresponda.

---

# Ejemplos resueltos

## Ejemplo 1: calcular interés y valor futuro

**Problema:**

¿Cuál es el interés y el valor futuro que produce un capital de S/ 1,000.00 durante 4 años, si está afecto a una tasa de interés simple anual de 24%?

### Paso 1: Datos

```text
C = S/ 1,000.00
i = 24% anual = 0.24
n = 4 años
I = ?
S = ?
```

La tasa y el tiempo están en años, por lo tanto, se pueden usar directamente.

### Paso 2: Fórmulas

$$
I = C \times i \times n
$$

$$
S = C + I
$$

También puede usarse:

$$
S = C(1 + i \times n)
$$

### Paso 3: Resultados parciales

Cálculo del interés:

```text
I = 1,000 × 0.24 × 4
I = 960
```

Cálculo del valor futuro:

```text
S = 1,000 + 960
S = 1,960
```

Verificación con fórmula directa:

```text
S = 1,000 × (1 + 0.24 × 4)
S = 1,000 × (1 + 0.96)
S = 1,000 × 1.96
S = 1,960
```

### Paso 4: Resultado final

```text
Interés = S/ 960.00
Valor futuro = S/ 1,960.00
```

---

## Ejemplo 2: tasa anual con plazo en meses

**Problema:**

¿Cuál es el interés y el valor futuro que produce un capital de S/ 7,500.00 durante 9 meses, si está afecto a una tasa de interés simple anual de 18%?

### Paso 1: Datos

```text
C = S/ 7,500.00
i = 18% anual = 0.18
n = 9 meses
I = ?
S = ?
```

La tasa está en años, pero el tiempo está en meses. Se convierte el plazo a años.

### Paso 2: Fórmulas

Conversión del tiempo:

$$
n = \frac{9}{12} = 0.75 \text{ años}
$$

Interés:

$$
I = C \times i \times n
$$

Valor futuro:

$$
S = C + I
$$

### Paso 3: Resultados parciales

```text
n = 9 / 12
n = 0.75 años
```

```text
I = 7,500 × 0.18 × 0.75
I = 1,012.50
```

```text
S = 7,500 + 1,012.50
S = 8,512.50
```

Verificación con fórmula directa:

```text
S = 7,500 × (1 + 0.18 × 0.75)
S = 7,500 × 1.135
S = 8,512.50
```

### Paso 4: Resultado final

```text
Interés = S/ 1,012.50
Valor futuro = S/ 8,512.50
```

---

## Ejemplo 3: hallar el valor presente de una deuda

**Problema:**

¿Cuál es el monto que debe cancelar hoy una persona para prepagar una deuda de US$ 11,125.00 que vence dentro de 9 meses, si fue contratada a una tasa de interés simple anual de 15%?

### Paso 1: Datos

```text
S = US$ 11,125.00
i = 15% anual = 0.15
n = 9 meses
C = ?
```

La tasa está en años y el tiempo en meses. Se convierte el plazo a años:

```text
n = 9 / 12 = 0.75 años
```

### Paso 2: Fórmula

$$
C = \frac{S}{1 + i \times n}
$$

### Paso 3: Resultados parciales

```text
C = 11,125 / (1 + 0.15 × 0.75)
C = 11,125 / (1 + 0.1125)
C = 11,125 / 1.1125
C = 10,000
```

Ahorro por pagar hoy:

```text
Ahorro = S - C
Ahorro = 11,125 - 10,000
Ahorro = 1,125
```

### Paso 4: Resultado final

```text
Valor presente de la deuda = US$ 10,000.00
Ahorro por prepago = US$ 1,125.00
```

---

## Ejemplo 4: hallar la tasa de interés simple

**Problema:**

¿A qué tasa de interés simple anual se acumulan S/ 72.00 de interés por un préstamo de S/ 1,200.00 durante 6 meses?

### Paso 1: Datos

```text
I = S/ 72.00
C = S/ 1,200.00
n = 6 meses
i = ?
```

Como se pide tasa anual, el tiempo debe expresarse en años:

```text
n = 6 / 12 = 0.5 años
```

### Paso 2: Fórmula

$$
i = \frac{I}{C \times n}
$$

### Paso 3: Resultados parciales

```text
i = 72 / (1,200 × 0.5)
i = 72 / 600
i = 0.12
```

Conversión a porcentaje:

```text
i = 0.12 × 100
i = 12%
```

### Paso 4: Resultado final

```text
Tasa de interés simple anual = 12% TSA
```

---

## Ejemplo 5: hallar el tiempo necesario

**Problema:**

¿Cuánto tiempo se tardará en hacer que un capital de US$ 1,350.00 acumule un valor futuro de por lo menos US$ 1,475.00, si está expuesto a una tasa de interés simple anual de 9%?

### Paso 1: Datos

```text
C = US$ 1,350.00
S = US$ 1,475.00
i = 9% anual = 0.09
n = ?
```

### Paso 2: Fórmula

$$
n = \frac{\frac{S}{C} - 1}{i}
$$

### Paso 3: Resultados parciales

```text
n = [(1,475 / 1,350) - 1] / 0.09
n = [1.092592593 - 1] / 0.09
n = 0.092592593 / 0.09
n = 1.028806584 años
```

Conversión a días con año ordinario:

```text
n = 1.028806584 × 360
n = 370.37 días
```

Como se necesita acumular por lo menos US$ 1,475.00, se redondea hacia arriba:

```text
n = 371 días
```

Verificación con 370 días:

```text
S = 1,350 × (1 + 0.09 × 370/360)
S = 1,474.88
```

No alcanza el valor mínimo de US$ 1,475.00.

Verificación con 371 días:

```text
S = 1,350 × (1 + 0.09 × 371/360)
S = 1,475.21
```

Sí alcanza el valor requerido.

### Paso 4: Resultado final

```text
Tiempo calculado = 1.028806584 años
Equivalente aproximado = 370.37 días
Tiempo financiero necesario = 371 días
```

---

## Ejemplo 6: comparar año ordinario y año exacto

**Problema:**

Pepe abre un depósito a plazo fijo con un capital de US$ 10,000.00, a una tasa simple anual de 6%, con vencimiento en un semestre. Calcular el valor futuro y los intereses usando año ordinario y año exacto.

### Paso 1: Datos

```text
C = US$ 10,000.00
i = 6% anual = 0.06
Plazo = 1 semestre = 6 meses = 180 días
S = ?
I = ?
```

### Paso 2: Fórmulas

$$
S = C(1 + i \times n)
$$

$$
I = S - C
$$

### Paso 3: Resultados parciales

#### Caso A: año ordinario o comercial

```text
n = 180 / 360
n = 0.50 años
```

```text
S = 10,000 × (1 + 0.06 × 0.50)
S = 10,000 × 1.03
S = 10,300.00
```

```text
I = 10,300.00 - 10,000.00
I = 300.00
```

#### Caso B: año exacto o calendario

```text
n = 180 / 365
n = 0.493150685 años
```

```text
S = 10,000 × (1 + 0.06 × 180/365)
S = 10,295.89
```

```text
I = 10,295.89 - 10,000.00
I = 295.89
```

### Paso 4: Resultado final

| Método | Tiempo usado | Valor futuro | Interés |
|---|---:|---:|---:|
| Año ordinario | 180/360 | US$ 10,300.00 | US$ 300.00 |
| Año exacto | 180/365 | US$ 10,295.89 | US$ 295.89 |

El año ordinario genera mayor interés porque usa un denominador menor: 360 días en lugar de 365.

---

## 12. Diferencia entre interés simple e interés compuesto

| Criterio | Interés simple | Interés compuesto |
|---|---|---|
| Capitalización | No existe capitalización. | Sí existe capitalización. |
| Base de cálculo | Siempre el capital inicial. | Capital inicial más intereses acumulados. |
| Crecimiento | Lineal. | Exponencial. |
| Fórmula general | $S = C(1 + i \times n)$ | $S = C(1+i)^n$ |
| Uso conceptual | Operaciones simples o de corto plazo. | Operaciones bancarias, inversiones y productos financieros con capitalización. |

En el mundo del interés simple, si se indica una **Tasa Nominal Anual de 12% bajo interés simple**, puede dividirse proporcionalmente entre 12 para obtener una tasa mensual simple de 1%, porque no hay capitalización.

En el mundo del interés compuesto, una tasa nominal necesita indicar la frecuencia de capitalización. Por ejemplo, no basta decir “12% nominal anual”; también se debe indicar si capitaliza mensual, trimestral, semestral, etc.

---

## 13. Conclusiones

1. La tasa simple calcula intereses sobre el capital inicial, sin incorporar los intereses generados al capital.

2. La fórmula central es $I = C \times i \times n$, y de ella se deriva el valor futuro $S = C(1 + i \times n)$.

3. La tasa `i` y el plazo `n` siempre deben estar expresados en la misma unidad de tiempo.

4. Si no se especifica el tipo de año, normalmente se trabaja con año ordinario o comercial de 360 días.

5. El año ordinario suele generar un interés mayor que el año exacto cuando se trabaja con la misma cantidad de días, porque el denominador usado para convertir el tiempo es menor.

6. El interés simple se diferencia del compuesto porque no capitaliza intereses; por tanto, el capital permanece constante durante toda la operación.
