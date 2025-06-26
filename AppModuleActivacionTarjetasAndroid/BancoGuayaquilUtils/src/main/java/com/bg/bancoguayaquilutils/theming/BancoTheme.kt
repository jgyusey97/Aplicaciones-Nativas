package com.bg.bancoguayaquilutils.theming
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

// CompositionLocals
private val LocalBancoColors = staticCompositionLocalOf { LightColorScheme }
private val LocalBancoTypography = staticCompositionLocalOf { bancoTypography }

// Wrapper del theme
@Composable
fun BancoTheme(
    colors: BancoColorScheme = LightColorScheme,
    typography: BancoTypography = bancoTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalBancoColors provides colors,
        LocalBancoTypography provides typography,
        content = content
    )
}

object BancoTheme {
    val colors: BancoColorScheme
        @Composable
        get() = LocalBancoColors.current

    val typography: BancoTypography
        @Composable
        get() = LocalBancoTypography.current
}