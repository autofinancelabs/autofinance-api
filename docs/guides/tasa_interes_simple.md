# Tasa de interés simple

**Guía de estudio y trabajo basada en el PDF:** *Unidad 1 - 06 - Tasas de Interés - 01 Tasa Simple*  
**Tema:** tiempo financiero, tasa de interés simple, año ordinario, año exacto, valor futuro, valor presente, tasa y tiempo.  
**Objetivo:** explicar la tasa de interés simple de forma clara, operativa y reutilizable para resolver ejercicios manualmente, en Excel o mediante una IA.

---

## 1. Idea central

La **tasa de interés simple** permite calcular cuánto interés genera un capital durante un periodo determinado, bajo una regla fundamental: **el interés se calcula siempre sobre el capital inicial**, no sobre intereses acumulados.

En una operación de interés simple, el capital original permanece constante durante todo el plazo. Por eso, aunque se generen intereses, estos no se suman al capital para producir nuevos intereses.

La fórmula base es:

```math
I = C \cdot i \cdot t
```

Donde:

| Símbolo | Nombre | Significado |
|---|---|---|
| `I` | Interés | Ganancia o costo generado por el uso del capital. |
| `C` | Capital, valor presente, principal | Monto inicial invertido o prestado. |
| `i` | Tasa de interés simple | Tasa aplicada al capital, expresada en decimal. |
| `t` | Tiempo | Duración de la operación, expresada en la misma unidad que la tasa. |
| `S` | Stock, monto, valor futuro | Monto final que se recibe o paga al vencimiento. |

---

## 2. ¿Qué es el interés?

El **interés** es el costo o la rentabilidad asociada al uso del dinero en el tiempo.

Puede entenderse desde dos perspectivas:

| Perspectiva | Interpretación |
|---|---|
| Quien pide prestado | El interés es el **costo del dinero** por usar capital ajeno. |
| Quien presta o invierte | El interés es la **rentabilidad** obtenida por ceder capital durante un tiempo. |

En términos generales, las tasas de interés pueden clasificarse según la forma en que se acumulan los intereses:

| Tipo de interés | Característica principal |
|---|---|
| Interés simple | Los intereses se calculan siempre sobre el capital inicial. No hay capitalización. |
| Interés compuesto | Los intereses se suman al capital y luego generan nuevos intereses. Sí hay capitalización. |

---

## 3. Interés simple versus interés compuesto

La diferencia esencial entre interés simple e interés compuesto es la **capitalización**.

| Criterio | Interés simple | Interés compuesto |
|---|---|---|
| Capital base | Permanece constante. | Aumenta cuando los intereses se reinvierten. |
| Capitalización | No existe. | Sí existe. |
| Intereses generados | Se calculan sobre el capital inicial. | Se calculan sobre capital más intereses acumulados. |
| Crecimiento | Lineal. | Exponencial. |
| Fórmula típica | `S = C(1 + i·t)` | `S = C(1+i)^n` |

### Conclusión clave

En el **mundo del interés simple**, si se indica una tasa anual simple de 12%, puede prorratearse linealmente:

```math
\text{Tasa mensual simple} = \frac{12\%}{12} = 1\%
```

En cambio, en el **mundo del interés compuesto**, una tasa nominal anual no queda completamente definida si no se indica cada cuánto se capitaliza. Por ejemplo, no es lo mismo una tasa nominal anual capitalizable mensualmente que una capitalizable trimestralmente.

---

## 4. TNA, TSA y capitalización

En el material del curso se usa con frecuencia la expresión **TSA**, que significa **Tasa Simple Anual**. En otros contextos financieros puede aparecer la expresión **TNA**, es decir, **Tasa Nominal Anual**.

La diferencia práctica es importante:

| Concepto | Uso en interés simple | Uso en interés compuesto |
|---|---|---|
| Tasa anual simple o TSA | Se prorratea linealmente. Ejemplo: 12% anual simple equivale a 1% mensual simple. | No aplica como tasa compuesta efectiva por sí sola. |
| Tasa nominal anual o TNA | Puede funcionar como referencia anual lineal si se trabaja bajo interés simple. | Necesita indicar frecuencia de capitalización: mensual, bimestral, trimestral, etc. |
| Capitalización | No existe. | Es indispensable para interpretar correctamente la tasa nominal. |

### Ejemplo conceptual

Si se tiene una tasa anual de 10% durante 30 años:

| Escenario | Interpretación | Resultado conceptual |
|---|---|---|
| Sin reinversión de intereses | Interés simple. El capital original no se incrementa con los intereses. | Rendimiento acumulado: `10% × 30 = 300%`. |
| Con reinversión de intereses | Interés compuesto. Los intereses se suman al capital y generan nuevos intereses. | Rendimiento acumulado: `(1+0.10)^30 - 1 = 1644.94%`. |

