package com.bg.bancoguayaquilactivaciontarjeta.views.modals

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Dialog
import com.bg.bancoguayaquilactivaciontarjeta.views.components.PageWrapper
import com.bg.bancoguayaquilutils.theming.BancoWrapper

@Composable
fun FullScreenModal(
    isOpen: Boolean,
    onDismiss: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    if (isOpen) {
        Dialog(
            onDismissRequest = { onDismiss?.invoke() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false,
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = modifier.fillMaxSize()
                ) {
                    PageWrapper(
                        header = header,
                        footer = footer,
                        content = content
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenModalPreview() {
    BancoWrapper {
        FullScreenModal(true) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("FullScreen Modal")
            }
        }
    }
}
