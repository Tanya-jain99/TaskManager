package com.tanya.planner.ui.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanya.planner.design.white100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerAppBar(title: String, onBackClick :() -> Unit){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = white100),
        title = { Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = title,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight.W700
            )
        ) }, navigationIcon = {
            IconButton(onClick = onBackClick , content = {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            })
        },
        actions = {
            Spacer(modifier = Modifier.size(42.dp))
        })

}