# Indicadores de Rentabilidad y Análisis de Reemplazo

**Guía práctica en Markdown para estudiar, resolver y automatizar ejercicios de evaluación económica de proyectos**

**Fuente base:** *Finanzas e Ingeniería Económica. Unidad 3: Indicadores de Rentabilidad*, Senmache S. José.  
**Indicadores principales tratados:** VAN, TIR, B/C, PRD, VAC, CAUE, MCM y CC.

---

## 1. Propósito de esta guía

Esta guía explica cómo evaluar si un proyecto, activo o alternativa de inversión conviene económicamente. Está pensada para que pueda ser usada por una persona, una hoja de cálculo o una IA que necesite resolver ejercicios de ingeniería económica.

El documento cubre dos bloques:

1. **Indicadores de rentabilidad**, usados cuando el proyecto genera beneficios o flujos netos.
2. **Análisis de reemplazo o elección de activos**, usado cuando se comparan alternativas que principalmente generan costos.


## 1.1 ¿Qué son los indicadores de rentabilidad?

Los **indicadores de rentabilidad** son herramientas financieras que ayudan a decidir si conviene invertir dinero en un proyecto. Su función es comparar la inversión inicial con los beneficios económicos futuros, considerando que el dinero tiene distinto valor según el momento en que se recibe o se paga.

En términos simples, permiten responder preguntas como estas:

| Pregunta práctica                                       | Indicador que ayuda a responderla | Lectura rápida                                      |
|---------------------------------------------------------|-----------------------------------|-----------------------------------------------------|
| ¿Cuánto valor monetario gano hoy si acepto el proyecto? | VAN                               | Mide riqueza creada en unidades monetarias.         |
| ¿Qué porcentaje de rentabilidad genera el proyecto?     | TIR                               | Mide rentabilidad implícita del flujo.              |
| ¿Cuánto gano por cada unidad monetaria invertida?       | B/C                               | Mide eficiencia relativa de la inversión.           |
| ¿Cuándo recupero la inversión?                          | PRD                               | Mide tiempo de recuperación con flujos descontados. |

La decisión correcta no consiste solo en elegir el indicador más alto, sino en interpretar cada resultado según el tipo de problema. En proyectos de inversión con beneficios, el **VAN** suele ser el criterio más fiable porque mide la riqueza absoluta creada. La **TIR**, el **B/C** y el **PRD** sirven como indicadores complementarios.

---

## 2. Idea central: valor económico de un proyecto

El valor de una empresa o proyecto no depende únicamente de su precio, de sus activos físicos o de su valor contable. En evaluación de proyectos, el valor se relaciona principalmente con los **flujos de caja futuros** que el proyecto puede generar y con el **riesgo** asociado a esos flujos.

El PDF distingue varias formas de entender el valor:

| Tipo de valor                 | Significado práctico                                       | Uso típico                         |
|-------------------------------|------------------------------------------------------------|------------------------------------|
| Valor nominal                 | Valor facial mostrado por un instrumento financiero.       | Bonos, acciones, títulos.          |
| Valor contable                | Valor neto según el balance.                               | Contabilidad patrimonial.          |
| Valor de liquidación          | Monto neto obtenido al vender activos y pagar acreedores.  | Cierre, quiebra, venta forzada.    |
| Valor de mercado              | Precio que los inversionistas estarían dispuestos a pagar. | Compra, venta, valorización.       |
| Valor de reposición           | Costo de restituir la capacidad operativa actual.          | Reemplazo de activos.              |
| Valor por descuento de flujos | Valor actual de los flujos esperados.                      | Evaluación financiera y económica. |

La evaluación económica se basa principalmente en el **descuento de flujos de caja**, porque permite comparar dinero recibido o pagado en diferentes momentos del tiempo.

---

## 3. Conceptos base antes de calcular indicadores

### 3.1 Flujo de Caja Libre o Flujo de Caja Neto

El **flujo de caja** representa entradas y salidas reales de dinero. Para evaluar un proyecto, no basta con mirar utilidad contable; interesa cuánto efectivo entra o sale en cada periodo.

Una estructura mínima de flujo de caja es:

| Periodo | Concepto                              |        Signo habitual |
|--------:|---------------------------------------|----------------------:|
|       0 | Inversión inicial                     |              Negativo |
|   1 a n | Flujos netos operativos               | Positivos o negativos |
|       n | Valor de salvataje, si existe         |              Positivo |
|   1 a n | Costos, reposiciones o mantenimientos |             Negativos |


### 3.1.1 Presupuesto de caja y tipos de flujo

El **presupuesto de caja** organiza los movimientos de efectivo del proyecto. Para trabajar ejercicios de rentabilidad, conviene separar los flujos según su origen, porque no todos cumplen la misma función dentro de la evaluación.

| Tipo de flujo                  | Qué representa                                                                                              | Ejemplos                                                                                                          | Uso en evaluación                                                    |
|--------------------------------|-------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------|
| Flujo operativo                | Efectivo generado por la actividad principal del negocio.                                                   | Cobros por ventas, pagos a proveedores, gastos operativos, impuestos operativos.                                  | Sirve para estimar la capacidad real del proyecto para generar caja. |
| Flujo de inversión             | Salidas o entradas asociadas a activos y capital de trabajo.                                                | CAPEX, compra de maquinaria, inversión inicial, recuperación o aumento de capital de trabajo, valor de salvataje. | Permite calcular cuánto se invierte y qué recuperaciones existen.    |
| Flujo de caja libre            | Efectivo disponible después de cubrir operación e inversiones, antes de decidir la forma de financiamiento. | Flujo operativo menos inversiones netas.                                                                          | Es la base típica para calcular VAN, TIR, B/C y PRD del proyecto.    |
| Flujo financiero               | Movimientos asociados al financiamiento con terceros.                                                       | Préstamos recibidos, amortización de capital, intereses, cuotas.                                                  | Se usa para evaluar el efecto de la deuda.                           |
| Flujo de fondos del accionista | Efectivo disponible para los socios o accionistas después del financiamiento.                               | Aportes, dividendos, flujo después del servicio de deuda.                                                         | Se usa cuando se evalúa la rentabilidad del accionista.              |

Con una convención sencilla de signos, puede expresarse así:

```text
Flujo de Caja Libre = Flujo Operativo - Inversión Neta
```

Cuando el flujo de inversión ya está registrado con signo negativo, también puede verse como:

```text
Flujo de Caja Libre = Flujo Operativo + Flujo de Inversión
```

La idea importante es que el **flujo de caja libre** muestra el efectivo disponible para remunerar a quienes financian el proyecto. Si todavía no se considera deuda, el flujo pertenece al proyecto como unidad económica. Si luego se resta el servicio de la deuda, se obtiene una aproximación al flujo disponible para el accionista.

### 3.1.2 Esquema mínimo de flujo proyectado

Para que una persona o una IA pueda resolver correctamente un ejercicio, el flujo debe organizarse por periodos:

