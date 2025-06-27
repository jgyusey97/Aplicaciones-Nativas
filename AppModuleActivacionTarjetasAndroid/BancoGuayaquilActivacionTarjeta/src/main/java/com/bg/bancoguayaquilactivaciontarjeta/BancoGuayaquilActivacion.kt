package com.bg.bancoguayaquilactivaciontarjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import com.bg.bancoguayaquilactivaciontarjeta.navigation.ActivationFlowHost
import com.bg.bancoguayaquilutils.theming.BancoWrapper

class ActivacionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivacionScreen()
        }
    }
}

@Composable
fun ActivacionScreen() {
    var showFlow by remember { mutableStateOf(true) }

    BancoWrapper {
        Surface(modifier = Modifier.fillMaxSize()) {
            if (showFlow) {
                ActivationFlowHost(
                    onCloseFlow = {
                        showFlow = false // Cierra el flujo
                    }
                )
            } else {
                Text("Flujo cerrado")
            }
        }
    }
}


