// Estructura inicial del módulo activaciontarjeta

package com.bg.bancoguayaquilactivaciontarjeta

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
import com.bg.bancoguayaquilactivaciontarjeta.ui.theme.ActivacionTarjetasTheme

@Composable
fun ActivacionMainUI() {
    var tarjetasPendientes by remember { mutableStateOf(0) }

    // Llamada rápida a la consulta de tarjetas pendientes
    LaunchedEffect(Unit) {
        tarjetasPendientes = consultarTarjetasPendientes()
    }
    ActivacionTarjetasTheme {
        ActivarTarjetaCard(
            tarjetasPendientes = tarjetasPendientes
        )
    }

}

private fun consultarTarjetasPendientes(): Int {
    // Aquí puedes reemplazarlo con lógica real más adelante
    return 1 // Simulación de 2 tarjetas por activar
}

@Composable
fun ActivarTarjetaCard(
    tarjetasPendientes: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        onClick = {
            // Aquí se puede navegar al flujo de activación más adelante
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(48.dp)) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_lock_idle_lock),
                    contentDescription = "Icono de tarjeta",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    tint = Color.Black
                )
                if (tarjetasPendientes > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50))
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tarjetasPendientes.toString(),
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Activar tarjeta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121)
                )
                Text(
                    text = "Activa tus tarjetas y empieza a disfrutar de tus beneficios",
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivarTarjetaCardPreview() {


        ActivarTarjetaCard(tarjetasPendientes = 2)

}
