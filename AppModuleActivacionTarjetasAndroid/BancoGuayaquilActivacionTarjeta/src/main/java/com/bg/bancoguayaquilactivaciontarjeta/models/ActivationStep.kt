package com.bg.bancoguayaquilactivaciontarjeta.models


enum class ActivationStep {
    NONE,
    SELECT_CARD,
    FACE_VERIFICATION,
    ENTER_DIGITS,
    SUCCESS
}
