package com.bg.bancoguayaquilcertificates.views.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFAE5F0),
            contentColor = Color(0xFFD2006E),
            disabledContainerColor = Color(0xFF000000).copy(alpha = 0.1f),
        ),
        enabled = enabled
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun SecondaryButtonPreview() {
    CertificatesTheme {
        Column {
            SecondaryButton("Secondary", onClick = {})
            SecondaryButton("Disable", onClick = {}, enabled = false)
        }
    }
}
