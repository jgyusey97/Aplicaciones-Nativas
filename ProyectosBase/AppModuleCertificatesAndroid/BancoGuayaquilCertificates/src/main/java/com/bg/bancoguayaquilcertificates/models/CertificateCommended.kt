package com.bg.bancoguayaquilcertificates.models

data class CertificateCommended(
    val value: String,
    val recommendeds: List<String>,
    val textRecommendation: String
)
