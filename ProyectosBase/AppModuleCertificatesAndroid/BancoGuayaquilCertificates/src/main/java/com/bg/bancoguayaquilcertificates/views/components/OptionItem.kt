package com.bg.bancoguayaquilcertificates.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme

enum class OptionItemVariant {
    Item,
    Radio
}

@Composable
fun OptionItem(
    text: String,
    description: String? = null,
    onSelect: (() -> Unit)? = null,
    variant: OptionItemVariant = OptionItemVariant.Item,
    selected: Boolean = false,
    badgeText: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = { onSelect?.invoke() }
                )
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column {
                    Text(
                        text = text,
                        fontWeight = FontWeight.Medium
                    )

                    description?.let {
                        Text(
                            text = it,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

                badgeText?.let {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = Color(0xFFF0F5FE))
                    ) {
                        Text(
                            text = badgeText,
                            color = Color(0xFF0F62FE),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(all = 5.dp)
                        )
                    }
                }
            }

            when (variant) {
                OptionItemVariant.Item -> {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Ir",
                        tint = Color.Gray
                    )
                }

                OptionItemVariant.Radio -> {
                    RadioButton(
                        selected = selected,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFFD2006E)
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OptionItemPreview() {
    CertificatesTheme {
        Column() {
            OptionItem("Item 1")
            OptionItem("Item 2", description = "Some description")
            OptionItem(
                "Radio Item",
                variant = OptionItemVariant.Radio,
                selected = true,
                badgeText = "Recomendado"
            )
        }
    }
}
