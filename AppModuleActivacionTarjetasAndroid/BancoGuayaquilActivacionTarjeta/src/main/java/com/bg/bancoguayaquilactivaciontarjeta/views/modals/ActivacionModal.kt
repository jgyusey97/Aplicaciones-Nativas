package com.bg.bancoguayaquilactivaciontarjeta.views.modals
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    cards: List<CardData>,
    onClose: () -> Unit,
    onContinue: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Portal de activación",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFF0F5FE))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardListHeader(1)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "TARJETA TITULAR",
                color = Color(0xFF0056F2),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 8.dp)
            )

            cards.forEach {
                PendingCardItem(
                    cardNumber = it.number,
                    owner = it.owner,
                    requestedDate = it.date,
                    imageUrl = it.imageUrl,
                    imageRes = it.imageRes,
                    mode = it.mode
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                ActionButton(text = "Salir", onClick = onClose, enabled = false)
                ActionButton(text = "Continuar", onClick = onContinue)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewActivationModal() {
    ActivationModal(
        cards = listOf(
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
