package com.bg.bancoguayaquilactivaciontarjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bg.bancoguayaquilactivaciontarjeta.ui.theme.AppModuleActivacionTarjetasAndroidTheme
import com.bg.bancoguayaquilactivaciontarjeta.ActivarTarjetaCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppModuleActivacionTarjetasAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Aquí llamas el componente que viene del módulo de activación
                    ActivacionMainUI()
                }
            }
        }
    }
}

@Composable
fun ActivacionTarjetaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        ActivacionMainUI()
    }
}

