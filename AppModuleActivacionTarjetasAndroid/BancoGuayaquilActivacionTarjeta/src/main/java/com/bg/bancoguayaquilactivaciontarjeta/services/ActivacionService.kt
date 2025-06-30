package com.bg.bancoguayaquilactivaciontarjeta.services

import com.bg.bancoguayaquilactivaciontarjeta.models.TarjetaPendienteActivacion
import com.bg.bancoguayaquilactivaciontarjeta.repository.ActivacionRepository

class ActivacionService {

    private val repository = ActivacionRepository()

    suspend fun  getTarjetasPendientes(nombre: String): List<TarjetaPendienteActivacion> {
        return repository.getTarjetasPendientes(nombre)
    }



}