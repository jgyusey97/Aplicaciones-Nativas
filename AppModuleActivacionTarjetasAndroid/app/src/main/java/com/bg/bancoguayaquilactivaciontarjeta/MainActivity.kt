package com.bg.bancoguayaquilactivaciontarjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox

import androidx.compose.material.icons.outlined.Email

import androidx.compose.material.icons.outlined.MailOutline

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = SecondaryColor
            ) {
                PantallaPrincipalConActivacion()
            }
        }
    }
}

// Colores propios definidos localmente
val PrimaryColor = Color(0xFF171D9A)
val SecondaryColor = Color(0xFFF5F5F5)
val CardBackgroundColor = Color(0xFFF1F1F1)
val AccentColor = Color(0xFFD0006F)

@Composable
fun PantallaPrincipalConActivacion() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryColor)
                .padding(16.dp)
        ) {
            Column {
                TarjetaConImagen(drawableId = R.drawable.avanti_card) {
                    // Acción del botón
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Consumos a la fecha", color = Color.White)
                Text("$0.00", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Disponible: $4,000.00", color = Color.White)
            }
        }

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SecondaryColor)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("General", color = Color.Gray, modifier = Modifier.weight(1f))
            Text("Preferencias", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }

        // Opciones
        Column(modifier = Modifier.padding(16.dp)) {
            OpcionItem(
                titulo = "Solicita una tarjeta adicional",
                subtitulo = "Comparte el cupo de tu tarjeta con una adicional",
                icono = Icons.Outlined.AccountBox
            )
            OpcionItem(
                titulo = "Comprar por internet",
                subtitulo = "Estado: Activo",
                icono = Icons.Outlined.MailOutline,
                switch = true
            )
            OpcionItem(
                titulo = "Solicitar clave",
                subtitulo = "Llegará por correo y SMS",
                icono = Icons.Outlined.Email,
                botonTexto = "Enviar"
            )
            ActivarTarjetaButton()
        }
    }
}

@Composable
fun TarjetaConImagen(drawableId: Int, onBotonClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "Tarjeta",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Button(
                onClick = onBotonClick,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Activa tu tarjeta", color = Color.Black)
            }
        }
    }
}

@Composable
fun OpcionItem(
    titulo: String,
    subtitulo: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    switch: Boolean = false,
    botonTexto: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = PrimaryColor,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = titulo, fontWeight = FontWeight.Bold)
                Text(text = subtitulo, color = Color.Gray, fontSize = 12.sp)
            }

            if (switch) {
                Switch(checked = true, onCheckedChange = {}, colors = SwitchDefaults.colors(
                    checkedThumbColor = PrimaryColor
                ))
            }

            botonTexto?.let {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = AccentColor)
                ) {
                    Text(it, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPreview() {
    PantallaPrincipalConActivacion()
}
