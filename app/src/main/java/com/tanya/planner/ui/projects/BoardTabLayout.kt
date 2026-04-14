package com.tanya.planner.ui.projects

import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanya.planner.design.blue60
import com.tanya.planner.design.grey50
import com.tanya.planner.design.white100
import com.tanya.planner.model.TaskStatus

@Composable
fun BoardTabLayout(selectedStatus: TaskStatus, onTabClick: (TaskStatus) -> Unit) {

    val selectedTabIndex = selectedStatus.ordinal

    PrimaryScrollableTabRow(
        selectedTabIndex = selectedTabIndex, edgePadding = 0.dp, indicator = {
            TabRowDefaults.PrimaryIndicator(
                Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = true),
                width = Dp.Unspecified,
                color = blue60
            )
        },
        containerColor = white100
    ) {
        TaskStatus.entries.forEach { status ->
            Tab(
                selected = selectedTabIndex == status.ordinal,
                onClick = {
                    onTabClick(status)
                },
                selectedContentColor = blue60,
                unselectedContentColor = grey50,
                text = {
                    Text(
                        text = status.uiLabel,
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W700)
                    )
                }
            )
        }
    }
}