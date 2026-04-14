package com.tanya.planner.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tanya.planner.design.black100
import com.tanya.planner.design.grey5
import com.tanya.planner.design.grey60
import com.tanya.planner.design.grey8
import com.tanya.planner.design.white100
import com.tanya.planner.ui.home.RecentTaskItem
import com.tanya.planner.ui.theme.PlannerAppBar
import com.tanya.planner.viewmodel.search.SearchScreenViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    viewmodel: SearchScreenViewModel = hiltViewModel<SearchScreenViewModel>()
) {
    Scaffold(
        containerColor = white100,
        topBar = { PlannerAppBar(title = "Search") { navController.popBackStack() } },
        content = { innerPadding ->
            val state by viewmodel.uiState.collectAsState()
            var textState by remember { mutableStateOf(viewmodel.getLastQuery()) }
            Column(modifier = Modifier.padding(innerPadding)) {
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedPlaceholderColor = grey60,
                        focusedPlaceholderColor = grey60,
                        focusedBorderColor = grey8,
                        unfocusedBorderColor = grey8,
                        focusedContainerColor = grey5,
                        unfocusedContainerColor = grey5,
                        cursorColor = black100
                    ),
                    placeholder = {
                        Text("Search here...")
                    },
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    value = textState,
                    onValueChange = {
                        textState = it
                        viewmodel.queryUpdated(it)
                    }
                )
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state) { it ->
                        RecentTaskItem(task = it) {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "metadata",
                                it
                            )
                            navController.navigate("addWorkItemScreen")
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(navController = rememberNavController())
}