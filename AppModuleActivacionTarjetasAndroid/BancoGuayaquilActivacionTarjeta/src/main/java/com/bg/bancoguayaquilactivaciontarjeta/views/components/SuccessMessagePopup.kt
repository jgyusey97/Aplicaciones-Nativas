package com.bg.bancoguayaquilactivaciontarjeta.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bg.bancoguayaquilactivaciontarjeta.R
import com.bg.bancoguayaquilutils.theming.BancoTheme
import com.bg.bancoguayaquilutils.theming.BancoWrapper

@Composable
fun SuccessMessagePopup(
    title: String,
    message: AnnotatedString,
    buttonText: String,
    onClose: () -> Unit,
    maxWidth: Dp = 340.dp
) {
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .widthIn(max = maxWidth)
                .background(Color.White, RoundedCornerShape(28.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onClose) {
                        Icon(
                            painter = painterResource(id = R.drawable.salir),
                            contentDescription = "Cerrar",
                            tint = BancoTheme.colors.title
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = Color(0xFF00B150), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.success),
                        contentDescription = "Éxito",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = title,
                    style = BancoTheme.typography.headingLarge,
                    color = BancoTheme.colors.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = message,
                    style = BancoTheme.typography.body,
                    color = BancoTheme.colors.body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                ActionButton(
                    text = buttonText,
                    onClick = onClose,
                    containerColor = BancoTheme.colors.primarySoft,
                    contentColor = BancoTheme.colors.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuccessMessagePopup() {
    BancoWrapper {
        SuccessMessagePopup(
            title = "Tarjetas activadas exitosamente",
            message = buildAnnotatedString {
                append("Tus tarjetas ya están listas.\nAgrégalas a tu ")
                withStyle(BancoTheme.typography.labelStrong.toSpanStyle()) {
                    append("billetera digital")
                }
                append(" y llévalas en tu celular.")
            },
            buttonText = "Entendido",
            onClose = {}
        )
    }
}
