package com.bg.bancoguayaquilactivaciontarjeta.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bg.bancoguayaquilactivaciontarjeta.R
@Composable
fun FaceVerificationScreen(
    imageRes: Int,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Botón de cerrar
        IconButton (
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(40.dp)
                .background(Color(0xFFF5F5F7), shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar",
                tint = Color.Black
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen circular con borde
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Rostro",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(260.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color(0xFF0093B1), CircleShape)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Texto guía
            Text(
                text = "Permanece quieto, con tu cara\nen el centro del círculo",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            )
        }

        // Logo inferior
        Image(
            painter = painterResource(id = R.drawable.logo_bg), // Usa tu ícono
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .size(48.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FaceVerificationScreenPreview() {
    FaceVerificationScreen(
        imageRes = R.drawable.logo_facephi, // usa una imagen dummy
        onClose = {}
    )
}