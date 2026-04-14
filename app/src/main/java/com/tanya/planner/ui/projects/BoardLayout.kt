package com.tanya.planner.ui.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tanya.planner.design.black100
import com.tanya.planner.model.TaskStatus
import com.tanya.planner.ui.home.RecentTaskItem
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import androidx.compose.runtime.snapshotFlow
import com.tanya.planner.viewmodel.projects.BoardViewModel

@Composable
fun BoardLayout(modifier: Modifier, navController: NavHostController) {

    val listLazyState = rememberLazyListState()
    val entries = TaskStatus.entries
    var selectedStatus by remember { mutableStateOf(TaskStatus.TODO) }
    // Only set when user explicitly clicks a tab — drives the scroll animation
    var pendingTabScroll by remember { mutableStateOf<TaskStatus?>(null) }
    val scrollingToTab = remember { mutableStateOf(false) }

    // Animate to column ONLY when a tab is explicitly clicked
    LaunchedEffect(pendingTabScroll) {
        pendingTabScroll?.let { status ->
            scrollingToTab.value = true
            listLazyState.animateScrollToItem(status.ordinal, 0)
            scrollingToTab.value = false
            pendingTabScroll = null
        }
    }

    // Track closest column to viewport center on every layout change (including fling settle)
    LaunchedEffect(listLazyState) {
        snapshotFlow {
            Pair(
                listLazyState.layoutInfo.visibleItemsInfo,
                listLazyState.layoutInfo.viewportEndOffset
            )
        }
            .filter { !scrollingToTab.value }
            .map { (visibleItems, viewportEnd) ->
                if (visibleItems.isEmpty()) return@map null
                val viewportCenter = viewportEnd / 2
                val closest = visibleItems.minByOrNull { item ->
                    kotlin.math.abs((item.offset + item.size / 2) - viewportCenter)
                }
                TaskStatus.entries.getOrElse(closest?.index ?: 0) { TaskStatus.TODO }
            }
            .distinctUntilChanged()
            .collect { newStatus ->
                if (newStatus != null && newStatus != selectedStatus) {
                    selectedStatus = newStatus
                }
            }
    }

    Column(modifier = modifier) {
        BoardTabLayout(selectedStatus = selectedStatus) { status ->
            selectedStatus = status
            pendingTabScroll = status
        }


        LazyRow(
            state = listLazyState,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(all = 16.dp)
        ) {

            items(entries) { status ->
                TaskColumn(
                    navController = navController,
                    modifier = Modifier
                        .fillParentMaxWidth(0.9f), taskStatus = status
                )
            }
        }
    }
}

@Composable
fun TaskColumn(
    taskStatus: TaskStatus,
    viewModel: BoardViewModel = hiltViewModel<BoardViewModel>(),
    modifier: Modifier,
    navController: NavHostController
) {

    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(taskStatus) {
        viewModel.getTasks(taskStatus)
    }

    LazyColumn(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = taskStatus.uiLabel,
                color = black100,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W700)
            )
        }

        items(uiState.value.getOrDefault(taskStatus, emptyList())) {
            RecentTaskItem(
                    task = it,
                    showDescription = true,
                    onTaskItemClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("metadata", it)
                        navController.navigate("addWorkItemScreen")
                    },
                )
        }

    }
}