La frase importante es: **si los intereses se reinvierten, hay interés compuesto; si los intereses se retiran y el capital permanece constante, hay interés simple**.

---

## 5. El papel del tiempo en finanzas

El tiempo es fundamental porque permite determinar las ganancias o intereses generados por un capital. En finanzas, el dinero cambia de valor cuando se desplaza en el tiempo y está afectado por una tasa de interés.

Ideas centrales:

| Idea | Explicación |
|---|---|
| El tiempo no se controla | Solo se puede administrar o programar. |
| El dinero aumenta hacia el futuro | Cuando se acumula mediante una tasa de interés. |
| El dinero disminuye hacia el presente | Cuando se descuenta mediante una tasa. |
| No se suele hablar de “tiempo pasado” | Se habla de valor presente, valor actual o capital. |
| El futuro se representa como monto | Se habla de valor futuro, stock o monto acumulado. |

Representación conceptual:

```text
Presente                                             Futuro
   C -------------------- tiempo -------------------- S
      se acumula con una tasa de interés simple
```

Si se va del presente al futuro, se calcula un **valor futuro**.  
Si se va del futuro al presente, se calcula un **valor presente**.

---

## 6. Flujo de dinero

Un **flujo de dinero** es la representación de una cantidad monetaria que puede ser de ingreso o egreso.

| Tipo de flujo | Ejemplos |
|---|---|
| Ingreso | Préstamo recibido, retorno, cobro, ingreso financiero. |
| Egreso | Pago, inversión, desembolso, devolución de deuda. |

Un flujo cambia de valor únicamente cuando se desplaza en el tiempo y está sujeto a una tasa de interés.

---

## 7. Equivalencias de tiempo usadas en el curso

El material trabaja principalmente con el **año ordinario o comercial de 360 días**, salvo que el enunciado indique lo contrario.

| Periodo | Equivalencias |
|---|---|
| 1 año | 2 semestres, 3 cuatrimestres, 4 trimestres, 6 bimestres, 12 meses, 360 días |
| 1 semestre | 2 trimestres, 3 bimestres, 6 meses, 12 quincenas, 180 días |
| 1 cuatrimestre | 4 meses, 8 quincenas, 120 días |
| 1 trimestre | 3 meses, 6 quincenas, 90 días |
| 1 bimestre | 2 meses, 4 quincenas, 60 días |
| 1 mes | 2 quincenas, 30 días |
| 1 quincena | 15 días |

Estas equivalencias son importantes porque el valor financiero cambia si se usa un año de 360 días o uno de 365 días.

---

## 8. Año ordinario o comercial y año calendario o exacto

En los ejercicios de interés simple se debe distinguir entre:

1. **El tiempo transcurrido**, que va en el numerador.
2. **El año base**, que va en el denominador.

La regla general es:

```math
t = \frac{\text{tiempo transcurrido}}{\text{año base}}
```

También puede expresarse como:

```math
n = \frac{\text{plazo de la operación}}{\text{base anual}}
```

Donde `n` o `t` representa el plazo expresado como proporción de año.

### 8.1 Año ordinario o comercial: 360 días

El **año ordinario o comercial** considera un año de 360 días. Se usa mucho en cálculos bancarios y comerciales porque simplifica las equivalencias: cada mes se considera de 30 días.

```math
t = \frac{\text{días transcurridos}}{360}
```

Ejemplos:

| Plazo | Conversión comercial |
|---|---:|
| 3 meses | `90/360 = 0.25` años |
| 6 meses | `180/360 = 0.50` años |
| 9 meses | `270/360 = 0.75` años |

### 8.2 Año calendario o exacto: 365 días

El **año calendario o exacto** considera un año de 365 días. Si se toma en cuenta el calendario real, los meses pueden tener 28, 29, 30 o 31 días.

```math
t = \frac{\text{días transcurridos}}{365}
```

En algunos contextos puede considerarse 366 días cuando el año es bisiesto, pero el material del curso trabaja la definición de año exacto con base 365 días.

---

## 9. Las cuatro combinaciones de tiempo y año base

Para evitar confusiones, conviene separar el **numerador** y el **denominador**.

| Método | Numerador: tiempo transcurrido | Denominador: año base | Ejemplo semestral o calendario |
|---|---:|---:|---:|
| Tiempo comercial y año comercial | 180 días | 360 días | `180/360 = 0.500000000` |
| Tiempo comercial y año exacto | 180 días | 365 días | `180/365 = 0.493150685` |
| Tiempo exacto y año comercial | 184 días | 360 días | `184/360 = 0.511111111` |
| Tiempo exacto y año exacto | 184 días | 365 días | `184/365 = 0.504109589` |

### Regla de lectura

