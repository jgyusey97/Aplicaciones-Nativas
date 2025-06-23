package com.bg.bancoguayaquilactivaciontarjeta.ui.theme

import android.os.Build

private val DarkColorScheme = darkColorScheme()
private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    background = backgroundColor


)

@Composable
fun ActivacionTarjetasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}