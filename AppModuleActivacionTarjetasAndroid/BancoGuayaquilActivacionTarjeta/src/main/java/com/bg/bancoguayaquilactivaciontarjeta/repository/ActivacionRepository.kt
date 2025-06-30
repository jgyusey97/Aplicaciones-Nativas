package com.bg.bancoguayaquilactivaciontarjeta.repository

import android.content.Context
import com.bg.bancoguayaquilactivaciontarjeta.models.EscenariosPendientesActivacion
import com.bg.bancoguayaquilactivaciontarjeta.models.TarjetaPendienteActivacion
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivacionRepository() {

    /* Debe de ser integrada esta llamada para el consumo de api y obtener la sesion antes de la libreria
    private val service = MultiChannelService.instance
    private val session = BancoGuayaquilSession.instance.getSession()
    *
     */
/*
    suspend fun getTarjetasPendientes(nombre: String): List<TarjetaPendienteActivacion> = withContext(Dispatchers.IO) {
        val inputStream = context.assets.open("pendientes_activacion_test.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val data = Gson().fromJson(json, EscenariosPendientesActivacion::class.java)
        return@withContext data.escenarios[nombre] ?: emptyList()
    }
*/

    suspend fun getTarjetasPendientes(nombre: String): List<TarjetaPendienteActivacion> = withContext(Dispatchers.IO) {
        val inputStream = context.assets.open("pendientes_activacion_test.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val data = Gson().fromJson(json, EscenariosPendientesActivacion::class.java)
        data.escenarios[nombre] ?: emptyList()
    }

}



