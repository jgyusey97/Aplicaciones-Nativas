package com.bg.bancoguayaquilcertificates.views.modals

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bg.bancoguayaquilcertificates.R
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme
import com.bg.bancoguayaquilcertificates.views.components.PrimaryButton

@Composable
fun CertificateSentModal(
    isOpen: Boolean,
    email: String? = null,
    onDismiss: (() -> Unit)? = null,
) {
    FullScreenModal(
        isOpen,
        onDismiss,
        footer = {
            Box(modifier = Modifier.padding(16.dp)) {
                PrimaryButton(
                    text = "Listo",
                    onClick = onDismiss,
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.checkmark),
                contentDescription = "Checkmark Icon",
                modifier = Modifier.size(160.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Certificado enviado",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    "Enviamos el certificado al correo",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 10.dp)
                )

                email?.let {
                    Text(it, color = Color.Gray)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CertificateSentModalPreview() {
    CertificatesTheme {
        CertificateSentModal(isOpen = true, email = "user@mail.com")
    }
}