| Elemento | Pregunta que responde |
|---|---|
| Tiempo transcurrido | ¿Cuántos días duró realmente o comercialmente la operación? |
| Año base | ¿Con qué base anual se divide el plazo: 360 o 365? |

En el curso, si no se especifica otra cosa, se asume **año comercial de 360 días**. En operaciones bancarias también es común encontrar el método de **tiempo exacto y año comercial**, es decir, contar días reales en el numerador y dividir entre 360; aun así, siempre debe revisarse el contrato, la norma aplicable o el enunciado del problema.

---

## 10. Interés simple exacto versus ordinario

Cuando se usa una tasa anual, el interés simple ordinario normalmente genera un monto mayor que el interés exacto, porque el denominador 360 produce una fracción de año más grande que el denominador 365.

| Método | Base anual | Fórmula del tiempo | Efecto |
|---|---:|---|---|
| Interés simple ordinario o comercial | 360 días | `t = días / 360` | Genera mayor fracción de año. |
| Interés simple exacto o calendario | 365 días | `t = días / 365` | Genera menor fracción de año. |

Ejemplo con 90 días:

| Método | Tiempo en años |
|---|---:|
| Ordinario | `90/360 = 0.2500000000` |
| Exacto | `90/365 = 0.2465753425` |

Por eso, con el mismo capital y la misma tasa anual, el interés ordinario será ligeramente mayor.

---

## 11. Fórmulas principales de interés simple

### 11.1 Interés

```math
I = S - C
```

```math
I = C \cdot i \cdot t
```

### 11.2 Valor futuro

```math
S = C + I
```

Como `I = C·i·t`, entonces:

```math
S = C + C \cdot i \cdot t
```

Factorizando:

```math
S = C(1+i \cdot t)
```

La expresión `(1+i·t)` se llama **factor de acumulación a tasa de interés simple**.

### 11.3 Valor presente

Si se conoce el valor futuro y se desea calcular el capital equivalente en el presente:

```math
C = \frac{S}{1+i \cdot t}
```

También puede escribirse como:

```math
C = S(1+i \cdot t)^{-1}
```

La expresión `(1+i·t)^-1` se llama **factor de descuento a tasa de interés simple**.

### 11.4 Despeje de variables

| Variable buscada | Fórmula |
|---|---|
| Valor futuro | `S = C(1+i·t)` |
| Valor presente o capital | `C = S/(1+i·t)` |
| Interés | `I = S-C` o `I = C·i·t` |
| Tiempo | `t = ((S/C)-1)/i` |
| Tasa de interés simple | `i = ((S/C)-1)/t` |
| Tasa usando interés | `i = I/(C·t)` |
| Tiempo usando interés | `t = I/(C·i)` |

---

## 12. Reglas obligatorias para resolver ejercicios

| Regla | Explicación | Ejemplo |
|---|---|---|
| La tasa debe estar en decimal | Dividir entre 100. | `18% = 0.18` |
| La tasa y el tiempo deben estar en la misma unidad | Si la tasa es anual, el tiempo debe estar en años. | `9 meses = 9/12 = 0.75 años` |
| Si no se indica el tipo de año, usar año ordinario | Base de 360 días. | `90 días = 90/360 años` |
| Si se pide interés exacto, usar 365 días | Base de 365 días. | `90 días = 90/365 años` |
| Si se trabaja con meses comerciales, cada mes tiene 30 días | Regla del año ordinario. | `3 meses = 90 días` |
| Si se dan fechas calendario, contar días reales | Según los días de cada mes. | Julio + agosto + setiembre = `31 + 31 + 30` |
| Si se pide alcanzar “por lo menos” un monto | Redondear días hacia arriba. | `370.37 días → 371 días` |

---

## 13. Procedimiento general para resolver problemas

### Paso 1: Identificar datos

```text
C = capital inicial
S = valor futuro
I = interés
i = tasa de interés simple
t = tiempo
```

### Paso 2: Uniformizar unidades

La tasa y el tiempo deben estar en la misma unidad.

| Situación | Conversión recomendada |
|---|---|
| Tasa anual y tiempo en meses comerciales | Convertir meses a años con base 360: `meses×30/360`. |
| Tasa anual y tiempo en días ordinarios | Usar `t = días/360`. |
| Tasa anual y tiempo en días exactos | Usar `t = días/365`. |
| Tasa mensual y tiempo en meses | Usar directamente. |

### Paso 3: Elegir fórmula

| Pregunta del problema | Fórmula principal |
|---|---|
| ¿Cuánto interés genera? | `I=C·i·t` |
| ¿Cuánto se paga o recibe al final? | `S=C(1+i·t)` |
| ¿Cuánto vale hoy un monto futuro? | `C=S/(1+i·t)` |
| ¿Qué tasa se está cobrando? | `i=(S/C-1)/t` |
| ¿Cuánto tiempo se necesita? | `t=(S/C-1)/i` |

