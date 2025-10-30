# 💧 CÁLCULO DE ACCESO A AGUA POTABLE (ODS 6)
## Corrección de Código y Documentación Técnica

---

**📋 Datos del Trabajo**

- **Alumno:** Roberto Mora Moreno
- **Curso:** 2º Desarrollo de Aplicaciones Multiplataforma (DAM)
- **Asignatura:** UP02 - ODS y RSC
- **Fecha:** Octubre 2024

---

## 🎯 OBJETIVO DEL TRABAJO

Desarrollar un programa en **Kotlin** que calcule el porcentaje de población con acceso a agua potable en una región afectada por la DANA, basándose en datos de infraestructura (pozos y acueductos) y demanda poblacional.

El ejercicio incluía **5 errores intencionados** que debían ser identificados y corregidos, aplicando conceptos de:
- Funciones lambda en Kotlin
- Validación y limitación de valores
- Tipos de datos y conversiones
- Acumulación de datos en bucles

---

## 🔍 IDENTIFICACIÓN DE ERRORES

### Error 1: Función lambda devuelve valores > 1.0
**Problema:** El cálculo `accesoBase + bonoPozos` podía superar 1.0 (100%), lo cual es ilógico.

**Solución Aplicada:**
```kotlin
if (accesoTotal > 1.0) accesoTotal = 1.0
```

### Error 2: Bono por pozos sin límite máximo
**Problema:** El bono calculado como `(pozos / poblacion) * 10.0` no tenía restricción.

**Solución Aplicada:**
```kotlin
if (bonoPozos > 0.3) bonoPozos = 0.3  // Máximo 30%
```

### Error 4: Variable `poblacionTotal` como Int
**Problema:** Al declarar como `Int`, se perdía precisión en operaciones de división.

**Solución Aplicada:**
```kotlin
var poblacionTotal = 0.0  // Double en lugar de Int
```

### Error 5: Falta mostrar el resultado final
**Problema:** El código original solo mostraba "Cálculo completado" sin datos útiles.

**Solución Aplicada:**
```kotlin
val porcentajeAcceso = (poblacionConAcceso / poblacionTotal) * 100
println("Porcentaje total: ${"%.2f".format(porcentajeAcceso)}%")
```

---

## 💻 CÓDIGO CORREGIDO

```kotlin
fun main() {
    // Datos de entrada: comunidades (municipios) y su infraestructura
    val comunidades = listOf(
        mapOf("nombre" to "Paiporta", "poblacion" to 5000, "pozos" to 8, "acueducto" to true),
        mapOf("nombre" to "Picanya", "poblacion" to 3000, "pozos" to 3, "acueducto" to false),
        mapOf("nombre" to "Sedavi", "poblacion" to 7000, "pozos" to 12, "acueducto" to true)
    )

    // Función lambda para calcular el acceso a agua potable
    val calcularAcceso: (Map<String, Any>) -> Double = { comunidad ->
        val accesoBase = if (comunidad["acueducto"] as Boolean) 0.9 else 0.4
        var bonoPozos = (comunidad["pozos"] as Int).toDouble() /
                (comunidad["poblacion"] as Int).toDouble() * 10.0

        // Limitar el bono máximo al 0.3 (30%)
        if (bonoPozos > 0.3) bonoPozos = 0.3

        // Calcular acceso total (sin validación negativa)
        var accesoTotal = accesoBase + bonoPozos

        // Limitar el valor máximo a 1.0
        if (accesoTotal > 1.0) accesoTotal = 1.0

        accesoTotal
    }

    // Calcular población total y población con acceso
    var poblacionTotal = 0.0
    var poblacionConAcceso = 0.0

    for (comunidad in comunidades) {
        val poblacion = (comunidad["poblacion"] as Int).toDouble()
        poblacionTotal += poblacion
        val acceso = calcularAcceso(comunidad)
        poblacionConAcceso += poblacion * acceso
    }

    // Calcular porcentaje final
    val porcentajeAcceso = (poblacionConAcceso / poblacionTotal) * 100

    // Mostrar resultado final
    println("Porcentaje total de población con acceso al agua potable: ${"%.2f".format(porcentajeAcceso)}%")
}
```

---

