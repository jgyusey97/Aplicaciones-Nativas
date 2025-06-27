package com.bg.bancoguayaquilactivaciontarjeta.views.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bg.bancoguayaquilutils.theming.BancoTheme
import com.bg.bancoguayaquilutils.theming.BancoWrapper
import com.bg.bancoguayaquilactivaciontarjeta.R

object NoTextToolbar : TextToolbar {
    override val status: TextToolbarStatus
        get() = TextToolbarStatus.Hidden

    override fun showMenu(
        rect: androidx.compose.ui.geometry.Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
        // Do nothing
    }

    override fun hide() {
        // Do nothing
    }
}

@Composable
fun SecureInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Ingresa el código",
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var isVisible by rememberSaveable { mutableStateOf(false) }

    val backgroundColor = if (isError) Color(0xFFFFF3F3) else Color(0xFFF3F3F3)
    val borderColor = if (isError) Color.Red else Color.Transparent
    val contentColor = if (isError) Color.Red else Color.Black
    val placeholderColor = if (isError) Color.Red.copy(alpha = 0.7f) else Color.Gray

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor, shape = RoundedCornerShape(12.dp))
                .border(2.dp, borderColor, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = value,
                        onValueChange = {
                            if (it.length <= 6 && it.all(Char::isDigit)) onValueChange(it)
                        },
                        singleLine = true,
                        textStyle = BancoTheme.typography.body.copy(
                            color = contentColor,
                            textAlign = TextAlign.Start
                        ),
                        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        interactionSource = remember { MutableInteractionSource() },
                        decorationBox = { innerTextField ->
                            CompositionLocalProvider(LocalTextToolbar provides NoTextToolbar) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (value.isEmpty()) {
                                        Text(
                                            text = placeholder,
                                            style = BancoTheme.typography.body.copy(color = placeholderColor)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        }
                    )
                }

                Icon(
                    painter = painterResource(id = if (isError) R.drawable.alert else if (isVisible) R.drawable.lock_closed else R.drawable.eye),
                    contentDescription = if (isError) "Error" else "Toggle visibility",
                    tint = if (isError) Color.Red else Color.DarkGray,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable(enabled = !isError) { isVisible = !isVisible }
                )
            }
        }

        if (isError && errorMessage != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.alert),
                    contentDescription = "Error",
                    tint = Color.Red,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = errorMessage,
                    modifier = Modifier.fillMaxWidth(),
                    style = BancoTheme.typography.body.copy(color = Color.Red, fontSize = 12.sp)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Campo vacío")
@Composable
fun PreviewEmptyField() {
    var text by remember { mutableStateOf("") }
    BancoWrapper {
        SecureInputField(value = text, onValueChange = { text = it })
    }
}

@Preview(showBackground = true, name = "Campo con error")
@Composable
fun PreviewErrorField() {
    var text by remember { mutableStateOf("234523") }
    BancoWrapper {
        SecureInputField(
            value = text,
            onValueChange = { text = it },
            isError = true,
            errorMessage = "Número de tarjeta inválido. Verifica los 6 últimos dígitos."
        )
    }
}

@Preview(showBackground = true, name = "Campo con texto oculto")
@Composable
fun PreviewFilledHiddenField() {
    var text by remember { mutableStateOf("123456") }
    BancoWrapper {
        SecureInputField(
            value = text,
            onValueChange = { text = it },
            placeholder = "Cédula o código"
        )
    }
}

@Preview(showBackground = true, name = "Campo con texto visible")
@Composable
fun PreviewFilledVisibleField() {
    var text by remember { mutableStateOf("123456") }
    BancoWrapper {
        SecureInputField(
            value = text,
            onValueChange = { text = it },
            placeholder = "Cédula o código"
        )
    }
}
