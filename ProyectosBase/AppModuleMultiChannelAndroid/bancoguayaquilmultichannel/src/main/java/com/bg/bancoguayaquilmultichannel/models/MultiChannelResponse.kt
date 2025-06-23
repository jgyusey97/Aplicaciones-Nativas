package com.bg.bancoguayaquilmultichannel.models

import com.google.gson.annotations.SerializedName

data class MultiChannelResponse<T>(
    @SerializedName("CodigoRetorno")
    var code: String,

    @SerializedName("MensajeRetorno")
    val message: String,

    @SerializedName("Respuesta")
    val data: T? = null
)
