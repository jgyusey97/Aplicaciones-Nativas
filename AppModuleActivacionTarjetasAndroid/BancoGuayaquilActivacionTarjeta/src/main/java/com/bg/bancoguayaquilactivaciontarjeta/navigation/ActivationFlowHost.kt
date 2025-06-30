package com.bg.bancoguayaquilactivaciontarjeta.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bg.bancoguayaquilactivaciontarjeta.views.viewmodel.FlowControllerViewModel
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.SelectedCardModal
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.CardData
import com.bg.bancoguayaquilactivaciontarjeta.R
import com.bg.bancoguayaquilactivaciontarjeta.models.ActivationStep
import com.bg.bancoguayaquilactivaciontarjeta.views.FaceVerificationScreen
import com.bg.bancoguayaquilactivaciontarjeta.views.components.SuccessMessagePopup
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.EnterDigitsModal
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.FullScreenModal

@Composable
fun ActivationFlowHost(
    onCloseFlow: () -> Unit,
    viewModel: FlowControllerViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    val titular = state.tarjetas.find { it.princiadicio == "P" }
    val adicionales = state.tarjetas.filter { it.princiadicio == "A" }


    when (state.step) {
        ActivationStep.SELECT_CARD -> {
            SelectedCardModal(
                viewModel = viewModel,
                onClose = {
                    viewModel.resetFlow()
                    onCloseFlow()
                },
                onContinue = {
                    viewModel.goTo(ActivationStep.FACE_VERIFICATION)
                }
            )
        }

        ActivationStep.FACE_VERIFICATION -> {
            FullScreenModal(isOpen = true) {
                FaceVerificationScreen(
                    imageRes = R.drawable.logo_facephi,
                    onClose = {
                        viewModel.goTo(ActivationStep.ENTER_DIGITS)
                    }
                )
            }
        }

        ActivationStep.ENTER_DIGITS -> {
            EnterDigitsModal(
                viewModel = viewModel,
                onClose = {
                    viewModel.resetFlow()
                    onCloseFlow()
                },
                onContinue = {
                    viewModel.goTo(ActivationStep.SUCCESS)
                }
            )
        }

        ActivationStep.SUCCESS -> {
            SuccessMessagePopup(
                title = "Tarjeta activada exitosamente",
                message = buildAnnotatedString {
                    append("Tus tarjetas ya están listas.\nAgrégalas a tu ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("billetera digital")
                    }
                    append(" y llévalas en tu celular.")
                },
                buttonText = "Entendido",
                onClose = {
                    viewModel.resetFlow()
                    onCloseFlow()
                }
            )
        }

        else -> {}
    }
}
