package com.tanya.planner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tanya.planner.design.blue60
import com.tanya.planner.design.grey5
import com.tanya.planner.design.grey60
import com.tanya.planner.design.grey8
import com.tanya.planner.design.white100
import com.tanya.planner.viewmodel.home.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { HomeAppBar(name = "Tanya") }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = white100,
                )
            )
        },
        containerColor = white100,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("metadata", null)
                    navController.navigate("addWorkItemScreen")
                },
                containerColor = blue60,
                contentColor = white100,
                shape = CircleShape,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add task")
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            val isLoading = uiState is HomeScreenUIState.Loading
            Text(
                text = "Search here ...",
                color = if (isLoading) grey60.copy(alpha = 0.4f) else grey60,
                modifier = Modifier
                    .clickable(enabled = !isLoading) {
                        navController.navigate("search")
                    }
                    .clip(shape = RoundedCornerShape(30.dp))
                    .border(color = grey8, width = 1.dp, shape = RoundedCornerShape(30.dp))
                    .background(if (isLoading) grey5.copy(alpha = 0.5f) else grey5)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            when (val state = uiState) {
                is HomeScreenUIState.Loading -> LoadingContent()
                is HomeScreenUIState.Error -> ErrorContent(
                    message = state.message,
                    onRetry = { viewModel.getItems() },
                )
                is HomeScreenUIState.Success -> SuccessContent(
                    data = state.data,
                    navController = navController,
                )
            }
        }
    }
}