| Periodo |               Flujo operativo |                             Flujo de inversión | Flujo de caja libre |               Flujo financiero |       Flujo del accionista |
|--------:|------------------------------:|-----------------------------------------------:|--------------------:|-------------------------------:|---------------------------:|
|       0 |                             0 |                             -Inversión inicial |  -Inversión inicial |     Préstamo recibido o aporte | Aporte neto del accionista |
|       1 | Ingresos - egresos operativos |                     CAPEX o capital de trabajo |           FCL año 1 | Cuota, intereses, amortización |            FCL menos deuda |
|       2 | Ingresos - egresos operativos |                     CAPEX o capital de trabajo |           FCL año 2 | Cuota, intereses, amortización |            FCL menos deuda |
|       n |               Operación final | Salvataje o recuperación de capital de trabajo |           FCL final | Pago final de deuda, si existe |  Flujo final al accionista |

Esta separación evita mezclar la rentabilidad económica del proyecto con el efecto del financiamiento.

### 3.2 Costo de Oportunidad del Capital

El **Costo de Oportunidad del Capital (COK)** es la rentabilidad mínima exigida por el inversionista para invertir en un proyecto en lugar de colocar su dinero en una alternativa de riesgo equivalente.

En términos prácticos:

```text
COK = rendimiento mínimo exigido por invertir en este proyecto
```

Debe reflejar:

- El valor del dinero en el tiempo.
- El riesgo del proyecto.
- La mejor alternativa disponible con riesgo comparable.


### 3.2.1 COK según el tipo de financiamiento

El COK debe ser coherente con el flujo que se está descontando. No es lo mismo evaluar el proyecto como si todo fuera financiado con capital propio que evaluarlo con deuda y aportes de accionistas.

| Caso                                        | Tasa recomendada                             | Explicación                                                                                                        | Flujo asociado                                                                 |
|---------------------------------------------|----------------------------------------------|--------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------|
| Proyecto financiado solo con capital propio | COK del accionista o costo de capital propio | Representa la mejor alternativa de inversión que el accionista deja de realizar por invertir en el proyecto.       | Flujo económico del proyecto sin deuda o flujo del accionista si no hay deuda. |
| Proyecto con financiamiento mixto           | WACC o Costo Promedio Ponderado de Capital   | Combina el costo de la deuda y el costo del capital propio según su peso en la estructura de financiamiento.       | Flujo de caja libre del proyecto antes del financiamiento.                     |
| Evaluación del accionista con deuda         | Costo de oportunidad del accionista          | Mide si el dinero propio invertido por el accionista recibe una rentabilidad suficiente después de pagar la deuda. | Flujo de fondos del accionista.                                                |

Fórmula general del WACC cuando se considera impuesto a la renta:

$$
WACC = \frac{E}{D+E}K_e + \frac{D}{D+E}K_d(1-T)
$$

Donde:

| Símbolo | Significado                            |
|---------|----------------------------------------|
| $E$     | Valor del capital propio o patrimonio. |
| $D$     | Valor de la deuda.                     |
| $K_e$   | Costo del capital propio.              |
| $K_d$   | Costo de la deuda.                     |
| $T$     | Tasa de impuesto, si aplica.           |

Si el ejercicio no menciona impuestos, puede trabajarse una versión simplificada sin el ajuste tributario:

$$
WACC = \frac{E}{D+E}K_e + \frac{D}{D+E}K_d
$$

### 3.3 Regla de consistencia

Al construir el flujo de caja:

| Criterio              | Recomendación                                                                                   |
|-----------------------|-------------------------------------------------------------------------------------------------|
| Flujo relevante       | Usar flujos de caja, no utilidades contables.                                                   |
| Base incremental      | Incluir solo ingresos y egresos que cambian por aceptar el proyecto.                            |
| Inflación             | No mezclar flujos nominales con tasa real, ni flujos reales con tasa nominal.                   |
| Capital de trabajo    | Incluir necesidades de fondo de maniobra cuando correspondan.                                   |
| Costos de oportunidad | Incluir el valor de recursos que podrían usarse en otra alternativa.                            |
| Gastos generales      | Asignarlos solo si realmente son incrementales.                                                 |
| Costos hundidos       | No deben decidir el proyecto si ya ocurrieron y no cambian por aceptar o rechazar la inversión. |

---

# PARTE I: INDICADORES DE RENTABILIDAD

---

## 4. Resumen general de indicadores

| Indicador | Nombre completo                    | Qué mide                                                                                                | Criterio de aceptación | Mejor cuando...                                    |
|-----------|------------------------------------|---------------------------------------------------------------------------------------------------------|------------------------|----------------------------------------------------|
| VAN       | Valor Actual Neto                  | Valor monetario creado por el proyecto. Responde: ¿cuánto dinero gano hoy?                              | VAN > 0                | Se quiere maximizar riqueza.                       |
| TIR       | Tasa Interna de Retorno            | Rentabilidad porcentual implícita. Responde: ¿qué % gano?                                               | TIR > COK              | Se necesita expresar rentabilidad como porcentaje. |
| B/C       | Relación Beneficio-Costo           | Valor actual generado por cada unidad invertida. Responde: ¿cuánto gano por cada sol?                   | B/C > 1                | Se quiere comparar eficiencia relativa.            |
| PRD       | Periodo de Recuperación Descontado | Tiempo necesario para recuperar la inversión considerando valor del dinero. Responde: ¿cuándo recupero? | Menor PRD es mejor     | Se evalúa liquidez o rapidez de recuperación.      |

---

## 5. Valor Actual Neto (VAN)

### 5.1 Definición

El **Valor Actual Neto (VAN)** es el valor actual de los flujos de caja libres o netos que genera un proyecto, descontados al COK, menos la inversión inicial.

Mide cuánto más rico sería el inversionista si realiza el proyecto en lugar de invertir su dinero en una alternativa de rentabilidad equivalente al COK.

### 5.2 Fórmula

$$
VAN = -I_0 + \sum_{t=1}^{n} \frac{FC_t}{(1+COK)^t}
$$

Donde:

| Símbolo | Significado                                           |
|---------|-------------------------------------------------------|
| $I_0$   | Inversión inicial en el periodo 0.                    |
| $FC_t$  | Flujo de caja del periodo $t$.                        |
| $COK$   | Costo de oportunidad del capital o tasa de descuento. |
| $n$     | Vida útil u horizonte de evaluación.                  |


### 5.2.1 Lectura práctica y fórmula en Excel

El VAN responde la pregunta: **¿cuánto dinero gano hoy, después de recuperar la inversión y exigir la rentabilidad mínima del COK?**

Forma conceptual:

```text
VAN = VNA(tasa, FC1, FC2, ..., FCn) - Inversión inicial
```

En Excel o Google Sheets, si la tasa está en `B1`, la inversión positiva en `B2` y los flujos futuros en `B3:B6`:

```excel
=VNA(B1,B3:B6)-B2
```

Si se registra la inversión inicial como flujo negativo en el periodo 0 dentro del mismo rango, por ejemplo `B2:B6`, se usa:

```excel
=VNA(B1,B3:B6)+B2
```

La función `VNA` descuenta solo los flujos futuros. Por eso la inversión del periodo 0 debe sumarse o restarse por separado.

### 5.3 Construcción del VAN

