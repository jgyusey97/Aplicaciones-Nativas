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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.bg.bancoguayaquilactivaciontarjeta.navigation.ActivationFlowHost
import com.bg.bancoguayaquilactivaciontarjeta.views.viewmodel.FlowControllerViewModel

import com.bg.bancoguayaquilutils.theming.BancoTheme
import com.bg.bancoguayaquilutils.theming.BancoWrapper

@Composable
fun ActivarTarjetaButton(viewModel: FlowControllerViewModel = viewModel()) {

    val state by viewModel.state.collectAsState()
    var showFlow by remember { mutableStateOf(false) }


    // Carga automática de tarjetas cuando aparece
    LaunchedEffect(Unit) {
        viewModel.cargarTarjetas("variasAdicionales")
    }

    BancoWrapper {
        ActivarTarjeta(
            tarjetasPendientes = state.tarjetas.size,
        )

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivarTarjeta(
    tarjetasPendientes: Int

) {
    var showModal by remember { mutableStateOf(false) }
    val showFlow = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BancoTheme.colors.background2
        ),

        onClick = {

            showFlow.value = true

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

                    fontWeight = FontWeight.Bold,
//                        color = Color(0xFF212121),
                    style = BancoTheme.typography.headingMedium
                )
                Text(
                    text = "Activa tus tarjetas y empieza a disfrutar de tus beneficios",
                    fontSize = 12.sp,
                    lineHeight = 15.sp,

                    style = BancoTheme.typography.body,
                    color = BancoTheme.colors.body
                )
            }

        }
    }
    if (showFlow.value) {
        ActivationFlowHost(onCloseFlow = {
            showFlow.value = false
        })
    }

    // ActivationFlowHost(viewModel = flowViewModel)


}

