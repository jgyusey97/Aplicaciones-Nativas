package com.bg.bancoguayaquilactivaciontarjeta.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val client = OkHttpClient.Builder().build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:9502/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val activacionApi: ActivacionApi = retrofit.create(ActivacionApi::class.java)
}