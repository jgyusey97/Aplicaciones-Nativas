package com.bg.bancoguayaquilcertificates.views.modals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bg.bancoguayaquilcertificates.extensions.toTitleCase
import com.bg.bancoguayaquilcertificates.models.ProductCertOption
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme
import com.bg.bancoguayaquilcertificates.views.components.OptionItem

@Composable
fun ProductsModal(
    isOpen: Boolean,
    products: List<ProductCertOption>,
    onSelectProduct: ((product: ProductCertOption) -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
) {
    FullScreenModal(
        isOpen = isOpen,
        onDismiss = onDismiss,
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Productos",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )

                IconButton(onClick = { onDismiss?.invoke() }) {
                    Icon(
                        Icons.Filled.Close,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Close"
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(products) { item ->
                    OptionItem(
                        text = item.productName.toTitleCase(),
                        description = item.productDesc,
                        onSelect = { onSelectProduct?.invoke(item) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductsModalPreview() {
    val mockProducts = List(15) {
        ProductCertOption(
            productCode = "0237273283$it",
            productName = "CUENTA DE AHORROS",
            productDesc = "0237273283${it + 1}"
        )
    }

    CertificatesTheme {
        ProductsModal(
            isOpen = true,
            products = mockProducts,
        )
    }
}
