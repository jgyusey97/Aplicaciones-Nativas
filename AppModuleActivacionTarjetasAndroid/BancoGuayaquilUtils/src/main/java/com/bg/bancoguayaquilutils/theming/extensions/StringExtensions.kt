package com.bg.bancoguayaquilutils.theming.extensions

/**
 * Extensiones de utilidad para el tipo [String], diseñadas para aplicar estilos de capitalización
 * comunes en interfaces de usuario, como nombres propios o frases.
 */

/**
 * Capitaliza la primera letra de cada palabra en la cadena.
 *
 * Útil para mostrar nombres completos o títulos con formato tipo título.
 *
 * @return Una nueva cadena donde cada palabra comienza con mayúscula.
 * Ejemplo: `"banco guayaquil"` → `"Banco Guayaquil"`
 */
fun String.toCapitalize(): String =
    split(" ").joinToString(" ") {
        it.lowercase().replaceFirstChar { c -> c.uppercase() }
    }

/**
 * Capitaliza solo la primera letra de toda la cadena y convierte el resto en minúsculas.
 *
 * Útil para frases o nombres donde solo se desea la primera letra en mayúscula.
 *
 * @return Una nueva cadena con la primera letra en mayúscula.
 * Ejemplo: `"banco guayaquil"` → `"Banco guayaquil"`
 */
fun String.toCapitalizeFirst(): String {
    if (this.isEmpty()) return ""
    return this.first().uppercase() + this.drop(1).lowercase()
}
