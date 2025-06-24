package com.bg.bancoguayaquilcertificates.models

data class ValidationConfiguration(
    val title: String,
    val description: String,
    val imageResource: Int,
    val primaryButtonText: String,
    val primaryButtonAction: () -> Unit,
    val secondaryButtonText: String? = null,
    val secondaryButtonAction: (() -> Unit)? = null
)