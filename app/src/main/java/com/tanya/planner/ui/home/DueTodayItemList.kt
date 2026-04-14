package com.tanya.planner.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tanya.planner.design.grey5
import com.tanya.planner.ui.DueTodayViewModel
import com.tanya.planner.ui.theme.PlannerAppBar

@Composable
fun DueTodayList(
    modifier: Modifier = Modifier,
    viewModel: DueTodayViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val tasks = viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        containerColor = grey5,
        topBar = {
            PlannerAppBar(title = "Due Today") { navController.popBackStack() }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = innerPadding.calculateTopPadding() + 12.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(tasks.value) { task ->
                RecentTaskItem(
                    task = task,
                    showDescription = true,
                    onTaskItemClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("metadata", task)
                        navController.navigate("addWorkItemScreen")
                    },
                )
            }
        }
    }
}