1. Estimar los flujos de caja libres del proyecto durante su vida útil.
2. Incluir el valor de salvataje si existe.
3. Determinar el COK.
4. Descontar cada flujo al periodo 0.
5. Sumar los valores actuales de los flujos.
6. Restar la inversión inicial.

### 5.4 Criterio de decisión

| Resultado | Interpretación                                          | Decisión                                                   |
|-----------|---------------------------------------------------------|------------------------------------------------------------|
| VAN > 0   | El proyecto genera valor por encima del COK.            | Aceptar.                                                   |
| VAN < 0   | El proyecto no cubre la rentabilidad exigida.           | Rechazar.                                                  |
| VAN = 0   | El proyecto iguala exactamente la rentabilidad exigida. | Aceptar, postergar o quedar indiferente según el contexto. |

> **Nota — préstamos desde la perspectiva del deudor:** en este sistema el VAN también se aplica a un préstamo visto por el deudor, donde el préstamo recibido es una **entrada** en el período 0 y las cuotas son **salidas**. En ese caso `VAN = Préstamo − Σ Cuota_t / (1 + COK)^t`, y **VAN > 0 significa que el préstamo es barato** frente al COK (conviene endeudarse), interpretación opuesta a la de un proyecto de inversión. Ver `van-tir.md` (§7) y `metodo-frances.md`.

### 5.5 Ventajas y desventajas

| Aspecto                  | Explicación                                                                        |
|--------------------------|------------------------------------------------------------------------------------|
| Ventaja principal        | Considera el valor del dinero en el tiempo.                                        |
| Ventaja adicional        | Ayuda a elegir entre proyectos excluyentes porque mide creación de valor absoluta. |
| Desventaja               | Requiere estimar correctamente el COK.                                             |
| Riesgo de interpretación | El VAN es un monto monetario, no una tasa porcentual.                              |

---

## 6. Tasa Interna de Retorno (TIR)

### 6.1 Definición

La **Tasa Interna de Retorno (TIR)** es la tasa de descuento que hace que el VAN sea igual a cero. Representa la rentabilidad promedio del capital que permanece invertido en el proyecto.

### 6.2 Fórmula conceptual

$$
0 = -I_0 + \sum_{t=1}^{n} \frac{FC_t}{(1+TIR)^t}
$$

La TIR no se despeja fácilmente cuando hay varios periodos. Normalmente se calcula con hoja de cálculo, calculadora financiera o método numérico.


### 6.2.1 Lectura práctica y fórmula en Excel

La TIR responde la pregunta: **¿qué porcentaje de rentabilidad genera el proyecto según sus propios flujos?**

Forma conceptual:

```text
TIR(-Inversión, FC1, FC2, ..., FCn)
```

En Excel o Google Sheets, lo más recomendable es colocar toda la serie de flujos en un rango, incluyendo la inversión inicial con signo negativo. Por ejemplo, si `B2:B6` contiene `-I0, FC1, FC2, FC3, FC4`:

```excel
=TIR(B2:B6)
```

En inglés:

```excel
=IRR(B2:B6)
```

La TIR es útil, pero no debe reemplazar automáticamente al VAN cuando se comparan proyectos mutuamente excluyentes, especialmente si tienen distinta inversión inicial, distinta vida útil o distinta distribución temporal de beneficios.

### 6.3 Criterio de decisión

| Resultado | Interpretación                                      | Decisión                                                   |
|-----------|-----------------------------------------------------|------------------------------------------------------------|
| TIR > COK | La rentabilidad del proyecto supera la exigida.     | Aceptar.                                                   |
| TIR < COK | La rentabilidad del proyecto no alcanza la exigida. | Rechazar.                                                  |
| TIR = COK | El proyecto iguala la rentabilidad mínima.          | Aceptar, postergar o quedar indiferente según el contexto. |

### 6.4 Tipos de TIR

| Tipo           | Significado                                                                                             |
|----------------|---------------------------------------------------------------------------------------------------------|
| TIR económica  | Rentabilidad promedio de todo el capital invertido, suponiendo que todo se financia con capital propio. |
| TIR financiera | Rentabilidad considerando financiamiento, servicio de deuda y distribución de dividendos.               |

### 6.5 Ventajas y desventajas

| Aspecto    | Explicación                                                                       |
|------------|-----------------------------------------------------------------------------------|
| Ventaja    | Expresa la rentabilidad como porcentaje, por lo que suele ser fácil de comunicar. |
| Ventaja    | Complementa al VAN.                                                               |
| Desventaja | Puede contradecir al VAN en proyectos excluyentes.                                |
| Desventaja | Puede generar múltiples resultados si los flujos no son convencionales.           |
| Desventaja | No siempre refleja creación absoluta de valor.                                    |

### 6.6 Flujo convencional y no convencional

| Tipo de flujo   | Patrón típico                      | Riesgo al usar TIR                                   |
|-----------------|------------------------------------|------------------------------------------------------|
| Convencional    | Primero negativo, luego positivos. | Normalmente produce una sola TIR.                    |
| No convencional | Cambios de signo múltiples.        | Puede producir más de una TIR o resultados ambiguos. |

Ejemplo de flujo convencional:

```text
Periodo:  0      1      2      3
Flujo:   -I     +FC    +FC    +FC
```

Ejemplo de flujo no convencional:

```text
Periodo:  0      1      2      3
Flujo:   -I     +FC    -FC    +FC
```

---

## 7. Relación Beneficio-Costo (B/C)

### 7.1 Definición

La **Relación Beneficio-Costo (B/C)** se calcula dividiendo el valor actual de los flujos de caja esperados entre el valor de la inversión.

Mide cuánto se genera por cada unidad monetaria invertida.

### 7.2 Fórmula

$$
B/C = \frac{VA}{I_0}
$$

Donde:

$$
VA = \sum_{t=1}^{n} \frac{FC_t}{(1+COK)^t}
$$


### 7.2.1 Lectura práctica y fórmula en Excel

El B/C responde la pregunta: **¿cuántas unidades monetarias a valor presente genero por cada unidad monetaria invertida?**

Forma conceptual:

```text
B/C = VNA(tasa, FC1, FC2, ..., FCn) / Inversión inicial
```

En Excel o Google Sheets, si la tasa está en `B1`, la inversión positiva en `B2` y los flujos futuros en `B3:B6`:

```excel
=VNA(B1,B3:B6)/B2
```

Interpretación rápida:

```text
B/C = 1.20 significa que por cada S/ 1.00 invertido se generan S/ 1.20 a valor presente.
```

### 7.3 Criterio de decisión

| Resultado | Interpretación                                            | Decisión                                                   |
|-----------|-----------------------------------------------------------|------------------------------------------------------------|
| B/C > 1   | Los beneficios actualizados superan a la inversión.       | Aceptar.                                                   |
| B/C < 1   | Los beneficios actualizados son menores que la inversión. | Rechazar.                                                  |
| B/C = 1   | Beneficios e inversión son equivalentes.                  | Aceptar, postergar o quedar indiferente según el contexto. |

### 7.4 Desventaja principal

El B/C puede ser útil para ordenar alternativas, pero no toma en cuenta por sí solo la magnitud absoluta de la inversión. Un proyecto pequeño puede tener B/C alto y generar poco valor monetario, mientras que uno grande puede tener B/C menor y crear más riqueza total.

