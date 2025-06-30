package com.bg.bancoguayaquilactivaciontarjeta.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.bg.bancoguayaquilactivaciontarjeta.R
import com.bg.bancoguayaquilutils.theming.BancoTheme
import com.bg.bancoguayaquilutils.theming.BancoWrapper

@Composable
fun InfoMessageBanner(
    message: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFE8F1FF), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.information), // Usa el ícono azul de info
            contentDescription = "Información",
            tint = Color(0xFF005EE0),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = message,
            style = BancoTheme.typography.message,
            color = BancoTheme.colors.title
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoMessageBanner() {
    BancoWrapper {
        InfoMessageBanner(
            message = "Para activar tu tarjeta adicional, necesitamos los datos de la tarjeta titular"
        )
    }
}
