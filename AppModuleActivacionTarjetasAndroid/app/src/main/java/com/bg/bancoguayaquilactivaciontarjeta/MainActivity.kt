package com.bg.bancoguayaquilactivaciontarjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import com.bg.bancoguayaquilactivaciontarjeta.ui.theme.AppModuleActivacionTarjetasAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipalConActivacion()
                }

        }
    }
}

@Composable
fun PantallaPrincipalConActivacion() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Header con tarjeta negra
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF171D9A))
                .padding(16.dp)
        ) {
            Column {
                // Tarjeta
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Imagen de tarjeta (simulada con color)
                        Text(
                            text = "Banco Guayaquil AAdvantage",
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            fontWeight = FontWeight.Bold
                        )
                        // Botón dentro de la tarjeta
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text(text = "Activa tu tarjeta", color = Color.Black)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Consumos a la fecha", color = Color.White)
                Text("$0.00", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Disponible: $4,000.00", color = Color.White)
            }
        }

        // Tabs (simulados)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("General", color = Color.Gray, modifier = Modifier.weight(1f))
            Text("Preferencias", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }

        // Opciones
        Column(modifier = Modifier.padding(16.dp)) {
            OpcionItem("Solicita una tarjeta adicional", "Comparte el cupo de tu tarjeta con una adicional")
            OpcionItem("Comprar por internet", "Estado: Activo", switch = true)
            OpcionItem("Solicitar clave", "Llegará por correo y SMS", botonTexto = "Enviar")
            ActivarTarjetaCard(tarjetasPendientes = 3)
        }
    }
}

@Composable
fun OpcionItem(titulo: String, subtitulo: String, switch: Boolean = false, botonTexto: String? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF0F0F0), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_info_details),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = titulo, fontWeight = FontWeight.Bold)
                Text(text = subtitulo, color = Color.Gray, fontSize = 12.sp)
            }

            if (switch) {
                Switch(checked = true, onCheckedChange = {})
            }

            botonTexto?.let {
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)) {
                    Text(it)
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ActivarTarjetaCardPreview() {


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        PantallaPrincipalConActivacion()
    }

}




