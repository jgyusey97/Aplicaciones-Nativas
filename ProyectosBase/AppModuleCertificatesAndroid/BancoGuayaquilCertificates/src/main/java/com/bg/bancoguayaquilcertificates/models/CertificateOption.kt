package com.bg.bancoguayaquilcertificates.models

data class CertificateReason(
    val id: String,
    val value: String,
    val recommended: List<String>
)

val certificateReasons = listOf(
    CertificateReason("1", "Trabajo o trámites", listOf("CF")),
    CertificateReason("2", "Visa o viaje", listOf("CF")),
    CertificateReason("3", "Préstamo o crédito", listOf("CF", "NA")),
    CertificateReason("4", "No tengo productos", listOf("CC")),
    CertificateReason("5", "Menor de edad", listOf("RP")),
    CertificateReason("6", "Otro", emptyList())
)
