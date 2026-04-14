package com.tanya.planner.ui

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tanya.planner.model.BottomNavScreen
import com.tanya.planner.model.RecentTaskItemModel
import com.tanya.planner.ui.calendar.CalendarScreen
import com.tanya.planner.ui.home.AddWorkItemScreen
import com.tanya.planner.ui.home.DueTodayList
import com.tanya.planner.ui.home.HomeScreen
import com.tanya.planner.ui.home.OverDueTaskList
import com.tanya.planner.ui.search.SearchScreen
import com.tanya.planner.ui.home.SeeAllScreen
import com.tanya.planner.ui.projects.ProjectsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    val bottomScreens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Calendar,
        BottomNavScreen.Projects
    )

    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Home.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {

        bottomScreens.forEach { screen ->
            composable(route = screen.route) {

                when (screen) {
                    is BottomNavScreen.Home ->
                        HomeScreen(modifier = modifier, navController = navController)

                    is BottomNavScreen.Calendar ->
                        CalendarScreen(modifier = modifier, navController)

                    is BottomNavScreen.Projects ->
                        ProjectsScreen(modifier = modifier, navController)
                }
            }
        }

        composable("addWorkItemScreen") {
            val recentTaskItemModel = navController.previousBackStackEntry?.savedStateHandle?.get<RecentTaskItemModel>("metadata")
            AddWorkItemScreen(
                modifier = modifier,
                navController = navController,
                recentTaskItemModel = recentTaskItemModel,
            )
        }
        composable("seeAllScreen") {
            val recentTaskItemModel = navController.previousBackStackEntry?.savedStateHandle?.get<RecentTaskItemModel>("metadata")
            SeeAllScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(route = "dueTodayTasksScreen"){
            DueTodayList(modifier = modifier, navController = navController)
        }
        composable(route = "overdueTasksScreen"){
            OverDueTaskList(modifier = modifier, navController = navController)
        }
        composable(route = "search"){
            SearchScreen(navController)
        }
    }
}