---

## 8. Periodo de Recuperación Descontado (PRD)

### 8.1 Definición

El **Periodo de Recuperación Descontado (PRD)** es el tiempo necesario para recuperar la inversión inicial usando flujos de caja descontados.

No mide rentabilidad directamente. Más bien mide la rapidez con que se recupera la inversión, considerando el valor del dinero en el tiempo.

### 8.2 Procedimiento

1. Descontar cada flujo de caja al periodo 0.
2. Acumular los flujos descontados.
3. Identificar el periodo en el que la acumulación iguala o supera la inversión.
4. Si la recuperación ocurre dentro de un periodo, interpolar.

### 8.3 Fórmula de interpolación

$$
PRD = a + \frac{I_0 - FAcum_a}{FDesc_{a+1}}
$$

Donde:

| Símbolo       | Significado                                      |
|---------------|--------------------------------------------------|
| $a$           | Último periodo antes de recuperar la inversión.  |
| $FAcum_a$     | Flujo acumulado descontado hasta el periodo $a$. |
| $FDesc_{a+1}$ | Flujo descontado del periodo siguiente.          |


Otra forma práctica de escribirlo, cuando se trabaja con acumulados negativos, es:

$$
PRD = \text{Último periodo negativo} + \frac{\text{Monto acumulado negativo pendiente}}{\text{Flujo descontado del siguiente periodo}}
$$

En esta fórmula, el numerador representa cuánto falta recuperar al cierre del último periodo negativo; el denominador representa cuánto aporta el flujo descontado del periodo siguiente. Por eso el resultado suele tener una parte entera y una fracción de periodo.

### 8.4 Criterio de decisión

Mientras menor sea el PRD, mejor será el proyecto desde una perspectiva de recuperación de capital y exposición al riesgo. Sin embargo, debe usarse como indicador auxiliar, no como criterio único.

### 8.5 Desventajas

| Desventaja                    | Explicación                                                           |
|-------------------------------|-----------------------------------------------------------------------|
| Ignora beneficios posteriores | No considera lo que ocurre después del periodo de recuperación.       |
| Puede ser inmediatista        | Favorece recuperar rápido, aunque el proyecto no sea el más rentable. |
| No mide rentabilidad total    | Indica cuándo se recupera el capital, no cuánto valor se crea.        |


### 8.6 Lectura conjunta de VAN, TIR y B/C

En proyectos simples con flujos convencionales, los tres criterios principales suelen apuntar hacia la misma decisión:

| Situación                                   |     VAN |       TIR |     B/C | Decisión general                                           |
|---------------------------------------------|--------:|----------:|--------:|------------------------------------------------------------|
| El proyecto crea valor                      | VAN > 0 | TIR > COK | B/C > 1 | Aceptar.                                                   |
| El proyecto solo iguala la exigencia mínima | VAN = 0 | TIR = COK | B/C = 1 | Aceptar, postergar o quedar indiferente según el contexto. |
| El proyecto destruye valor                  | VAN < 0 | TIR < COK | B/C < 1 | Rechazar.                                                  |

La jerarquía recomendada es usar el **VAN como criterio principal**, porque expresa valor absoluto creado. La TIR y el B/C ayudan a interpretar rentabilidad porcentual y eficiencia relativa, pero pueden inducir a error si se comparan proyectos excluyentes con distinta escala o temporalidad.

---

# PARTE II: EJEMPLO INTEGRAL DE VAN, TIR, B/C Y PRD

---

## 9. Ejemplo resuelto: proyecto con flujos anuales

### 9.1 Datos

Una empresa evalúa un proyecto con los siguientes datos:

| Dato                     |      Valor |
|--------------------------|-----------:|
| Inversión inicial        | S/ 100,000 |
| COK                      |  12% anual |
| Vida útil                |     4 años |
| Flujo año 1              |  S/ 30,000 |
| Flujo año 2              |  S/ 35,000 |
| Flujo año 3              |  S/ 40,000 |
| Flujo año 4              |  S/ 45,000 |
| Valor de salvataje año 4 |  S/ 10,000 |
| Flujo total año 4        |  S/ 55,000 |

### 9.2 Flujo de caja

| Periodo | Flujo de caja | Factor de descuento 12% | Flujo descontado |
|--------:|--------------:|------------------------:|-----------------:|
|       0 |   -100,000.00 |                  1.0000 |      -100,000.00 |
|       1 |     30,000.00 |                  0.8929 |        26,785.71 |
|       2 |     35,000.00 |                  0.7972 |        27,901.79 |
|       3 |     40,000.00 |                  0.7118 |        28,471.21 |
|       4 |     55,000.00 |                  0.6355 |        34,953.49 |

### 9.3 VAN

$$
VAN = -100000 + 26785.71 + 27901.79 + 28471.21 + 34953.49
$$

$$
VAN = 18112.20
$$

**Interpretación:** el proyecto crea S/ 18,112.20 por encima de la rentabilidad exigida del 12%. Por VAN, el proyecto se acepta.

### 9.4 TIR

$$
0 = -100000 + \frac{30000}{(1+TIR)^1} + \frac{35000}{(1+TIR)^2} + \frac{40000}{(1+TIR)^3} + \frac{55000}{(1+TIR)^4}
$$

Resultado aproximado:

```text
TIR = 19.51%
```

Como la TIR es mayor que el COK de 12%, el proyecto se acepta.

### 9.5 Relación B/C

$$
B/C = \frac{118112.20}{100000}
$$

$$
B/C = 1.1811
$$

**Interpretación:** por cada S/ 1.00 invertido, el proyecto genera S/ 1.1811 en valor actual. Como B/C > 1, se acepta.

### 9.6 Periodo de Recuperación Descontado

| Periodo | Flujo descontado | Acumulado descontado |
|--------:|-----------------:|---------------------:|
|       1 |        26,785.71 |            26,785.71 |
|       2 |        27,901.79 |            54,687.50 |
|       3 |        28,471.21 |            83,158.71 |
|       4 |        34,953.49 |           118,112.20 |

La inversión se recupera entre los años 3 y 4.

$$
PRD = 3 + \frac{100000 - 83158.71}{34953.49}
$$

$$
PRD = 3.48 \text{ años}
$$

**Interpretación:** la inversión se recupera aproximadamente en 3.48 años usando flujos descontados.

### 9.7 Decisión integral

| Indicador |    Resultado | Criterio       | Decisión                            |
|-----------|-------------:|----------------|-------------------------------------|
| VAN       | S/ 18,112.20 | VAN > 0        | Aceptar                             |
| TIR       |       19.51% | TIR > 12%      | Aceptar                             |
| B/C       |       1.1811 | B/C > 1        | Aceptar                             |
| PRD       |    3.48 años | Menor es mejor | Recuperación dentro de la vida útil |

**Conclusión:** el proyecto es rentable y puede aceptarse.


---

## 9A. Tasa de Fisher o tasa de indiferencia

### 9A.1 Definición