### Paso 4: Calcular y redondear

En ejercicios financieros se suele redondear el dinero a dos decimales. Si el problema pregunta por el tiempo mínimo necesario para alcanzar un monto, se debe redondear hacia arriba cuando el resultado tenga decimales en días.

---

# 14. Ejemplos desarrollados

## Ejemplo 1: interés y valor futuro con tiempo en años

**Problema:** ¿Cuál es el interés y el valor futuro que produce un capital de S/. 1,000.00 durante 4 años, si está afecto a una tasa de interés simple anual de 24%?

### Datos

| Dato | Valor |
|---|---:|
| Capital `C` | S/. 1,000.00 |
| Tasa `i` | 24% anual = 0.24 |
| Tiempo `t` | 4 años |

Como la tasa y el tiempo están en años, se usan directamente.

```math
I = C \cdot i \cdot t
```

```math
I = 1000(0.24)(4) = 960
```

```math
S = C + I = 1000 + 960 = 1960
```

También:

```math
S = C(1+i \cdot t) = 1000(1+0.24 \cdot 4) = 1960
```

| Concepto | Resultado |
|---|---:|
| Interés | S/. 960.00 |
| Valor futuro | S/. 1,960.00 |

---

## Ejemplo 2: interés y valor futuro con tiempo en meses

**Problema:** ¿Cuál es el interés y el valor futuro que produce un capital de S/. 7,500.00 en 9 meses, si está afecto a una tasa de interés simple anual de 18%?

### Datos

| Dato | Valor |
|---|---:|
| Capital `C` | S/. 7,500.00 |
| Tasa `i` | 18% anual = 0.18 |
| Tiempo | 9 meses |

### Caso A: interés simple ordinario

```math
t = \frac{9 \cdot 30}{360} = \frac{270}{360} = 0.75
```

```math
I = 7500(0.18)(0.75) = 1012.50
```

```math
S = 7500 + 1012.50 = 8512.50
```

### Caso B: interés simple exacto

```math
t = \frac{9 \cdot 30}{365} = \frac{270}{365} = 0.7397260274
```

```math
I = 7500(0.18)(0.7397260274) = 998.63
```

```math
S = 7500 + 998.63 = 8498.63
```

| Método | Tiempo | Interés | Valor futuro |
|---|---:|---:|---:|
| Ordinario, 360 días | 0.7500000000 | S/. 1,012.50 | S/. 8,512.50 |
| Exacto, 365 días | 0.7397260274 | S/. 998.63 | S/. 8,498.63 |

---

## Ejemplo 3: depósito a plazo fijo por un semestre

**Problema:** Pepe abre un depósito a plazo fijo por US$ 10,000.00 a una tasa simple anual de 6%, con vencimiento de un semestre. Calcular el valor futuro y los intereses usando año ordinario y año calendario.

### Datos

| Dato | Valor |
|---|---:|
| Capital `C` | US$ 10,000.00 |
| Tasa `i` | 6% anual = 0.06 |
| Plazo | 6 meses = 180 días comerciales |
| Valor futuro `S` | ? |
| Interés `I` | ? |

### Caso A: tiempo comercial y año comercial

```math
t = \frac{6}{12} = \frac{180}{360} = 0.50
```

```math
S = C(1+i \cdot t)
```

```math
S = 10000(1+0.06 \cdot 0.50) = 10300.00
```

```math
I = S-C = 10300.00-10000.00 = 300.00
```

### Caso B: tiempo comercial y año exacto

```math
t = \frac{6 \cdot 30}{365} = \frac{180}{365} = 0.493150685
```

```math
S = 10000\left(1+0.06 \cdot \frac{180}{365}\right) = 10295.89
```

```math
I = S-C = 10295.89-10000.00 = 295.89
```

### Resultado comparativo

| Método | Cálculo del tiempo | Valor futuro `S` | Interés `I` |
|---|---:|---:|---:|
| Tiempo comercial y año comercial | `180/360` | US$ 10,300.00 | US$ 300.00 |
| Tiempo comercial y año exacto | `180/365` | US$ 10,295.89 | US$ 295.89 |

---

## Ejemplo 4: depósito del 1 de marzo al 1 de setiembre

**Problema:** El 1 de marzo, Pepe abre un depósito a plazo fijo por US$ 10,000.00 a una tasa simple anual de 6%. El depósito vence el 1 de setiembre del mismo año. Calcular el valor futuro y los intereses usando año ordinario y año calendario.

### Datos

| Dato | Valor |
|---|---:|
| Capital `C` | US$ 10,000.00 |
| Tasa `i` | 6% anual = 0.06 |
| Plazo | Del 01/03 al 01/09 |
| Valor futuro `S` | ? |
| Interés `I` | ? |

