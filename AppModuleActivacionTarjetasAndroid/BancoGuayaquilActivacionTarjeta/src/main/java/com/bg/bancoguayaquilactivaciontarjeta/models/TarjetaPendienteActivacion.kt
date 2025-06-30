

package com.bg.bancoguayaquilactivaciontarjeta.models


data class TarjetaPendienteActivacion(
    val tarjeta: String,
    val alias: String,
    val bin: String,
    val afinidad: String,
    val nombrePlastico: String,
    val princiadicio: String, // "P" o "A"
    val cuenta: String,
    val cliente: String,
    val imagenUrl:String,
    val fechApertura:String
)

data class EscenariosPendientesActivacion(
    val escenarios: Map<String, List<TarjetaPendienteActivacion>>
)
