package com.tanya.planner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tanya.planner.design.grey5
import com.tanya.planner.ui.theme.PlannerAppBar
import com.tanya.planner.viewmodel.SeeAllScreenViewModel

@Composable
fun SeeAllScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewmodel: SeeAllScreenViewModel = hiltViewModel(),
) {
    val tasks by viewmodel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        containerColor = grey5,
        topBar = {
            PlannerAppBar(title = "All Tasks") {
                navController.popBackStack()
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(grey5),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = innerPadding.calculateTopPadding() + 12.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp,
            ),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp),
        ) {
            items(tasks) { task ->
                RecentTaskItem(
                    task = task,
                    onTaskItemClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("metadata", task)
                        navController.navigate("addWorkItemScreen")
                    },
                )
            }
        }
    }
}
