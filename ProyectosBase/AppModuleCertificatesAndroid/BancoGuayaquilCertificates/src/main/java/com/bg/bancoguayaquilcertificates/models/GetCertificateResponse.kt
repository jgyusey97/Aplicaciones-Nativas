package com.bg.bancoguayaquilcertificates.models

import com.google.gson.annotations.SerializedName

data class GetCertificateResponse(
    @SerializedName("CodigoRetorno")
    val returnCode: String,
    @SerializedName("Message")
    val message: String
)
