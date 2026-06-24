# Segmento objetivo

> Define **a quién va dirigido** AutoFinance. Acompaña a [about.md](about.md) y usa el
> vocabulario de [lenguaje-ubicuo.md](lenguaje-ubicuo.md).

## Marco conceptual

Antes de nombrar el segmento, conviene separar tres conceptos que suelen confundirse:

| Concepto                        | Pregunta que responde                       | A quién corresponde                                           |
|---------------------------------|---------------------------------------------|---------------------------------------------------------------|
| **Punto de vista del producto** | ¿Para quién es la herramienta?              | La entidad financiera.                                        |
| **Perspectiva de cálculo**      | ¿Desde qué óptica se calculan VAN y TIR?    | El deudor.                                                    |
| **Segmento objetivo**           | ¿A quién apunta el producto como audiencia? | La entidad financiera, representada por el asesor de crédito. |

Por eso AutoFinance tiene **un único segmento objetivo**: la **entidad financiera**. El deudor
es **beneficiario final**, no segmento del producto, porque no opera el sistema.

> **Nota de lenguaje:** en estos documentos **no** se usa la palabra "usuario" para referirse a
> las personas. El término `Usuario` se reserva para el futuro contexto genérico de **IAM**
> (autenticación/autorización). Aquí hablamos de **asesor de crédito**, **actor** o **rol
> operativo**.

## Segmento objetivo: entidad financiera (asesor de crédito)

La entidad financiera peruana que ofrece crédito vehicular bajo la modalidad **Compra
Inteligente**. Operativamente, quien interactúa con el sistema es el **asesor de crédito**.

| Atributo     | Descripción                                                                                                                                                                            |
|--------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Quién es     | Entidad financiera (banco, financiera, concesionaria con financiamiento) en Perú; el asesor de crédito es su rol operativo.                                                            |
| Qué necesita | Armar simulaciones rápidas y **exactas**; registrar cliente y oferta; **editar y volver a guardar** lo registrado; mostrar al deudor el cronograma y los indicadores de transparencia. |
| Contexto     | Marco normativo SBS (transparencia de la información); operaciones en Soles o Dólares; modalidad balloon para reducir la cuota mensual.                                                |
| Qué valora   | Reproducibilidad del cálculo, claridad de los indicadores, trazabilidad de las operaciones.                                                                                            |

## Beneficiario final: el deudor / comprador

El deudor **no es segmento objetivo del producto** (no usa el sistema), pero su perfil y sus
derechos **moldean los requisitos**.

| Atributo                | Descripción                                                                                                                                                                                                      |
|-------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Perfil                  | Comprador de vehículo **sensible a la cuota mensual baja**, dispuesto a asumir un pago final (cuotón) a cambio de cuotas periódicas menores. La modalidad Compra Inteligente encaja exactamente con este perfil. |
| Por qué importa         | Su **óptica define el cálculo de VAN y TIR**; la **norma de transparencia SBS** existe para protegerlo, lo que obliga a exponer TCEA y el desglose de seguros y costos.                                          |
| Relación con el sistema | Recibe la oferta y la explicación del asesor; sus datos se registran, pero **no opera** la herramienta.                                                                                                          |

## Por qué un solo segmento en v1

- El enunciado fija que el desarrollo se enfoca **desde el punto de vista de la entidad**; el
  sistema es su herramienta interna.
- Mantener **un segmento** evita mezclar "quién usa el producto" con "quién se beneficia del
  resultado", lo que mantendría limpio el modelado posterior.
- El perfil del deudor se captura como **driver de requisitos** (indicadores, transparencia),
  no como un segmento aparte que habría que atender con funcionalidades propias.

## Tabla resumen

| Actor                                  | Relación con el producto                 | Necesidad principal                                 | Cómo lo atiende AutoFinance                                       |
|----------------------------------------|------------------------------------------|-----------------------------------------------------|-------------------------------------------------------------------|
| Entidad financiera / asesor de crédito | **Segmento objetivo** (opera el sistema) | Cronograma e indicadores exactos, registro editable | Motor de cálculo + CRUD + persistencia trazable                   |
| Deudor / comprador                     | **Beneficiario final** (no opera)        | Cuota mensual baja y transparencia del costo        | Modalidad balloon + VAN/TIR óptica del deudor + transparencia SBS |