### Conteo de días exactos

El 1 de setiembre no se cuenta porque ese día vence el depósito.

| Mes | Días contados |
|---|---:|
| Marzo | 31 |
| Abril | 30 |
| Mayo | 31 |
| Junio | 30 |
| Julio | 31 |
| Agosto | 31 |
| Setiembre | 0 |
| **Total** | **184** |

### Caso A: tiempo exacto y año comercial

```math
t = \frac{184}{360} = 0.511111111
```

```math
S = 10000\left(1+0.06 \cdot \frac{184}{360}\right) = 10306.67
```

```math
I = S-C = 10306.67-10000.00 = 306.67
```

### Caso B: tiempo exacto y año exacto

```math
t = \frac{184}{365} = 0.504109589
```

```math
S = 10000\left(1+0.06 \cdot \frac{184}{365}\right) = 10302.47
```

```math
I = S-C = 10302.47-10000.00 = 302.47
```

### Resultado comparativo

| Método | Cálculo del tiempo | Valor futuro `S` | Interés `I` |
|---|---:|---:|---:|
| Tiempo exacto y año comercial | `184/360` | US$ 10,306.67 | US$ 306.67 |
| Tiempo exacto y año exacto | `184/365` | US$ 10,302.47 | US$ 302.47 |

---

## Ejemplo 5: préstamo de 3 meses con año ordinario y exacto

**Problema:** Calcular el interés simple ordinario, el interés simple exacto y el valor futuro de un préstamo de US$ 10,250.00 al 15% anual, que debe devolverse en 3 meses.

| Dato | Valor |
|---|---:|
| Capital `C` | US$ 10,250.00 |
| Tasa `i` | 15% anual = 0.15 |
| Tiempo | 3 meses |

### Caso A: año ordinario

```math
t = \frac{3 \cdot 30}{360} = \frac{90}{360} = 0.25
```

```math
I = 10250(0.15)(0.25) = 384.38
```

```math
S = 10250 + 384.38 = 10634.38
```

### Caso B: año exacto

```math
t = \frac{3 \cdot 30}{365} = \frac{90}{365} = 0.2465753425
```

```math
I = 10250(0.15)(0.2465753425) = 379.11
```

```math
S = 10250 + 379.11 = 10629.11
```

| Método | Tiempo | Interés | Valor futuro |
|---|---:|---:|---:|
| Ordinario | `90/360` | US$ 384.38 | US$ 10,634.38 |
| Exacto | `90/365` | US$ 379.11 | US$ 10,629.11 |

---

## Ejemplo 6: préstamo con fechas calendario

**Problema:** Repetir el ejercicio anterior, pero suponiendo que la operación inicia el 1 de julio y se cancela el 1 de octubre del mismo año.

### Conteo de días

| Mes | Días |
|---|---:|
| Julio | 31 |
| Agosto | 31 |
| Setiembre | 30 |
| **Total** | **92** |

| Dato | Valor |
|---|---:|
| Capital `C` | US$ 10,250.00 |
| Tasa `i` | 15% anual = 0.15 |
| Tiempo real | 92 días |

### Caso A: año ordinario

```math
t = \frac{92}{360} = 0.2555555556
```

```math
I = 10250(0.15)(0.2555555556) = 392.92
```

```math
S = 10250 + 392.92 = 10642.92
```

### Caso B: año exacto

```math
t = \frac{92}{365} = 0.2520547945
```

```math
I = 10250(0.15)(0.2520547945) = 387.53
```

```math
S = 10250 + 387.53 = 10637.53
```

| Método | Tiempo | Interés | Valor futuro |
|---|---:|---:|---:|
| Tiempo exacto y año ordinario | `92/360` | US$ 392.92 | US$ 10,642.92 |
| Tiempo exacto y año exacto | `92/365` | US$ 387.53 | US$ 10,637.53 |

---

## Ejemplo 7: cálculo de la tasa de interés simple

**Problema:** ¿A qué tasa de interés simple se acumularán S/. 72.00 por el préstamo de S/. 1,200.00 en 6 meses?

| Dato | Valor |
|---|---:|
| Interés `I` | S/. 72.00 |
| Capital `C` | S/. 1,200.00 |
| Tiempo | 6 meses |
| Tasa `i` | ? |

Se asume año ordinario porque no se especifica otra cosa.

```math
t = \frac{6 \cdot 30}{360} = \frac{180}{360} = 0.5
```

Como:

```math
I = C \cdot i \cdot t
```

Entonces:

```math
i = \frac{I}{C \cdot t}
```

```math
i = \frac{72}{1200(0.5)} = \frac{72}{600} = 0.12
```

```math
i = 12\% \text{ TSA}
```

