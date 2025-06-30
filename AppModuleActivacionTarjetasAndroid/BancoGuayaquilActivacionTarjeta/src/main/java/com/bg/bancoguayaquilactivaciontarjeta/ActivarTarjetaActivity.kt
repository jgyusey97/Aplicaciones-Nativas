package com.bg.bancoguayaquilactivaciontarjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.bg.bancoguayaquilutils.theming.BancoWrapper

//Activity que se debe de llamar desde Capacitor para poder exponer el boton que va a iniciar el flujo
class ActivarTarjetaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BancoWrapper {
                ActivarTarjetaButton()
            }
        }
    }
}