La **Tasa de Fisher**, también llamada **tasa de indiferencia**, es la tasa de descuento exacta en la que el VAN de dos proyectos mutuamente excluyentes se iguala. En ese punto, desde el criterio VAN, da igual elegir cualquiera de los dos proyectos.

En un gráfico VAN vs. COK, la Tasa de Fisher es el punto donde se cruzan las curvas de VAN de ambos proyectos.

### 9A.2 Cuándo aparece y para qué sirve

La Tasa de Fisher es útil cuando dos proyectos tienen:

| Condición                                 | Efecto                                                              |
|-------------------------------------------|---------------------------------------------------------------------|
| Diferente escala de inversión             | Un proyecto exige mayor inversión inicial que el otro.              |
| Diferente distribución temporal de flujos | Un proyecto genera más flujos al inicio y otro más flujos al final. |
| Decisión mutuamente excluyente            | Solo puede elegirse una alternativa.                                |
| Sensibilidad al COK                       | La decisión puede cambiar si cambia la tasa de descuento.           |

Su objetivo es evitar una elección frágil. Si el COK está muy cerca de la Tasa de Fisher, un pequeño cambio en la tasa de mercado puede modificar la alternativa preferida.

### 9A.3 Cómo se calcula

La Tasa de Fisher se obtiene calculando la **TIR del flujo diferencial o incremental** entre dos proyectos.

Si se comparan los proyectos A y B:

$$
Flujo\ diferencial_t = FC_{A,t} - FC_{B,t}
$$

Luego se calcula la tasa que hace cero el VAN diferencial:

$$
0 = \sum_{t=0}^{n} \frac{FC_{A,t} - FC_{B,t}}{(1+TF)^t}
$$

Esa tasa $TF$ es la Tasa de Fisher.

En Excel o Google Sheets:

```excel
=TIR(rango_de_flujos_diferenciales)
```

### 9A.4 Regla de decisión

Cuando el flujo diferencial se construye como:

```text
Proyecto de mayor inversión - Proyecto de menor inversión
```

la regla práctica es:

| Comparación          | Decisión típica                        | Justificación                                                        |
|----------------------|----------------------------------------|----------------------------------------------------------------------|
| COK < Tasa de Fisher | Elegir el proyecto de mayor inversión. | A tasas bajas, el VAN premia más la magnitud de los flujos futuros.  |
| COK > Tasa de Fisher | Elegir el proyecto de menor inversión. | A tasas altas, pesan más la inversión inicial y los flujos cercanos. |
| COK = Tasa de Fisher | Indiferente entre ambos.               | Ambos tienen el mismo VAN.                                           |

Esta regla debe aplicarse verificando siempre los VAN de ambos proyectos, porque la Tasa de Fisher solo indica el punto de cruce, no reemplaza el análisis de creación de valor.

### 9A.5 Ejemplo: proyectos Aranis y Casero

Se comparan dos proyectos mutuamente excluyentes:

| Periodo | Flujo Aranis | Flujo Casero | Flujo diferencial Aranis - Casero |
|--------:|-------------:|-------------:|----------------------------------:|
|       0 |       -6,000 |       -5,000 |                            -1,000 |
|       1 |        1,200 |        1,200 |                                 0 |
|       2 |        1,300 |        1,200 |                               100 |
|       3 |        1,400 |        1,200 |                               200 |
|       4 |        1,500 |        1,200 |                               300 |
|       5 |        1,600 |        1,200 |                               400 |
|       6 |        1,600 |        1,200 |                               400 |

La TIR del flujo diferencial es:

```text
Tasa de Fisher = 7.74% aproximadamente
```

Esto significa que a una tasa de descuento de 7.74%, el VAN de Aranis y el VAN de Casero son iguales.

### 9A.6 Análisis de sensibilidad del COK

|    COK | VAN Aranis | VAN Casero | Diferencia Aranis - Casero | Decisión por VAN             |
|-------:|-----------:|-----------:|---------------------------:|------------------------------|
|  0.00% |   2,600.00 |   2,200.00 |                     400.00 | Aranis                       |
|  1.00% |   2,292.42 |   1,954.57 |                     337.85 | Aranis                       |
|  2.00% |   2,000.93 |   1,721.72 |                     279.22 | Aranis                       |
|  3.00% |   1,724.50 |   1,500.63 |                     223.87 | Aranis                       |
|  4.00% |   1,462.16 |   1,290.56 |                     171.59 | Aranis                       |
|  5.00% |   1,213.01 |   1,090.83 |                     122.18 | Aranis                       |
|  6.00% |     976.23 |     900.79 |                      75.44 | Aranis                       |
|  7.00% |     751.05 |     719.85 |                      31.20 | Aranis                       |
|  8.00% |     536.77 |     547.46 |                     -10.69 | Casero                       |
|  9.00% |     332.71 |     383.10 |                     -50.39 | Casero                       |
| 10.00% |     138.28 |     226.31 |                     -88.03 | Casero                       |
| 11.00% |     -47.10 |      76.65 |                    -123.74 | Casero                       |
| 12.00% |    -223.96 |     -66.31 |                    -157.65 | Ninguno, si se exige VAN > 0 |
| 13.00% |    -392.79 |    -202.94 |                    -189.85 | Ninguno, si se exige VAN > 0 |
| 14.00% |    -554.05 |    -333.60 |                    -220.45 | Ninguno, si se exige VAN > 0 |
| 15.00% |    -708.18 |    -458.62 |                    -249.55 | Ninguno, si se exige VAN > 0 |

### 9A.7 Interpretación del ejemplo

Si el COK es 8%, se elige **Casero**, porque tiene mayor VAN:

```text
VAN Casero = 547.46
VAN Aranis = 536.77
```

Si el COK baja a 7%, la decisión cambia. Se elige **Aranis**, porque:

```text
COK 7% < Tasa de Fisher 7.74%
VAN Aranis = 751.05
VAN Casero = 719.85
```

La conclusión es que la decisión es sensible al COK. El proyecto Aranis exige mayor inversión inicial, pero genera flujos posteriores más grandes. Por eso conviene cuando la tasa de descuento es baja. Casero exige menor inversión inicial y se vuelve preferible cuando la tasa sube por encima de la Tasa de Fisher.

### 9A.8 Procedimiento general para calcular Fisher

```text
1. Ordenar los flujos de ambos proyectos por periodo.
2. Identificar cuál tiene mayor inversión inicial.
3. Construir el flujo diferencial: mayor inversión - menor inversión.
4. Calcular la TIR del flujo diferencial.
5. Interpretar esa TIR como Tasa de Fisher.
6. Comparar el COK con la Tasa de Fisher.
7. Confirmar la decisión calculando el VAN de cada proyecto.
```


---

# PARTE III: ANÁLISIS DE REEMPLAZO O ELECCIÓN DE ACTIVOS

---

## 10. Cuándo usar análisis de reemplazo

El análisis de reemplazo se usa cuando se comparan alternativas que cumplen una misma función, pero tienen diferentes costos, vidas útiles, salvatajes o gastos de operación.

En estos casos, no siempre se evalúan ingresos, sino principalmente **costos**. Por eso, la regla cambia:

```text
Si se comparan costos, se elige la alternativa de menor costo equivalente.
```

