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
import com.bg.bancoguayaquilactivaciontarjeta.ui.theme.backgroundColor2
import com.bg.bancoguayaquilactivaciontarjeta.views.components.CardItemMode
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.*

import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.material3.ModalBottomSheet


@Composable
fun ActivarTarjetaCard() {
    var tarjetasPendientes by remember { mutableStateOf(0) }

    // Llamada rápida a la consulta de tarjetas pendientes
    LaunchedEffect(Unit) {
        tarjetasPendientes = consultarTarjetasPendientes()
    }

    ActivarTarjetaButton(
            tarjetasPendientes = tarjetasPendientes
        )


}

private fun consultarTarjetasPendientes(): Int {
    // Aquí puedes reemplazarlo con lógica real más adelante
    return 1 // Simulación de 2 tarjetas por activar
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivarTarjetaButton(
    tarjetasPendientes: Int
) {
    var showModal by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor2
        ),

            onClick = { showModal = true }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(48.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.card),
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

    if (showModal) {
        ModalBottomSheet  (
            onDismissRequest = { showModal = false },
            sheetState = bottomSheetState,
            containerColor = Color.Transparent // para que tu Surface controle el diseño
        ) {
            ActivationModal(
                cards = listOf(
                    CardData(
                        number = "XXXX23",
                        owner = "Carolina Romero",
                        date = "",
                        imageRes = R.drawable.avanti_card,
                        mode = CardItemMode.DEFAULT
                    )
                ),
                onClose = { showModal = false },
                onContinue = { /* acción */ }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivarTarjetaCardPreview() {

  ActivacionTarjetasTheme {
      ActivarTarjetaCard()
  }


}
