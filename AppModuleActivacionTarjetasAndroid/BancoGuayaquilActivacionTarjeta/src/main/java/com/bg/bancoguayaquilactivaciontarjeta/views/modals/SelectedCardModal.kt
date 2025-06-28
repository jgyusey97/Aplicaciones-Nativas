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
import androidx.compose.ui.unit.dp
import com.bg.bancoguayaquilactivaciontarjeta.views.components.*
import com.bg.bancoguayaquilutils.theming.BancoTheme
import com.bg.bancoguayaquilutils.theming.BancoWrapper

data class CardData(
    val number: String,
    val owner: String,
    val date: String,
    val imageUrl: String? = null,
    val imageRes: Int? = null,

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedCardModal(
    primaryCard: CardData? = null,
    aditionalCards: List<CardData> = emptyList(),
    onClose: () -> Unit,
    onContinue: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var pendientCards =0

    var mode: CardItemMode = CardItemMode.DEFAULT

    if (primaryCard != null) {
        pendientCards =1
    }
    val maxCardsHeight = 320.dp // Altura máxima visible para las tarjetas
    BancoWrapper {

        ModalBottomSheet(
            onDismissRequest = onClose,
            sheetState = sheetState,
            containerColor = BancoTheme.colors.background,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            var blockAditionals by remember { mutableStateOf(false) }
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
                                text = "Activación de tarjetas",
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



                        // Badge

                        CardListHeader(pendientCards+aditionalCards.size)


                        Spacer(modifier = Modifier.height(12.dp))

                        primaryCard?.let { card ->

                            if (aditionalCards.isNotEmpty()) {
                                mode  = CardItemMode.CHECKABLE
                            }

                            Text(
                                text = "TARJETA TITULAR",
                                color = BancoTheme.colors.title4,
                                style = BancoTheme.typography.labelStrong,
                                modifier = Modifier.padding(start = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            PendingCardItem(
                                cardNumber = card.number,
                                owner = card.owner,

                                imageUrl = card.imageUrl,
                                imageRes = card.imageRes,
                                mode = mode
                            )
                        }

                        if (aditionalCards.isNotEmpty()) {


                            if(primaryCard!=null){

                                HorizontalDivider(
                                    color = Color(0xFFE0E0E0),
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                mode =CardItemMode.LOCKED

                            }else {

                                mode =CardItemMode.CHECKABLE
                            }

                            Text(
                                text = "TARJETAS ADICIONALES",
                                color = BancoTheme.colors.title4,
                                style = BancoTheme.typography.labelStrong,
                                modifier = Modifier.padding(start = 8.dp)
                            )

                            // Lista scrollable con altura máxima
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = maxCardsHeight)
                            ) {
                                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                                    aditionalCards.forEachIndexed { index, card ->
                                        PendingCardItem(
                                            cardNumber = card.number,
                                            owner = card.owner,
                                            requestedDate = card.date,
                                            imageUrl = card.imageUrl,
                                            imageRes = card.imageRes,
                                            mode = mode
                                        )

                                        if (index < aditionalCards.lastIndex) {
                                            HorizontalDivider(
                                                color = Color(0xFFE0E0E0),
                                                thickness = 1.dp,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }



                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            ActionButton(
                                text = "Salir",
                                onClick = onClose,
                                containerColor = BancoTheme.colors.background,
                                contentColor = BancoTheme.colors.title3
                            )
                            ActionButton(
                                text = "Continuar",
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
/*
@Preview(showBackground = true)
@Composable
fun PreviewActivationModal() {
    ActivationModal(
        primaryCard = CardData(
            number = "XXXX23",
            owner = "Carolina Romero",
            date = "17/07/25",
            imageRes = R.drawable.avanti_card,
            mode = CardItemMode.CHECKABLE
        ),
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
        ),
        onClose = {},
        onContinue = {}
    )
}

 */