---

## Ejemplo 8: valor presente por prepago de deuda

**Problema:** ¿Cuánto debe cancelar Pedro hoy por un préstamo de US$ 11,125.00 que vence dentro de 9 meses, si fue contratado a una tasa de interés simple anual de 15%?

| Dato | Valor |
|---|---:|
| Valor futuro `S` | US$ 11,125.00 |
| Tasa `i` | 15% anual = 0.15 |
| Tiempo | 9 meses |
| Capital actual `C` | ? |

```math
t = \frac{9 \cdot 30}{360} = \frac{270}{360} = 0.75
```

```math
C = \frac{S}{1+i \cdot t}
```

```math
C = \frac{11125}{1+0.15(0.75)} = 10000
```

```math
Ahorro = S-C = 11125-10000 = 1125
```

| Concepto | Resultado |
|---|---:|
| Monto a pagar hoy | US$ 10,000.00 |
| Ahorro financiero | US$ 1,125.00 |

---

## Ejemplo 9: cálculo del tiempo necesario

**Problema:** ¿Cuánto tiempo se necesita para que un capital de US$ 1,350.00 acumule al menos US$ 1,475.00, si está expuesto a una tasa de interés simple anual de 9%?

| Dato | Valor |
|---|---:|
| Capital `C` | US$ 1,350.00 |
| Valor futuro deseado `S` | US$ 1,475.00 |
| Tasa `i` | 9% anual = 0.09 |
| Tiempo `t` | ? |

```math
t = \frac{S/C - 1}{i}
```

```math
t = \frac{1475/1350 - 1}{0.09} = 1.028806584 \text{ años}
```

En días de año ordinario:

```math
t = 1.028806584 \cdot 360 = 370.3703702 \text{ días}
```

Como se necesita alcanzar **por lo menos** US$ 1,475.00, se redondea hacia arriba:

```math
t = 371 \text{ días}
```

### Verificación

Con 370 días:

```math
S = 1350\left(1+0.09 \cdot \frac{370}{360}\right) = 1474.88
```

No alcanza US$ 1,475.00.

Con 371 días:

```math
S = 1350\left(1+0.09 \cdot \frac{371}{360}\right) = 1475.21
```

Sí alcanza el monto requerido.

---

## Ejemplo 10: tasa implícita en una operación informal

**Problema:** Juan pidió prestado S/. 5,000.00 y acordó devolver S/. 6,000.00 en 2 meses. ¿Cuál es la tasa de interés simple anual implícita?

| Dato | Valor |
|---|---:|
| Capital `C` | S/. 5,000.00 |
| Valor futuro `S` | S/. 6,000.00 |
| Tiempo | 2 meses |
| Tasa `i` | ? |

```math
t = \frac{2 \cdot 30}{360} = \frac{60}{360} = \frac{1}{6}
```

```math
i = \frac{S/C - 1}{t}
```

```math
i = \frac{6000/5000 - 1}{60/360} = \frac{0.2}{1/6} = 1.2
```

```math
i = 120\% \text{ anual}
```

---

## Ejemplo 11: descuento por pronto pago

**Problema:** Una factura de US$ 2,800.00 tiene condiciones **3/10, n/30**. Esto significa que hay un descuento de 3% si se paga hasta el día 10; si no, se paga el monto completo hasta el día 30.

Se pide:

1. ¿Cuál es la tasa máxima a la que puede obtenerse un préstamo bancario para aprovechar el descuento?
2. ¿Qué utilidad se logra si el banco presta a una tasa de interés simple anual de 18% y se paga la factura el día 10?

### Interpretación de `3/10, n/30`

| Elemento | Significado |
|---|---|
| `3/10` | Descuento de 3% si se paga hasta el día 10. |
| `n/30` | Si no se toma el descuento, se paga el total hasta el día 30. |
| Periodo relevante | 20 días, porque se adelanta el pago del día 30 al día 10. |

### Datos

| Dato | Valor |
|---|---:|
| Factura | US$ 2,800.00 |
| Descuento | 3% |
| Pago con descuento | `2800 × 0.97 = 2716` |
| Ahorro por descuento | US$ 84.00 |
| Tiempo financiero | 20 días |

### a) Tasa máxima aceptable

```math
i = \frac{84}{2716 \cdot (20/360)} = 0.5567010309
```

```math
i = 55.67010309\%
```

### b) Utilidad con préstamo al 18% anual

```math
I = 2716(0.18)\left(\frac{20}{360}\right) = 27.16
```

```math
Utilidad = 84 - 27.16 = 56.84
```

| Pregunta | Resultado |
|---|---:|
| Tasa máxima aceptable | 55.67010309% TSA |
| Utilidad con préstamo al 18% | US$ 56.84 |

---