El PDF trabaja cuatro criterios principales:

| Criterio | Nombre completo                       | Uso principal                                           |
|----------|---------------------------------------|---------------------------------------------------------|
| VAC      | Valor Actual de Costos                | Comparar alternativas con igual vida útil.              |
| CAUE     | Costo Anual Uniforme Equivalente      | Comparar alternativas con vidas útiles diferentes.      |
| MCM      | Mínimo Común Múltiplo de vidas útiles | Homogeneizar horizontes mediante repetición de ciclos.  |
| CC       | Costo Capitalizado                    | Evaluar alternativas de duración indefinida o perpetua. |

---

## 11. Valor Actual de Costos (VAC)

### 11.1 Definición

El **Valor Actual de Costos (VAC)** trae al periodo 0 todos los costos asociados a una alternativa. Se elige la alternativa con menor VAC.

### 11.2 Fórmula general

$$
VAC = I_0 + \sum_{t=1}^{n} \frac{C_t}{(1+i)^t} - \frac{S_n}{(1+i)^n}
$$

Donde:

| Símbolo | Significado                                                       |
|---------|-------------------------------------------------------------------|
| $I_0$   | Costo inicial.                                                    |
| $C_t$   | Costo de operación, mantenimiento u otros costos del periodo $t$. |
| $S_n$   | Valor de salvataje al final de la vida útil.                      |
| $i$     | Tasa de descuento.                                                |
| $n$     | Vida útil.                                                        |

### 11.3 Criterio

| Comparación   | Decisión              |
|---------------|-----------------------|
| VAC A < VAC B | Elegir alternativa A. |
| VAC B < VAC A | Elegir alternativa B. |

---

## 12. Ejemplo de VAC: elección de máquina

### 12.1 Datos

Una empresa evalúa dos máquinas con vida útil de 10 años y TEA de 11%.

| Dato                     |   Máquina 1 |   Máquina 2 |
|--------------------------|------------:|------------:|
| Costo inicial            | US\$ 50,000 | US\$ 65,000 |
| Costo anual de operación |  US\$ 3,000 |    US\$ 500 |
| Valor de salvataje       |  US\$ 5,000 | US\$ 10,000 |
| Vida útil                |     10 años |     10 años |
| Tasa                     |         11% |         11% |

### 12.2 Cálculo

$$
VAC = I_0 + C \left(\frac{1-(1+i)^{-n}}{i}\right) - \frac{S}{(1+i)^n}
$$

| Alternativa | VAC aproximado |
|-------------|---------------:|
| Máquina 1   | US\$ 65,906.77 |
| Máquina 2   | US\$ 64,422.77 |

### 12.3 Decisión

Se elige la **Máquina 2**, porque tiene menor valor actual de costos.

---

## 13. Costo Anual Uniforme Equivalente (CAUE)

### 13.1 Definición

El **CAUE** convierte el costo actual de una alternativa en una anualidad equivalente. Se usa especialmente cuando las alternativas tienen vidas útiles distintas.

Primero se calcula el VAC. Luego se transforma ese valor en un costo anual uniforme.

### 13.2 Fórmula

$$
CAUE = VAC \cdot \frac{i(1+i)^n}{(1+i)^n - 1}
$$

Donde:

| Símbolo | Significado             |
|---------|-------------------------|
| $VAC$   | Valor actual de costos. |
| $i$     | Tasa de descuento.      |
| $n$     | Vida útil.              |

### 13.3 Criterio

| Situación                    | Decisión           |
|------------------------------|--------------------|
| Si son costos                | Elegir menor CAUE. |
| Si son ingresos o beneficios | Elegir mayor CAUE. |

---

## 14. Ejemplo de CAUE: equipos con vidas distintas

### 14.1 Datos

Se comparan dos equipos con TEA de 14%.

| Dato                 |    Equipo 1 |    Equipo 2 |
|----------------------|------------:|------------:|
| Costo inicial        | US\$ 80,000 | US\$ 90,000 |
| Mantenimiento anual  |  US\$ 5,000 |  US\$ 3,000 |
| Mano de obra anual   | US\$ 25,000 | US\$ 15,000 |
| Otros costos anuales |      US\$ 0 |  US\$ 5,000 |
| Costo anual total    | US\$ 30,000 | US\$ 23,000 |
| Salvataje            | US\$ 10,000 | US\$ 20,000 |
| Vida útil            |      4 años |      7 años |

### 14.2 Resultados

| Alternativa |  VAC aproximado | CAUE aproximado |
|-------------|----------------:|----------------:|
| Equipo 1    | US\$ 161,490.57 |  US\$ 55,424.33 |
| Equipo 2    | US\$ 180,638.26 |  US\$ 42,123.47 |

### 14.3 Decisión

Aunque el Equipo 2 tiene mayor VAC, su vida útil es más larga. Al anualizar el costo mediante CAUE, el Equipo 2 resulta más conveniente porque tiene menor costo anual equivalente.

**Decisión:** comprar el **Equipo 2**.

---

## 15. Mínimo Común Múltiplo (MCM) de vidas útiles

### 15.1 Definición

El método del **MCM** busca comparar alternativas con vidas útiles distintas usando un horizonte común. Para ello, se repiten los ciclos de compra, operación y salvataje hasta llegar al mínimo común múltiplo de sus vidas útiles.

### 15.2 Cuándo usarlo

Se usa cuando:

- Las alternativas tienen vidas útiles distintas.
- Se asume que las alternativas pueden renovarse bajo condiciones similares.
- Se quiere comparar en un horizonte común.

### 15.3 Ejemplo conceptual

Si una alternativa dura 3 años y otra dura 4 años:

```text
MCM(3, 4) = 12 años
```

Entonces:

| Alternativa | Vida útil | Repeticiones en 12 años |
|-------------|----------:|------------------------:|
| A           |    3 años |                4 ciclos |
| B           |    4 años |                3 ciclos |

### 15.4 Ejemplo de MCM: máquinas con vida de 2 y 3 años

Datos:

| Dato          |  Máquina 1 |  Máquina 2 |
|---------------|-----------:|-----------:|
| Costo inicial | US\$ 5,000 | US\$ 7,500 |
| Costo anual   |   US\$ 200 |   US\$ 100 |
| Salvataje     |   US\$ 100 | US\$ 1,000 |
| Vida útil     |     2 años |     3 años |
| Tasa          |        18% |        18% |

Horizonte común:

$$
MCM(2,3) = 6 \text{ años}
$$

Resultados aproximados al traer todos los ciclos a valor actual:

| Alternativa | Costo actualizado en 6 años |
|-------------|----------------------------:|
| Máquina 1   |              US\$ 11,708.95 |
| Máquina 2   |              US\$ 11,435.43 |

**Decisión:** elegir la **Máquina 2**, porque tiene menor costo actualizado en el horizonte común.

---

## 16. Costo Capitalizado (CC)

### 16.1 Definición

El **Costo Capitalizado (CC)** se usa cuando el proyecto o alternativa se considera indefinida, perpetua o de duración muy larga. Aplica la lógica de perpetuidades.

### 16.2 Fórmula básica de perpetuidad

Si existe un costo anual constante $R$ indefinido:

