package com.bg.bancoguayaquilcertificates.views.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme

@Composable
fun SimpleTopAppBar(
    title: String,
    subTitle: String? = null,
    onBack: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        IconButton(
            onClick = { onBack?.invoke() }
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            subTitle?.let {
                Text(
                    text = it, style = MaterialTheme.typography.bodyMedium, color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleTopAppBarPreview() {
    CertificatesTheme {
        SimpleTopAppBar(title = "My App", subTitle = "Lorem ipsum")
    }
}
