package com.tanya.planner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanya.planner.design.white100

@Composable
fun TaskSummaryCard(
    title: String,
    icon: ImageVector,
    count: Long,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.aspectRatio(1f),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Diagonal gradient overlay: top-start highlight → bottom-end shadow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                white100.copy(alpha = 0.22f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.18f),
                            )
                        )
                    )
            )

            // Decorative background circles
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .offset(x = 50.dp, y = (-35).dp)
                    .clip(CircleShape)
                    .background(white100.copy(alpha = 0.10f))
                    .align(Alignment.TopEnd)
            )
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .offset(x = 25.dp, y = 30.dp)
                    .clip(CircleShape)
                    .background(white100.copy(alpha = 0.07f))
                    .align(Alignment.BottomEnd)
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(white100.copy(alpha = 0.20f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = white100,
                        modifier = Modifier.size(22.dp),
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = count.toString(),
                    color = white100,
                    fontWeight = FontWeight.W800,
                    fontSize = 38.sp,
                    lineHeight = 38.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    color = white100.copy(alpha = 0.80f),
                    fontWeight = FontWeight.W500,
                    fontSize = 13.sp,
                )
            }
        }
    }
}