$$
CC = \frac{R}{i}
$$

Si el costo empieza en un periodo futuro, debe descontarse al presente.

### 16.3 Componentes frecuentes

| Componente                | Tratamiento                                 |
|---------------------------|---------------------------------------------|
| Inversión inicial         | Se coloca directamente en t = 0.            |
| Costos anuales temporales | Se descuentan como anualidad finita.        |
| Costos anuales perpetuos  | Se tratan como perpetuidad.                 |
| Mantenimiento periódico   | Se trata como serie periódica indefinida.   |
| Capital de trabajo futuro | Se descuenta al presente.                   |
| Salvataje                 | Se trabaja por separado cuando corresponda. |

### 16.4 Mantenimiento periódico indefinido

Si cada $k$ años se incurre en un costo $M$, su valor presente como serie indefinida es:

$$
VP = \frac{M}{(1+i)^k - 1}
$$

---

## 17. Ejemplo de Costo Capitalizado

### 17.1 Datos

Un proyecto requiere:

| Concepto                               |      Valor |
|----------------------------------------|-----------:|
| Inversión inicial                      | S/ 500,000 |
| Costos anuales años 1 a 7              |  S/ 10,000 |
| Costos anuales desde año 8 en adelante |  S/ 25,000 |
| Capital de trabajo al final del año 5  | S/ 100,000 |
| Mantenimiento cada 15 años             |  S/ 50,000 |
| Tasa de descuento                      |    25% TEA |

### 17.2 Modelo de cálculo

$$
CC = 500000 + VP(10000 \text{ de año 1 a 7}) + VP(25000 \text{ desde año 8 a perpetuidad}) + \frac{100000}{(1.25)^5} + \frac{50000}{(1.25)^{15}-1}
$$

Resultado aproximado:

$$
CC = S/ 587174.28
$$

### 17.3 Interpretación

El proyecto tiene un costo capitalizado de **S/ 587,174.28**. Este valor representa el costo presente equivalente de sostener indefinidamente el proyecto bajo las condiciones dadas.

---

# PARTE IV: CÓMO TRABAJAR LOS EJERCICIOS

---

## 18. Procedimiento general para resolver un problema

### 18.1 Si el proyecto genera beneficios

Usar principalmente VAN, TIR, B/C y PRD.

```text
1. Identificar inversión inicial.
2. Construir flujo de caja por periodo.
3. Definir COK.
4. Descontar flujos.
5. Calcular VAN.
6. Calcular TIR.
7. Calcular B/C.
8. Calcular PRD si se pide recuperación.
9. Emitir decisión económica.
```

### 18.2 Si se comparan alternativas de costo

Usar VAC, CAUE, MCM o CC según el caso.

```text
1. Identificar si las alternativas generan ingresos o solo costos.
2. Revisar si tienen igual o distinta vida útil.
3. Si tienen igual vida útil, usar VAC.
4. Si tienen distinta vida útil, usar CAUE o MCM.
5. Si una alternativa es indefinida, usar CC.
6. Elegir la alternativa de menor costo equivalente.
```

---

## 19. Selección rápida del método

| Situación del problema                             | Método recomendado | Regla de decisión                             |
|----------------------------------------------------|--------------------|-----------------------------------------------|
| Proyecto con inversión y flujos netos              | VAN                | Aceptar si VAN > 0.                           |
| Se pide rentabilidad porcentual                    | TIR                | Aceptar si TIR > COK.                         |
| Se pide rendimiento por unidad invertida           | B/C                | Aceptar si B/C > 1.                           |
| Se pide tiempo de recuperación                     | PRD                | Menor PRD es mejor.                           |
| Alternativas con igual vida útil y solo costos     | VAC                | Elegir menor VAC.                             |
| Alternativas con distinta vida útil                | CAUE               | Elegir menor CAUE.                            |
| Alternativas renovables con vidas distintas        | MCM                | Elegir menor valor actual en horizonte común. |
| Alternativa indefinida o perpetua                  | CC                 | Elegir menor costo capitalizado.              |
| Dos proyectos excluyentes con posible cruce de VAN | Tasa de Fisher     | Identificar desde qué COK cambia la decisión. |

---

## 20. Plantilla de datos para resolver con IA o software

Una IA o programa puede representar el problema así:

```yaml
tipo_problema: "evaluacion_proyecto"
moneda: "S/"
tasa_descuento: 0.12
periodos: 4
inversion_inicial: 100000
flujos:
  - periodo: 1
    flujo: 30000
  - periodo: 2
    flujo: 35000
  - periodo: 3
    flujo: 40000
  - periodo: 4
    flujo: 55000
indicadores_requeridos:
  - VAN
  - TIR
  - BC
  - PRD
```

Para alternativas de reemplazo:

```yaml
tipo_problema: "analisis_reemplazo"
tasa_descuento: 0.11
criterio: "VAC"
alternativas:
  - nombre: "Maquina 1"
    costo_inicial: 50000
    costo_anual: 3000
    salvataje: 5000
    vida_util: 10
  - nombre: "Maquina 2"
    costo_inicial: 65000
    costo_anual: 500
    salvataje: 10000
    vida_util: 10
regla_decision: "elegir_menor_costo"
```

---

## 21. Pseudocódigo de cálculo

### 21.1 VAN

```text
funcion calcular_VAN(inversion, flujos, tasa):
    VAN = -inversion
    para cada flujo en flujos:
        t = flujo.periodo
        VAN = VAN + flujo.monto / (1 + tasa)^t
    retornar VAN
```

### 21.2 B/C

```text
funcion calcular_BC(inversion, flujos, tasa):
    VA = 0
    para cada flujo en flujos:
        VA = VA + flujo.monto / (1 + tasa)^flujo.periodo
    BC = VA / inversion
    retornar BC
```

### 21.3 PRD

```text
funcion calcular_PRD(inversion, flujos, tasa):
    acumulado = 0
    para cada flujo en flujos ordenados por periodo:
        flujo_desc = flujo.monto / (1 + tasa)^flujo.periodo
        si acumulado + flujo_desc >= inversion:
            faltante = inversion - acumulado
            fraccion = faltante / flujo_desc
            retornar periodo_anterior + fraccion
        acumulado = acumulado + flujo_desc
    retornar "No se recupera dentro del horizonte"
```

### 21.4 VAC

```text
funcion calcular_VAC(costo_inicial, costos, salvataje, tasa, vida):
    VAC = costo_inicial
    para cada costo en costos:
        VAC = VAC + costo.monto / (1 + tasa)^costo.periodo
    VAC = VAC - salvataje / (1 + tasa)^vida
    retornar VAC
```

### 21.5 CAUE

```text
funcion calcular_CAUE(VAC, tasa, vida):
    factor = tasa * (1 + tasa)^vida / ((1 + tasa)^vida - 1)
    CAUE = VAC * factor
    retornar CAUE
```

---

## 22. Fórmulas útiles en Excel o Google Sheets

Supóngase:

- La tasa está en `B1`.
- La inversión inicial está en `B2`.
- Los flujos de los años 1 a n están en `B3:B6`.