## Ejemplo 12: tasa implícita en compra al crédito

**Problema:** Un televisor tiene precio de venta al contado de S/. 1,800.00. Víctor paga S/. 300.00 de cuota inicial y el saldo a 30 días, aceptando un recargo de 8% sobre el precio al contado. ¿Qué tasa de interés simple anual implícita paga por el crédito?

| Concepto | Cálculo | Valor |
|---|---:|---:|
| Precio al contado | — | S/. 1,800.00 |
| Recargo | 8% | — |
| Precio de lista | `1800 × 1.08` | S/. 1,944.00 |
| Cuota inicial | — | S/. 300.00 |
| Capital financiado `C` | `1800 - 300` | S/. 1,500.00 |
| Monto a pagar `S` | `1944 - 300` | S/. 1,644.00 |
| Tiempo | `30/360` | 0.0833333333 años |

```math
i = \frac{S/C - 1}{t}
```

```math
i = \frac{1644/1500 - 1}{30/360} = 1.152
```

```math
i = 115.2\% \text{ anual}
```

---

# 15. Plantillas rápidas de resolución

## 15.1 Para calcular interés y monto futuro

```text
Datos:
C =
i =
t =

Convertir i a decimal.
Convertir t a la misma unidad de i.

I = C * i * t
S = C + I
```

## 15.2 Para calcular valor presente

```text
Datos:
S =
i =
t =

C = S / (1 + i * t)
Ahorro o descuento = S - C
```

## 15.3 Para calcular tasa de interés

```text
Datos:
C =
S =
t =

Convertir t a la unidad de la tasa buscada.

i = (S / C - 1) / t
Tasa porcentual = i * 100
```

## 15.4 Para calcular tiempo

```text
Datos:
C =
S =
i =

Convertir i a decimal.

t = (S / C - 1) / i

Si i es anual, t queda en años.
Si se necesita en días ordinarios:
días = t * 360
```

---

# 16. Fórmulas tipo Excel

Supongamos que:

| Celda | Contenido |
|---|---|
| B2 | Capital `C` |
| B3 | Tasa anual en porcentaje, por ejemplo `18%` |
| B4 | Días |
| B5 | Base anual: 360 o 365 |
| B6 | Valor futuro `S`, si aplica |

## Tiempo en años

```excel
=B4/B5
```

## Interés simple

```excel
=B2*B3*(B4/B5)
```

## Valor futuro

```excel
=B2*(1+B3*(B4/B5))
```

## Valor presente

```excel
=B6/(1+B3*(B4/B5))
```

## Tasa implícita

```excel
=(B6/B2-1)/(B4/B5)
```

## Tiempo requerido en años

```excel
=(B6/B2-1)/B3
```

## Tiempo requerido en días ordinarios

```excel
=((B6/B2-1)/B3)*360
```

Si se requiere alcanzar un monto mínimo:

```excel
=REDONDEAR.MAS(((B6/B2-1)/B3)*360,0)
```

---

# 17. Pseudocódigo para una IA o programa

```text
Entrada:
  C: capital inicial, opcional
  S: valor futuro, opcional
  I: interés, opcional
  i: tasa de interés simple, opcional
  t: tiempo, opcional
  unidad_tasa: anual, mensual, diaria, etc.
  tipo_anio: ordinario_360 o exacto_365
  dias: número de días, opcional

Proceso:
  1. Convertir la tasa porcentual a decimal si viene como porcentaje.
  2. Convertir el tiempo a la misma unidad de la tasa.
     Si la tasa es anual:
       si tipo_anio = ordinario_360, t = dias / 360
       si tipo_anio = exacto_365, t = dias / 365
  3. Elegir fórmula según la variable faltante:
       si falta I: I = C * i * t
       si falta S: S = C * (1 + i * t)
       si falta C: C = S / (1 + i * t)
       si falta i: i = (S / C - 1) / t
       si falta t: t = (S / C - 1) / i
  4. Redondear montos a dos decimales.
  5. Si se calcula tiempo mínimo en días, redondear hacia arriba.

Salida:
  Mostrar la variable calculada, unidades, fórmula usada y conversión de tiempo.
```

---

# 18. Errores frecuentes

| Error | Por qué está mal | Corrección |
|---|---|---|
| Usar `18` en vez de `0.18` | La fórmula requiere tasa decimal. | Dividir la tasa entre 100. |
| Mezclar tasa anual con tiempo mensual | Las unidades no coinciden. | Convertir meses a años o tasa anual a mensual. |
| Usar 365 cuando se pidió año ordinario | Cambia el resultado financiero. | Revisar si el enunciado indica ordinario o exacto. |
| No contar correctamente los días calendario | El tiempo puede cambiar. | Sumar los días reales de cada mes. |
| Redondear hacia abajo el tiempo mínimo | Puede no alcanzarse el monto deseado. | Redondear hacia arriba cuando se pide “por lo menos”. |
| Confundir valor futuro con interés | El valor futuro incluye capital más interés. | Recordar: `S = C + I`. |
| Confundir interés simple con compuesto | En simple no se capitaliza. | Verificar si los intereses se reinvierten. |
| Interpretar una TNA compuesta sin capitalización | La tasa nominal compuesta requiere frecuencia `m`. | Pedir o identificar la frecuencia de capitalización. |