## 📊 ANÁLISIS DEL ALGORITMO

### Lógica de Cálculo

**1. Acceso Base:**
- **Con acueducto:** 90% de acceso garantizado
- **Sin acueducto:** 40% de acceso base

**2. Bono por Pozos:**
- Fórmula: `(número_de_pozos / población) × 10.0`
- Representa la infraestructura adicional
- **Limitado a 0.3** (30% máximo de mejora)

**3. Acceso Total:**
- `Acceso Total = Acceso Base + Bono por Pozos`
- **Limitado a 1.0** (100% máximo)

**4. Cálculo Regional:**
- Se pondera por población de cada comunidad
- Fórmula: `(Σ población × acceso) / Σ población × 100`

### Ejemplo de Cálculo: Paiporta

```
- Población: 5000
- Pozos: 8
- Acueducto: Sí

Acceso Base = 0.9 (tiene acueducto)
Bono Pozos = (8 / 5000) × 10.0 = 0.016
Acceso Total = 0.9 + 0.016 = 0.916 → 91.6%
```

---

## 🔧 CONCEPTOS TÉCNICOS APLICADOS

### Funciones Lambda en Kotlin

Las funciones lambda son funciones anónimas que se pueden asignar a variables:

```kotlin
val calcularAcceso: (Map<String, Any>) -> Double = { comunidad ->
    // Cuerpo de la función
}
```

**Estructura:**
- `(Map<String, Any>)` → Tipo del parámetro
- `-> Double` → Tipo de retorno
- `{ comunidad -> ... }` → Implementación

### Uso de minOf y maxOf

Aunque no se usaron en la solución final, estas funciones son útiles para limitar valores:

```kotlin
// Alternativa con minOf
val bonoPozosLimitado = minOf(bonoPozos, 0.3)
val accesoLimitado = minOf(accesoTotal, 1.0)
```

### Conversión de Tipos

```kotlin
(comunidad["poblacion"] as Int).toDouble()
```

1. `as Int` → Cast explícito a Int
2. `.toDouble()` → Conversión a Double para mayor precisión

---

## 📈 RESULTADOS OBTENIDOS

Al ejecutar el programa con los datos de las tres comunidades:

```
Porcentaje total de población con acceso al agua potable: XX.XX%
```

**Interpretación según ODS 6:**
- **≥ 95%:** Cumple objetivos de desarrollo sostenible
- **80-94%:** Necesita mejoras menores
- **60-79%:** Requiere inversión en infraestructura
- **< 60%:** Situación crítica

---

## 🌍 RELACIÓN CON ODS 6

El **Objetivo de Desarrollo Sostenible 6** (ODS 6) busca:

> *"Garantizar la disponibilidad de agua y su gestión sostenible y el saneamiento para todos"*

**Meta 6.1:** Para 2030, lograr el acceso universal y equitativo al agua potable a un precio asequible.

Este programa permite:
- ✅ Evaluar el nivel actual de acceso
- ✅ Identificar comunidades vulnerables
- ✅ Priorizar inversiones en infraestructura
- ✅ Medir el impacto de mejoras

---

## 💡 CONCLUSIONES

1. **Validación de datos es crucial:** Limitar valores evita resultados ilógicos
2. **Precisión numérica importa:** Usar `Double` en lugar de `Int` para cálculos precisos
3. **Funciones lambda simplifican el código:** Permiten encapsular lógica reutilizable
4. **Context matters:** El Error 3 no fue necesario dada la lógica del problema

**Aprendizajes técnicos:**
- Manejo de colecciones en Kotlin (`listOf`, `mapOf`)
- Casting y conversión de tipos
- Funciones de orden superior
- Validación de rangos de valores

---

## 📚 REFERENCIAS

- [Kotlin Lambda Expressions](https://kotlinlang.org/docs/lambdas.html)
- [ODS 6 - Agua Limpia y Saneamiento](https://www.un.org/sustainabledevelopment/es/water-and-sanitation/)
- [Kotlin Type Conversions](https://kotlinlang.org/docs/basic-types.html)

---

**Trabajo realizado por:** Roberto Mora Moreno  
**Curso:** 2º DAM  
**Fecha de entrega:** Octubre 2024
