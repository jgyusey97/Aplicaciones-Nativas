package com.bg.bancoguayaquilactivaciontarjeta.services

import android.content.Context
import com.bg.bancoguayaquilactivaciontarjeta.models.EscenariosPendientesActivacion
import com.bg.bancoguayaquilactivaciontarjeta.models.TarjetaPendienteActivacion
import com.bg.bancoguayaquilactivaciontarjeta.repository.ActivacionRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivacionService(private val context: Context) {

    private val repository = ActivacionRepository()
/*
    suspend fun  getTarjetasPendientes(nombre: String): List<TarjetaPendienteActivacion> {
        return repository.getTarjetasPendientes()
    }
*/


    suspend fun getTarjetasPendientes(nombre: String): List<TarjetaPendienteActivacion> = withContext(Dispatchers.IO) {
        val inputStream = context.assets.open("pendientes_activacion_test.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val data = Gson().fromJson(json, EscenariosPendientesActivacion::class.java)
        return@withContext data.escenarios[nombre] ?: emptyList()
    }

}