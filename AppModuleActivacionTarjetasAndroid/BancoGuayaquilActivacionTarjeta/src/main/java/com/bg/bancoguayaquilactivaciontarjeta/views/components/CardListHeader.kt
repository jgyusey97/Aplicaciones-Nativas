package com.bg.bancoguayaquilactivaciontarjeta.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bg.bancoguayaquilactivaciontarjeta.R
import com.bg.bancoguayaquilutils.theming.BancoTheme

import com.bg.bancoguayaquilutils.theming.BancoWrapper

@Composable
fun CardListHeader(pendingCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BancoTheme.colors.background3,
                shape = RoundedCornerShape(37.5.dp)
            )
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "TARJETAS PENDIENTES",
            style = BancoTheme.typography.headingMedium



        )

        Box(contentAlignment = Alignment.TopEnd) {
            Icon(
                painter = painterResource(id = R.drawable.card),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            if (pendingCount > 0) {
                Box(
                    modifier = Modifier
                        .offset(x = 6.dp, y = (-6).dp)
                        .size(16.dp)
                        .background(Color(0xFF32A85C), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$pendingCount",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CardListHeaderPreview() {

    BancoWrapper {
        CardListHeader(1)
    }


}