package com.bg.bancoguayaquilactivaciontarjeta.views.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bg.bancoguayaquilutils.theming.BancoWrapper


@Composable
fun PageWrapper(
    scrollable: Boolean = false,
    header: @Composable () -> Unit = {},
    footer: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = header,
        bottomBar = footer
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .let {
                    if (scrollable)
                        it.verticalScroll(rememberScrollState())
                    else it
                }
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PageWrapperPreview() {
    BancoWrapper {
        PageWrapper(
            header = {
                TopAppBar(
                    title = { Text("Header") },
                    colors = topAppBarColors(
                        containerColor = Color(0x81D5D5D5)
                    )
                )
            },
            footer = {
                BottomAppBar(containerColor = Color(0x81D5D5D5)) {
                    Text("Footer", modifier = Modifier.padding(8.dp))
                }
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Content")
            }
        }
    }
}
