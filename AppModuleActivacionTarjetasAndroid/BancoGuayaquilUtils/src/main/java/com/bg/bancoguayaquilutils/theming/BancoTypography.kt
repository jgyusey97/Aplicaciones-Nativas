package com.bg.bancoguayaquilutils.theming
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.bg.bancoguayaquilutils.R
// 1. Declaración del FontFamily personalizado
val NunitoSans = FontFamily(
    Font(R.font.nunito_sans_regular, FontWeight.Normal),
    Font(R.font.nunito_sans_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_sans_bold, FontWeight.Bold)
)
data class BancoTypography (

    val headingLarge: TextStyle = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 23.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 32.sp
    ),
    val headingMedium: TextStyle = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 21.25.sp
    ),

    val labelStrong: TextStyle = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 14.sp,
        fontWeight = FontWeight(400),
        lineHeight = 21.25.sp
    ),

    val subtitle: TextStyle = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 27.sp
    ),
    val body: TextStyle = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,

    ),

    val caption: TextStyle = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 21.sp
    ),

    val message: TextStyle = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 17.sp
    )


)

val bancoTypography = BancoTypography()