package com.bg.bancoguayaquilactivaciontarjeta.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bg.bancoguayaquilactivaciontarjeta.models.ActivationStep
import com.bg.bancoguayaquilactivaciontarjeta.models.TarjetaPendienteActivacion
import com.bg.bancoguayaquilactivaciontarjeta.services.ActivacionService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ActivationState(
    val step: ActivationStep = ActivationStep.SELECT_CARD,
    val tarjetas: List<TarjetaPendienteActivacion> = emptyList(),
    val principal: TarjetaPendienteActivacion? = null,
    val adicionales: List<TarjetaPendienteActivacion> = emptyList(),
    val seleccionada: TarjetaPendienteActivacion? = null,
    val error: String? = null
)

class FlowControllerViewModel(
    private val service: ActivacionService
) : ViewModel() {

    private val _state = MutableStateFlow(ActivationState())
    val state: StateFlow<ActivationState> = _state

    fun cargarTarjetas(nombreEscenario: String) {
        viewModelScope.launch {
            val tarjetas = service.getTarjetasPendientes(nombreEscenario)

            if (tarjetas.isNotEmpty()) {

                val principal = tarjetas.find { it.princiadicio == "P" }
                val adicionales = tarjetas.filter { it.princiadicio == "A" }

                //Actualiza las listas dentro del flow
                _state.update {
                    it.copy(
                        tarjetas = tarjetas,
                        principal = principal,
                        adicionales = adicionales,
                        step = ActivationStep.SELECT_CARD
                    )
                }


            }

            _state.update {
                it.copy(tarjetas = tarjetas, step = ActivationStep.SELECT_CARD)
            }
        }
    }

    fun seleccionarTarjeta(tarjeta: TarjetaPendienteActivacion) {
        _state.update {
            it.copy(seleccionada = tarjeta, step = ActivationStep.ENTER_DIGITS)
        }
    }

    fun validarCodigo(codigo: String) {

        /*
        val tarjeta = _state.value.seleccionada
        if (tarjeta?.codigoEsperado == codigo) {
            _state.update { it.copy(step = ActivationStep.SUCCESS, error = null) }
        } else {
            _state.update { it.copy(error = "Código incorrecto") }
        }

         */
    }

    fun goTo(step: ActivationStep) {
        _state.update { it.copy(step = step) }
    }

    fun resetFlow() {
        _state.value = ActivationState()
    }
}
