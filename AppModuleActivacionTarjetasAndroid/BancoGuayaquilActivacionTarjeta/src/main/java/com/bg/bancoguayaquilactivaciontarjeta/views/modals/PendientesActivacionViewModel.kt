package com.bg.bancoguayaquilactivaciontarjeta.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bg.bancoguayaquilactivaciontarjeta.models.TarjetaPendienteActivacion
import com.bg.bancoguayaquilactivaciontarjeta.repository.PendientesActivacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PendientesActivacionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PendientesActivacionRepository(application)

    private val _tarjetas = MutableStateFlow<List<TarjetaPendienteActivacion>>(emptyList())
    val tarjetas: StateFlow<List<TarjetaPendienteActivacion>> = _tarjetas

    fun cargarEscenario(nombre: String) {
        viewModelScope.launch {
            _tarjetas.value = repository.cargarEscenario(nombre)
        }
    }
}
