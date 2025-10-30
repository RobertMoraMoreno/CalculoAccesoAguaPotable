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
