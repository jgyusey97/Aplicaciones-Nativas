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

import com.bg.bancoguayaquilutils.theming.BancoTheme
import com.bg.bancoguayaquilutils.theming.BancoWrapper


@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color
) {
    BancoWrapper {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = text,
                style = BancoTheme.typography.subtitle
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun ActionButtonEnablePreview() {

    BancoTheme {
        ActionButton(text = "Salir", onClick = {}, containerColor = BancoTheme.colors.background, contentColor = BancoTheme.colors.title3 )

    }


}

@Preview(showBackground = true)
@Composable
fun ActionButtonDisablePreview() {

    BancoTheme {
        ActionButton(text = "Continuar", onClick = {}, containerColor = BancoTheme.colors.primary, contentColor = BancoTheme.colors.background)


    }


}

