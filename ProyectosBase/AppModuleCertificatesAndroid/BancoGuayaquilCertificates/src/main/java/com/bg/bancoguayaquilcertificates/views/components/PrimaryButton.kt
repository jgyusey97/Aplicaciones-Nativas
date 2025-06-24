package com.bg.bancoguayaquilcertificates.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            if (enabled && onClick != null && !isLoading) onClick()
        },
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color(0xFF000000).copy(alpha = 0.1f),
        ),
        enabled = enabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .padding(2.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
fun PrimaryButtonPreview() {
    CertificatesTheme {
        Column(Modifier.background(Color.White)) {
            PrimaryButton("Primary")
            PrimaryButton("Disable", enabled = false)
        }
    }
}
