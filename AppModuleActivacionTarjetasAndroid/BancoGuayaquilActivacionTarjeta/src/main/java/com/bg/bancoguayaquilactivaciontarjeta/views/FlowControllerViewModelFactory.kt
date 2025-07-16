package com.bg.bancoguayaquilactivaciontarjeta.views

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bg.bancoguayaquilactivaciontarjeta.services.ActivacionService

class FlowControllerViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val service = ActivacionService(context)
        return FlowControllerViewModel(service) as T
    }
}

