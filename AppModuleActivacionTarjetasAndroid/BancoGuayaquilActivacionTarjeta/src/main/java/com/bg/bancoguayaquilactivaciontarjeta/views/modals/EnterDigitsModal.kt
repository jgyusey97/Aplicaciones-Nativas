package com.bg.bancoguayaquilactivaciontarjeta.views.modals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bg.bancoguayaquilactivaciontarjeta.views.components.*
import com.bg.bancoguayaquilutils.theming.BancoTheme
import com.bg.bancoguayaquilutils.theming.BancoWrapper
import com.bg.bancoguayaquilactivaciontarjeta.R
import com.bg.bancoguayaquilactivaciontarjeta.views.FlowControllerViewModel
import org.w3c.dom.Text

data class CarData(
    val number: String,
    val owner: String,
    val date: String,
    val imageUrl: String? = null,
    val imageRes: Int? = null,
    val mode: CardItemMode = CardItemMode.DEFAULT
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterDigitsModal(
    viewModel: FlowControllerViewModel = viewModel(),
    onClose: () -> Unit,
    onContinue: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val state by viewModel.state.collectAsState()
    val primaryCard = state.seleccionada

    val aditionalCards = state.adicionales


    var input by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    BancoWrapper {

        ModalBottomSheet(
            onDismissRequest = onClose,
            sheetState = sheetState,
            containerColor = BancoTheme.colors.background,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {

            Surface(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                // tonalElevation = 2.dp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            {

                Column(modifier = Modifier.fillMaxWidth()) {
                    // Header separado para permitir Divider a lo ancho
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Text(
                                text = "Activación de tarjeta",
                                style = BancoTheme.typography.headingLarge
                            )
                            CloseCircleButton(onClick = onClose)
                        }
                    }
                    Spacer(Modifier.height(12.dp))

                    HorizontalDivider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {


                        primaryCard?.let { card ->
                            Text(
                                text = "TARJETA TITULAR",
                                color = BancoTheme.colors.title4,
                                style = BancoTheme.typography.labelStrong,
                                modifier = Modifier.padding(start = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = buildAnnotatedString {
                                    append("Escribe los ")
                                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("6 últimos dígitos")
                                    }
                                    append(" de tu\n")
                                    append("tarjeta titular")
                                },

                                color = BancoTheme.colors.title,
                                style = BancoTheme.typography.headingMedium,
                                fontSize = 19.sp    ,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 14.dp)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            PendingCardItem(
                                cardNumber = card.tarjeta,
                                owner = card.nombrePlastico,
                                imageUrl = card.imagenUrl
                            )
                        }

                       if(primaryCard==null){


                           Text(
                               text = buildAnnotatedString {
                                   append("Escribe los ")
                                   withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                       append("6 últimos dígitos")
                                   }
                                   append(" de tu\n")
                                   append("tarjeta titular")
                               },
                               color = BancoTheme.colors.title,
                               style = BancoTheme.typography.headingMedium,
                               modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 14.dp)
                           )


                           if (aditionalCards.isNotEmpty()) {

                               if(aditionalCards.size >1){
                                   InfoMessageBanner(
                                       message = "Se activarán automáticamente todas las tarjetas adicionales seleccionadas."
                                   )

                               }else{
                                   InfoMessageBanner(
                                       message = "Para activar tu tarjeta adicional, necesitamos los datos de la tarjeta titular"
                                   )
                               }
                           }





                       }


                        Spacer(Modifier.height(14.dp))


                        SecureInputField(
                            value = text,
                            onValueChange = { text = it },
                            isError = false,
                            // errorMessage = "Número de tarjeta inválido. Verifica los 6 últimos dígitos."
                        )


                        Spacer(Modifier.height(25.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .align(Alignment.End)
                        ) {
                            ActionButton(
                                text = "Salir",
                                onClick = onClose,
                                containerColor = BancoTheme.colors.background,
                                contentColor = BancoTheme.colors.title3
                            )
                            ActionButton(
                                text = "Activar Tarjeta ",
                                onClick = onContinue,
                                containerColor = BancoTheme.colors.primary,
                                contentColor = BancoTheme.colors.background
                            )
                        }

                        Spacer(modifier = Modifier.height(25.dp))
                    }
                }
            }

        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEnterDigitsModal() {
    EnterDigitsModal(

        onClose = {},
        onContinue = {}
    )
}


