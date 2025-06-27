package com.bg.bancoguayaquilutils.theming

import androidx.compose.ui.graphics.Color

data class BancoColorScheme(

    val primary: Color,
    val primarySoft:Color,
    val background: Color,
    val background2:Color,
    val background3:Color,
    val title:Color,
    val title2: Color,
    val title3:Color,
    val title4: Color,
    val body: Color,
    val body2: Color,
    val body3: Color
)


val LightColorScheme = BancoColorScheme(

    primary = Color(0xFFD2006E),
    primarySoft =Color(0xFFFDF2F7),
    background= Color(0xFFFFFFFF),
    background2 =Color(0xFFF9F9F9),
    background3 = Color(0xFFF0F5FE),
    title = Color(0xFF393939),
    title2 = Color(0xFF6F6F6F),
    title3 = Color(0xFF2A244F),
    title4 = Color(0xFF0F62FE),
    body = Color(0xFF6F6F6F),
    body2 = Color(0xFFBDBDBD),
    body3 =Color(0xFFF3F3F3)



)

