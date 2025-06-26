package com.bg.bancoguayaquilactivaciontarjeta.views.modals
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bg.bancoguayaquilactivaciontarjeta.R

import com.bg.bancoguayaquilactivaciontarjeta.views.components.*
import com.bg.bancoguayaquilutils.theming.BancoTheme
import com.bg.bancoguayaquilutils.theming.BancoWrapper

data class CardData(
    val number: String,
    val owner: String,
    val date: String,
    val imageUrl: String? = null,
    val imageRes: Int? = null,
    val mode: CardItemMode = CardItemMode.DEFAULT
)


@Composable
fun ActivationModal(
    primaryCard: CardData? = null,
    aditionalCards: List<CardData> = emptyList(),
    onClose: () -> Unit,
    onContinue: () -> Unit
) {
    val maxCardsHeight = 320.dp // Altura máxima visible para las tarjetas
    BancoWrapper {

        Surface(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth()
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Portal de activación",
                        style =BancoTheme.typography.headingLarge


                    )


                    CloseCircleButton(onClick = onClose)

                }

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(
                    color = Color(0xFFE0E0E0),
                    thickness = 1.dp,
                  //  modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(Modifier.height(8.dp))

                // Badge
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFF0F5FE))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CardListHeader(aditionalCards.size)
                }

                Spacer(modifier = Modifier.height(16.dp))



                primaryCard?.let { card ->
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
                        requestedDate = card.date,
                        imageUrl = card.imageUrl,
                        imageRes = card.imageRes,
                        mode = card.mode
                    )
                }



                if (!aditionalCards.isEmpty()) {

                    HorizontalDivider(
                        color = Color(0xFFE0E0E0),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

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
                                mode = card.mode
                            )

                            // Agrega separador si NO es el último elemento
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



                Spacer(modifier = Modifier.height(16.dp))

                // Botones fijos
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier

                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    ActionButton(text = "Salir", onClick = onClose, containerColor = BancoTheme.colors.background, contentColor = BancoTheme.colors.title3 )
                    ActionButton(text = "Continuar", onClick = onContinue, containerColor = BancoTheme.colors.primary, contentColor = BancoTheme.colors.background)
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }

}






@Preview(showBackground = true)
@Composable
fun PreviewActivationModal() {
    ActivationModal(
        primaryCard =  CardData(
            number = "XXXX23",
            owner = "Carolina Romero",
            date =   "17/07/25",
            imageRes = R.drawable.avanti_card,
            mode = CardItemMode.CHECKABLE
        ),
        aditionalCards = listOf(
            CardData(
                number = "XXXX23",
                owner = "Carolina Romero",
                date =   "17/07/25",
                imageRes = R.drawable.avanti_card,
                mode = CardItemMode.DEFAULT
            ),
            CardData(
                number = "XXXX23",
                owner = "Carolina Romero",
                date =   "17/07/25",
                imageRes = R.drawable.avanti_card,
                mode = CardItemMode.CHECKABLE
            )

            
        ),
        onClose = {},
        onContinue = {}
    )
}