| Indicador              | Fórmula en Excel/Sheets                                                               |
|------------------------|---------------------------------------------------------------------------------------|
| VAN                    | `=VNA(B1,B3:B6)-B2`                                                                   |
| VAN en Excel inglés    | `=NPV(B1,B3:B6)-B2`                                                                   |
| TIR                    | `=TIR(B2:B6)` si B2 contiene la inversión inicial negativa y B3:B6 los flujos futuros |
| TIR en Excel inglés    | `=IRR(B2:B6)`                                                                         |
| B/C                    | `=VNA(B1,B3:B6)/B2`                                                                   |
| Flujo descontado año t | `=Flujo/(1+$B$1)^t`                                                                   |
| CAUE                   | `=VAC*(i*(1+i)^n)/((1+i)^n-1)`                                                        |
| Tasa de Fisher         | `=TIR(rango_flujos_diferenciales)`                                                    |

Nota: en Excel en español, algunas configuraciones usan `VNA` para valor actual neto y otras pueden variar según idioma/región. En Google Sheets, también pueden aparecer funciones en inglés según la configuración.

---

# PARTE V: ERRORES COMUNES

---

## 23. Errores frecuentes y cómo evitarlos

| Error                                                | Consecuencia                                   | Recomendación                                |
|------------------------------------------------------|------------------------------------------------|----------------------------------------------|
| Usar utilidad contable en vez de flujo de caja       | Distorsiona el valor económico.                | Usar entradas y salidas reales de efectivo.  |
| No incluir valor de salvataje                        | Subestima el proyecto o activo.                | Agregar salvataje en el último periodo.      |
| Mezclar tasas nominales y flujos reales              | Genera resultados inconsistentes.              | Mantener coherencia entre tasa y flujo.      |
| Comparar TIR sin mirar VAN                           | Puede elegir proyectos que no maximizan valor. | Priorizar VAN en proyectos excluyentes.      |
| Usar B/C como único criterio                         | Ignora tamaño de inversión.                    | Complementar con VAN.                        |
| Usar PRD como rentabilidad                           | Confunde recuperación con generación de valor. | Tratar PRD como indicador auxiliar.          |
| Comparar activos con vidas distintas usando solo VAC | Puede favorecer indebidamente una alternativa. | Usar CAUE o MCM.                             |
| Ignorar mantenimiento periódico                      | Subestima costos de largo plazo.               | Incluirlo como flujo recurrente o periódico. |
| Usar COK arbitrario                                  | Cambia artificialmente la decisión.            | Justificar tasa según riesgo y oportunidad.  |

---

## 24. Jerarquía recomendada de decisión

Cuando varios indicadores no coinciden, se recomienda seguir esta lógica:

1. **VAN** como criterio principal de creación de valor.
2. **TIR** como complemento porcentual.
3. **B/C** como medida de eficiencia relativa.
4. **PRD** como apoyo para analizar recuperación y riesgo.
5. **VAC, CAUE, MCM o CC** cuando se comparan alternativas de costos o reemplazo.

En proyectos mutuamente excluyentes, el VAN suele ser más confiable que la TIR porque mide cuánto valor monetario absoluto se crea.

---

# PARTE VI: MINI CASOS DE PRÁCTICA

---

## 25. Caso 1: aceptar o rechazar un proyecto

**Datos:**

| Periodo |   Flujo |
|--------:|--------:|
|       0 | -80,000 |
|       1 |  25,000 |
|       2 |  30,000 |
|       3 |  35,000 |
|       4 |  40,000 |

COK: 10%.

**Tarea:**

1. Calcular VAN.
2. Calcular TIR.
3. Calcular B/C.
4. Decidir si se acepta.

**Interpretación esperada:** si VAN > 0, TIR > 10% y B/C > 1, se acepta.

---

## 26. Caso 2: elegir entre dos máquinas con igual vida útil

**Datos:**

| Dato          | Máquina A | Máquina B |
|---------------|----------:|----------:|
| Costo inicial |    60,000 |    75,000 |
| Costo anual   |     4,000 |     1,500 |
| Salvataje     |     5,000 |    12,000 |
| Vida útil     |    8 años |    8 años |

Tasa: 13%.

**Método sugerido:** VAC.

**Regla:** elegir la máquina con menor VAC.

---

## 27. Caso 3: elegir entre dos equipos con vida útil distinta

**Datos:**

| Dato          | Equipo A | Equipo B |
|---------------|---------:|---------:|
| Costo inicial |  100,000 |  130,000 |
| Costo anual   |   20,000 |   12,000 |
| Salvataje     |   10,000 |   25,000 |
| Vida útil     |   5 años |   8 años |

Tasa: 15%.

**Método sugerido:** CAUE.

**Regla:** elegir el equipo con menor CAUE.

---

## 28. Caso 4: alternativa indefinida

**Datos:**

| Concepto                |   Valor |
|-------------------------|--------:|
| Inversión inicial       | 300,000 |
| Costo anual perpetuo    |  18,000 |
| Reparación cada 10 años |  40,000 |
| Tasa                    |     20% |

**Método sugerido:** Costo Capitalizado.

Modelo:

$$
CC = 300000 + \frac{18000}{0.20} + \frac{40000}{(1.20)^{10}-1}
$$

---

# PARTE VII: RESUMEN FINAL

---

## 29. Síntesis conceptual

Los indicadores de rentabilidad permiten decidir si una inversión genera suficiente valor frente a una alternativa de riesgo comparable. El **VAN** indica el valor monetario creado; la **TIR** expresa la rentabilidad porcentual; el **B/C** muestra la eficiencia por unidad invertida; y el **PRD** estima el tiempo necesario para recuperar la inversión.

Cuando el problema no consiste en aceptar o rechazar un proyecto con beneficios, sino en elegir entre activos que cumplen una misma función, se aplican criterios de análisis de reemplazo. El **VAC** permite comparar costos en valor presente, el **CAUE** anualiza esos costos para vidas útiles distintas, el **MCM** iguala horizontes mediante repetición de ciclos, y el **CC** sirve para alternativas indefinidas o perpetuas.

La clave metodológica es construir correctamente los flujos de caja, usar una tasa de descuento coherente con el riesgo y elegir el indicador apropiado según la naturaleza del problema.

---

## 30. Checklist final para resolver ejercicios

```text
[ ] Identifiqué si el problema es de rentabilidad o de costos.
[ ] Organicé los flujos por periodo.
[ ] Coloqué la inversión inicial en t = 0.
[ ] Incluí costos operativos, mantenimiento y salvataje.
[ ] Verifiqué la vida útil de cada alternativa.
[ ] Elegí el indicador correcto: VAN, TIR, B/C, PRD, VAC, CAUE, MCM o CC.
[ ] Usé una tasa de descuento coherente.
[ ] Interpreté el resultado con el criterio correcto.
[ ] Revisé si hay contradicción entre indicadores.
[ ] Si comparé proyectos excluyentes, verifiqué si existe Tasa de Fisher.
[ ] Emití una decisión clara y justificada.
```

---

## 31. Regla de oro

```text
Para proyectos que generan beneficios: aceptar si crean valor.
Para alternativas de costos: elegir la que cueste menos en términos equivalentes.
```
