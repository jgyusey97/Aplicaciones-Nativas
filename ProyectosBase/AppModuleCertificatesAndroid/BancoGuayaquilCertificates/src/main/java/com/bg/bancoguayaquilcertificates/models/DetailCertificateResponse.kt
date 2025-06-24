package com.bg.bancoguayaquilcertificates.models

import com.google.gson.annotations.SerializedName

data class DetailCertificateResponse(
    @SerializedName("CodigoRetorno")
    val returnCode: String,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Data")
    val data: CertificateData
)

data class CertificateData(
    @SerializedName("tbDetalleProducto")
    val productDetails: List<ProductDetail>,
    @SerializedName("tbDetalleCatlogoXTipo")
    val catalogDetailsByType: List<CatalogDetailByType>
)

data class ProductDetail(
    @SerializedName("idControl")
    val controlId: String,
    @SerializedName("tipoPro")
    val productType: String,
    @SerializedName("codigoPro")
    val productCode: String,
    @SerializedName("oestado")
    val state: String,
    @SerializedName("valor")
    val value: String,
    @SerializedName("signo")
    val sign: String,
    @SerializedName("efeDisO")
    val availableCash: String,
    @SerializedName("signoEfeDisO")
    val availableCashSign: String,
    @SerializedName("fechaEmi")
    val issueDate: String,
    @SerializedName("fechaVen")
    val expirationDate: String,
    @SerializedName("nivLetras")
    val levelLetters: String,
    @SerializedName("nivnumero")
    val levelNumber: String,
    @SerializedName("nivutil")
    val levelUtil: String,
    @SerializedName("cifrasutil")
    val figuresUtil: String,
    @SerializedName("nivelCupo")
    val levelQuota: String,
    @SerializedName("cifrasCupo")
    val figuresQuota: String,
    @SerializedName("nivelVcdo")
    val levelDue: String,
    @SerializedName("cifrasVcdo")
    val figuresDue: String
)

data class CatalogDetailByType(
    @SerializedName("idControl")
    val controlId: String,
    @SerializedName("Descripcion")
    val description: String,
    @SerializedName("CodigoHost")
    val hostCode: String,
    @SerializedName("TipoCatalogo")
    val catalogType: String
)

data class ProductCertOption(
    val productCode: String,
    val productName: String,
    val productDesc: String? = null,
)
