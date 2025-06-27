package com.bg.bancoguayaquilactivaciontarjeta.viewmodel

import androidx.lifecycle.ViewModel
import com.bg.bancoguayaquilactivaciontarjeta.navigation.ActivationStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FlowControllerViewModel : ViewModel() {
    val step = MutableStateFlow(ActivationStep.SELECT_CARD)
    fun goTo(next: ActivationStep) {
        step.value = next
    }
}
