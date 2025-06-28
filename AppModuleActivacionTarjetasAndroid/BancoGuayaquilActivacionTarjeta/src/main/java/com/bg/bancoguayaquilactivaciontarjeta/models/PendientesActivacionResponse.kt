package com.bg.bancoguayaquilactivaciontarjeta.models


import com.bg.bancoguayaquilactivaciontarjeta.models.TarjetaPendienteActivacion

data class PendientesActivacionResponse(
    val traceid: String,
    val success: Boolean,
    val collection: Boolean,
    val count: Int,
    val data: List<TarjetaPendienteActivacion>
)
