package com.bg.bancoguayaquilutils.theming



import androidx.compose.runtime.Composable
import com.bg.bancoguayaquilutils.theming.BancoTheme

@Composable
fun BancoWrapper(content: @Composable () -> Unit) {
    BancoTheme {
        content()
    }
}
