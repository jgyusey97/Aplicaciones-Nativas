package com.bg.bancoguayaquilactivaciontarjeta.api
import com.bg.bancoguayaquilactivaciontarjeta.models.TarjetaPendienteActivacion
import retrofit2.http.GET
import retrofit2.http.Header

interface ActivacionApi {

    @GET("productos/v1/tarjetas-credito/pendientes-activacion")
    suspend fun getTarjetasPendientes(
        @Header("identificacion") identificacion: String,
        @Header("canal") canal: String = "NEO",
        @Header("tipoOpcion") tipoOpcion: String = "CP"
    ): List<TarjetaPendienteActivacion>
}