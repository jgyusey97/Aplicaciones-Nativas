package com.bg.bancoguayaquilcertificates.models

data class GetCertificateRequest(
    val product: ProductDetail,
    val request: CertificateRequest
)

data class CertificateRequest(
    val recipient: String,
    //val payForm: PayForm,
    val language: String,
    val balanceType: String,
    val certificateType: CertificateType,
    val certificateCost: String? = null
)

data class PayForm(
    val accountType: String,
    val accountNumber: String
)
