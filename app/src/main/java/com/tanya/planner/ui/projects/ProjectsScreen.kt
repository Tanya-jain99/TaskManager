package com.tanya.planner.ui.projects

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.tanya.planner.design.grey3
import com.tanya.planner.design.white100
import com.tanya.planner.ui.theme.PlannerAppBar

@Composable
fun ProjectsScreen(modifier: Modifier, navController: NavHostController) {
    Scaffold(
        containerColor = white100,
        modifier = modifier,
        contentColor = grey3,
        topBar = {
            PlannerAppBar(title = "Projects") { navController.popBackStack() }
        }
    ) { innerPadding ->
        BoardLayout(modifier = Modifier.padding(innerPadding), navController)
    }

}