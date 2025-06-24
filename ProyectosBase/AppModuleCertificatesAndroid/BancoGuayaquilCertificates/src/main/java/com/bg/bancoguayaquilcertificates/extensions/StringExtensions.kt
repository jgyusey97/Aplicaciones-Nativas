package com.bg.bancoguayaquilcertificates.extensions


fun String.toCapitalize(): String = split(" ").joinToString(" ") {
    it.lowercase().replaceFirstChar { c -> c.uppercase() }
}

fun String.toTitleCase(): String {
    if (this.isEmpty()) {
        return ""
    }

    val firstLetter = this.firstOrNull()?.uppercase() ?: ""
    val restLetters = this.drop(1).lowercase()

    return firstLetter + restLetters
}
