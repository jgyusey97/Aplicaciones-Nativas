package com.bg.bancoguayaquilactivaciontarjeta.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bg.bancoguayaquilactivaciontarjeta.R

@Composable
fun CloseCircleButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0xFFF5F5F7)) // Fondo gris claro permanente
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Cerrar",
            tint = Color(0xFF333333) // Color del ícono
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLCloseCircleButton() {
    CloseCircleButton(onClick = {})
}
