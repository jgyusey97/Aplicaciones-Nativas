package com.bg.bancoguayaquilactivaciontarjeta.views.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bg.bancoguayaquilactivaciontarjeta.R
import com.bg.bancoguayaquilutils.theming.BancoColorScheme
import com.bg.bancoguayaquilutils.theming.BancoTheme
import com.bg.bancoguayaquilutils.theming.BancoWrapper
import com.bg.bancoguayaquilutils.theming.extensions.toCapitalize

enum class CardItemMode {
    DEFAULT, CHECKABLE, LOCKED
}

@Composable
fun PendingCardItem(
    cardNumber: String,

    owner: String,
    requestedDate: String? =null,
    imageUrl: String? = null,
    imageRes: Int? = null,
    mode: CardItemMode = CardItemMode.DEFAULT
) {
    var checked by remember { mutableStateOf(false) }
    val isLocked = mode == CardItemMode.LOCKED
    val alpha = if (isLocked) 0.3f else 1f

    BancoWrapper {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (mode != CardItemMode.DEFAULT) {
                Box(
                    modifier = Modifier
                        .size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when (mode) {
                        CardItemMode.CHECKABLE -> {
                            Checkbox(
                                checked = checked,

                                onCheckedChange = { checked = it },
                                colors = CheckboxDefaults.colors(
                                    uncheckedColor = Color(0xFF4C4C5A),
                                    checkedColor = BancoTheme.colors.primary
                                )
                            )
                        }
                        CardItemMode.LOCKED -> {
                            Icon(
                                painter = painterResource(id = R.drawable.lock_closed),
                                contentDescription = "Locked",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        else -> {}
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
            }

            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(64.dp)
                        .alpha(alpha)
                )
            } else if (imageRes != null) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(64.dp)
                        .alpha(alpha)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = cardNumber,
                    style =BancoTheme.typography.subtitle,
             //       style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.alpha(alpha)
                )
                Text(
                    text = owner.toCapitalize(),
                    color = BancoTheme.colors.body ,
                    style = BancoTheme.typography.body,

                    modifier = Modifier.alpha(alpha)
                )
                if (!requestedDate.isNullOrEmpty()) {
                    Text(
                        text = "Solicitada el $requestedDate",
                        style = MaterialTheme.typography.bodySmall,
                        color = BancoTheme.colors.body2,
                        modifier = Modifier.alpha(alpha)
                    )
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewDefaultCardItem() {
    PendingCardItem(
        cardNumber = "XXXX23",
        owner = "Carolina Romero",

        imageRes = R.drawable.avanti_card,
        mode = CardItemMode.DEFAULT
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckableCardItem() {
    PendingCardItem(
        cardNumber = "XXXX45",
        owner = "Carolina Romero",
        requestedDate = "17/07/25",
        imageRes = R.drawable.avanti_card,
        mode = CardItemMode.CHECKABLE
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLockedCardItem() {
    PendingCardItem(
        cardNumber = "XXXX45",
        owner = "Julia Romero",
        requestedDate = "17/07/25",
        imageRes = R.drawable.avanti_card,
        mode = CardItemMode.LOCKED
    )
}