---

# 19. Ejercicios propuestos del PDF con respuestas

| N.° | Enunciado resumido | Respuesta indicada |
|---:|---|---:|
| 1 | Interés simple ordinario y exacto de US$ 500.00 en 90 días al 8.5% anual. | US$ 10.63 y US$ 10.48 |
| 2 | Interés simple ordinario y exacto de US$ 600.00 en 118 días al 16% anual. | US$ 31.47 y US$ 31.04 |
| 3 | Valor al vencimiento de US$ 2,500 en 18 meses al 12% simple ordinario anual. | US$ 2,950.00 |
| 4 | Valor al vencimiento de US$ 1,200 en 120 días al 8.5% simple exacto anual. | US$ 1,233.53 |
| 5 | Préstamo de US$ 100.00 pagadero con US$ 120.00 en un mes. Calcular tasa simple ordinaria anual. | 240% |
| 6a | Tiempo para que S/. 1,000.00 gane S/. 100.00 al 15% simple. | 8 meses |
| 6b | Tiempo para que S/. 1,000.00 aumente al menos a S/. 1,200.00 al 13.5% simple. | 534 días |
| 7 | Tiempo para que US$ 5,000.00 llegue al menos a US$ 6,000.00 al 10% simple anual. | 2 años o 720 días |
| 8 | Tiempo para que US$ 12,350.00 llegue al doble al 11.25% simple anual. | 8.889 años o 3,200 días |
| 9a | Factura de US$ 8,000.00 con términos 3/10, n/40. Tasa máxima para aprovechar descuento. | 37.11340206% |
| 9b | Ganancia si se accede a préstamo al 21% y se paga anticipadamente. | US$ 104.20 |
| 10 | Refrigeradora de US$ 576.00, cuota inicial US$ 70.00, saldo a 30 días, recargo 12%. Tasa simple anual. | 163.9209486% |

---

# 20. Conclusiones operativas

1. El interés simple se calcula sobre el capital inicial. No existe capitalización.
2. La tasa y el tiempo siempre deben expresarse en la misma unidad.
3. Si la tasa es anual, el tiempo debe convertirse a años.
4. Si el enunciado no especifica el tipo de año, se asume año ordinario o comercial de 360 días en el curso.
5. El año ordinario o comercial usa 360 días y simplifica el cálculo considerando meses de 30 días.
6. El año calendario o exacto usa 365 días y permite una medición más cercana al calendario real.
7. El método elegido puede cambiar el interés y el valor futuro.
8. Con igual capital, tasa y días, dividir entre 360 genera más interés que dividir entre 365.
9. Una tasa nominal anual en interés simple puede prorratearse linealmente; en interés compuesto requiere conocer la frecuencia de capitalización.
10. Si los intereses se retiran, se conserva la lógica del interés simple; si se reinvierten, se entra al mundo del interés compuesto.

---

# 21. Mini chuleta de fórmulas

| Necesito calcular | Uso esta fórmula |
|---|---|
| Interés | `I = C·i·t` |
| Valor futuro | `S = C(1+i·t)` |
| Valor presente | `C = S/(1+i·t)` |
| Tasa | `i = (S/C-1)/t` |
| Tiempo | `t = (S/C-1)/i` |
| Tiempo ordinario en años | `t = días/360` |
| Tiempo exacto en años | `t = días/365` |
| Tiempo comercial semestral | `t = 6/12 = 180/360` |
| Tiempo exacto con año comercial | `t = días reales/360` |
| Tiempo exacto con año exacto | `t = días reales/365` |

---

# 22. Esquema mental final

```text
1. Identifica qué se pide: I, S, C, i o t.
2. Determina si el plazo es comercial o exacto.
3. Determina si el año base es 360 o 365.
4. Convierte la tasa a decimal.
5. Convierte el tiempo a la misma unidad que la tasa.
6. Aplica la fórmula.
7. Redondea montos a dos decimales.
8. Si se pide tiempo mínimo, redondea días hacia arriba.
```

La tasa de interés simple es una herramienta básica para valorar operaciones financieras de corto o mediano plazo cuando no se capitalizan intereses. Su correcta aplicación depende menos de memorizar fórmulas y más de interpretar bien el tiempo, la tasa, el tipo de año y la variable buscada.
