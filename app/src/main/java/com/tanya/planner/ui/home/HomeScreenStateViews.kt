package com.tanya.planner.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tanya.planner.design.black60
import com.tanya.planner.design.blue50
import com.tanya.planner.design.blue60
import com.tanya.planner.design.black100
import com.tanya.planner.design.red60
import com.tanya.planner.design.white100
import com.tanya.planner.model.HomeScreenUIData

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = blue60)
    }
}

@Composable
fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.size(72.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = red60,
                modifier = Modifier.size(48.dp),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Something went wrong",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                color = black100,
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = message,
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.W400,
                color = black60,
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = blue60,
                contentColor = white100,
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(
                text = "Try Again",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                )
            )
        }
    }
}

@Composable
fun SuccessContent(
    data: HomeScreenUIData,
    navController: NavHostController,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TaskSummaryCard(
            count = data.dueCount,
            title = "Due Today",
            icon = Icons.Default.CalendarToday,
            backgroundColor = blue60,
            modifier = Modifier
                .weight(1f)
                .clickable { navController.navigate("dueTodayTasksScreen") }
        )
        Spacer(modifier = Modifier.width(16.dp))
        TaskSummaryCard(
            count = data.overdueCount,
            title = "Overdue",
            icon = Icons.Default.Warning,
            backgroundColor = red60,
            modifier = Modifier
                .weight(1f)
                .clickable { navController.navigate("overdueTasksScreen") }
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Recent tasks",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = black100,
            )
        )
        Text(
            modifier = Modifier.clickable { navController.navigate("seeAllScreen") },
            text = "See All",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = blue50,
            )
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    data.recentTasks.forEach { task ->
        RecentTaskItem(
            task = task,
            onTaskItemClick = {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("metadata", task)
                navController.navigate("addWorkItemScreen")
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
