package com.bg.bancoguayaquilactivaciontarjeta.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bg.bancoguayaquilactivaciontarjeta.viewmodel.FlowControllerViewModel
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.SelectedCardModal
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.CardData
import com.bg.bancoguayaquilactivaciontarjeta.R
import com.bg.bancoguayaquilactivaciontarjeta.viewmodel.PendientesActivacionViewModel
import com.bg.bancoguayaquilactivaciontarjeta.views.FaceVerificationScreen
import com.bg.bancoguayaquilactivaciontarjeta.views.components.CardItemMode
import com.bg.bancoguayaquilactivaciontarjeta.views.components.InfoMessageBanner
import com.bg.bancoguayaquilactivaciontarjeta.views.components.SuccessMessagePopup
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.EnterDigitsModal
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.FullScreenModal
import com.bg.bancoguayaquilutils.theming.BancoTheme

@Composable
fun ActivationFlowHost(
    onCloseFlow: () -> Unit,
    flowViewModel: FlowControllerViewModel = viewModel(),
    pendientesViewModel: PendientesActivacionViewModel = viewModel()
) {
    val step by flowViewModel.step.collectAsState()
    val tarjetas by pendientesViewModel.tarjetas.collectAsState()

    // Cargar data de prueba solo una vez
    LaunchedEffect(Unit) {
        pendientesViewModel.cargarEscenario("titularYAdicional")
    }
    val titular = tarjetas.find { it.princiadicio == "P" }
    val adicionales = tarjetas.filter { it.princiadicio == "A" }
    when (step) {
        ActivationStep.SELECT_CARD -> {


            SelectedCardModal(
                primaryCard = titular?.let {
                    CardData(
                        number = it.tarjeta,
                        owner = it.nombrePlastico,
                        date = "17/07/25",
                        imageRes = R.drawable.avanti_card
                    )
                },
                aditionalCards = adicionales.map {
                    CardData(
                        number = it.tarjeta,
                        owner = it.nombrePlastico,
                        date = "17/07/25",
                        imageRes = R.drawable.avanti_card,

                    )
                },
                onClose = {
                    flowViewModel.resetFlow()
                    onCloseFlow()
                },
                onContinue = {
                    flowViewModel.goTo(ActivationStep.FACE_VERIFICATION)
                }
            )
        }

        ActivationStep.FACE_VERIFICATION -> {
            FullScreenModal(isOpen = true) {
                FaceVerificationScreen(
                    imageRes = R.drawable.logo_facephi,
                    onClose = {
                        flowViewModel.goTo(ActivationStep.ENTER_DIGITS)
                    }
                )
            }
        }

        ActivationStep.ENTER_DIGITS -> {
            val titular = tarjetas.find { it.princiadicio == "P" }

            EnterDigitsModal(
                primaryCard = titular?.let {
                    CardData(
                        number = it.tarjeta,
                        owner = it.nombrePlastico,
                        date = "17/07/25",
                        imageRes = R.drawable.avanti_card
                    )
                },

                aditionalCards = adicionales.map {
                    CardData(
                        number = it.tarjeta,
                        owner = it.nombrePlastico,
                        date = "17/07/25",
                        imageRes = R.drawable.avanti_card,

                        )
                },
                onClose = {
                    flowViewModel.resetFlow()
                    onCloseFlow()
                },
                onContinue = {
                    flowViewModel.goTo(ActivationStep.SUCCESS)
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
                    flowViewModel.resetFlow()
                    onCloseFlow()
                }
            )
        }

        else -> {}
    }
}

