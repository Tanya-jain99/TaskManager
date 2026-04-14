package com.tanya.planner.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanya.planner.design.grey5
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeAppBar(
    name: String,
) {
    Column {
        Text(
            text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd")),
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF616F89),
            )
        )
        Text(
            text = "Good morning, $name",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                color = Color(0xFF111318),
            )
        )
        Spacer(
            modifier = Modifier.height(8.dp),
        )
        HorizontalDivider(
            color = grey5,
        )
    }
}

@Preview
@Composable
private fun PreviewHomeAppBar() {
    HomeAppBar(name = "Tanya")
}