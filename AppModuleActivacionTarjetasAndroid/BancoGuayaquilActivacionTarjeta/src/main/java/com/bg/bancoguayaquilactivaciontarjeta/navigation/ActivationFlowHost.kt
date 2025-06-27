package com.bg.bancoguayaquilactivaciontarjeta.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bg.bancoguayaquilactivaciontarjeta.viewmodel.FlowControllerViewModel
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.SelectedCardModal
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.CardData
import com.bg.bancoguayaquilactivaciontarjeta.R
import com.bg.bancoguayaquilactivaciontarjeta.views.FaceVerificationScreen
import com.bg.bancoguayaquilactivaciontarjeta.views.components.CardItemMode
import com.bg.bancoguayaquilactivaciontarjeta.views.components.InfoMessageBanner
import com.bg.bancoguayaquilactivaciontarjeta.views.components.SuccessMessagePopup
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.EnterDigitsModal
import com.bg.bancoguayaquilactivaciontarjeta.views.modals.FullScreenModal
import com.bg.bancoguayaquilutils.theming.BancoTheme

@Composable
fun ActivationFlowHost( onCloseFlow: () -> Unit,viewModel: FlowControllerViewModel = viewModel()) {
    val step by viewModel.step.collectAsState()
    var showModal by remember { mutableStateOf(true) } // <- control del modal

    when (step) {
        ActivationStep.SELECT_CARD -> {
            SelectedCardModal(
                primaryCard = CardData(
                    number = "XXXX23",
                    owner = "Carolina Romero",
                    date = "17/07/25",
                    imageRes = R.drawable.avanti_card,
                    mode = CardItemMode.DEFAULT
                ),
                /*
                aditionalCards = listOf(
                    CardData(
                        number = "XXXX23",
                        owner = "Carolina Romero",
                        date = "17/07/25",
                        imageRes = R.drawable.avanti_card,
                        mode = CardItemMode.DEFAULT
                    ),
                    CardData(
                        number = "XXXX23",
                        owner = "Carolina Romero",
                        date = "17/07/25",
                        imageRes = R.drawable.avanti_card,
                        mode = CardItemMode.CHECKABLE
                    )
                ),*/
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

            FullScreenModal(
                 isOpen = true
            ) {

                FaceVerificationScreen(
                    imageRes = R.drawable.logo_facephi, // usa una imagen dummy
                    onClose = {

                        viewModel.goTo(ActivationStep.ENTER_DIGITS)
                    }
                )
            }



        }


        ActivationStep.ENTER_DIGITS -> {

            EnterDigitsModal (
                primaryCard = CardData(
                    number = "XXXX23",
                    owner = "Carolina Romero",
                    date = "17/07/25",
                    imageRes = R.drawable.avanti_card,
                    mode = CardItemMode.DEFAULT
                ),
                onClose = {
                    viewModel.resetFlow()
                    onCloseFlow()
                },
                onContinue = {
                    viewModel.goTo(ActivationStep.SUCCESS)
                })


        }

        ActivationStep.SUCCESS -> {



             FullScreenModal(

                 isOpen = true
             ) {


                 SuccessMessagePopup(
                     title = "Tarjeta activada exitosamente",
                     message = buildAnnotatedString {
                         append("Tus tarjeta ya está lista.\nAgrégala a tu ")
                         withStyle(BancoTheme.typography.labelStrong.toSpanStyle()) {
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


        }


        else -> {
            // Placeholder por si se expande luego
        }
    }
}
