package com.bg.bancoguayaquilcertificates.views.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme

data class SegmentOption(
    val id: String,
    val value: String,
)

@Composable
fun SegmentOptions(
    options: List<SegmentOption>,
    value: String? = null,
    onSelect: ((item: SegmentOption) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier.fillMaxWidth()
    ) {
        options.forEachIndexed { index, opt ->
            val isSelected = opt.id == value

            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index, count = options.size),
                onClick = { onSelect?.invoke(opt) },
                selected = isSelected,
                colors = SegmentedButtonDefaults.colors(
                    activeContentColor = Color(0xFFD2006E),
                    activeContainerColor = Color(0xFFFAE5F0),
                    inactiveContainerColor = Color.White,
                )
            ) {
                Text(opt.value)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SegmentOptionsPreview() {
    val options = listOf(
        SegmentOption(id = "1", value = "Opcion 1"),
        SegmentOption(id = "2", value = "Opcion 2"),
    )

    CertificatesTheme {
        SegmentOptions(
            value = "1",
            options = options,
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}
