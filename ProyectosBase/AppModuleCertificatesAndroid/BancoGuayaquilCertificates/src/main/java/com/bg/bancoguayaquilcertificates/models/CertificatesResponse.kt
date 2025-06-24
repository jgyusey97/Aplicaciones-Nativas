package com.bg.bancoguayaquilcertificates.models

import com.google.gson.annotations.SerializedName

data class CertificatesResponse(
    @SerializedName("CodigoRetorno")
    val returnCode: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Data")
    val data: CertificateTableResponse? = null
)

data class CertificateTableResponse(
    @SerializedName("tbConsultaProductoOut")
    val certificates: List<CertificateType> = emptyList()
)

data class CertificateType(
    @SerializedName("CodigoRetorno")
    val returnCode: String,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("codigoExt")
    val extCode: String,
    @SerializedName("Canal")
    val channel: String
)
