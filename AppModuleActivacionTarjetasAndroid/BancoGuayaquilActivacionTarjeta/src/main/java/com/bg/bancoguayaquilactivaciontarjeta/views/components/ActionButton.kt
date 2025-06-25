package com.bg.bancoguayaquilactivaciontarjeta.views.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bg.bancoguayaquilactivaciontarjeta.ActivarTarjetaCard
import com.bg.bancoguayaquilactivaciontarjeta.ui.theme.ActivacionTarjetasTheme

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    containerColor: Color = if (enabled) Color(0xFFD40072) else Color(0xFFE0E0E0),
    contentColor: Color = if (enabled) Color.White else Color.Gray
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ActionButtonEnablePreview() {

    ActivacionTarjetasTheme {
        ActionButton(
            text = "Salir",
            onClick = {},
            enabled = false

        )
    }


}

@Preview(showBackground = true)
@Composable
fun ActionButtonDisablePreview() {

    ActivacionTarjetasTheme {
        ActionButton(
            text = "Continuar",
            onClick = {},


        )
    }


}

