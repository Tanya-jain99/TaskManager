package com.tanya.planner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tanya.planner.design.blue60
import com.tanya.planner.design.white100
import com.tanya.planner.model.BottomNavScreen
import com.tanya.planner.ui.theme.PlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.WHITE,
                android.graphics.Color.WHITE
            )
        )
        val items = listOf(
            BottomNavScreen.Home,
            BottomNavScreen.Calendar,
            BottomNavScreen.Projects
        )
        setContent {
            PlannerTheme {
                val navController = rememberNavController()
                val navControllerState by navController.currentBackStackEntryAsState()
                val showNavBottomNavScreen by remember { derivedStateOf { items.any { screen -> navControllerState?.destination?.route == screen.route } } }
                Scaffold(
                    bottomBar = {
                        if (showNavBottomNavScreen) {
                            NavigationBar(
                                    containerColor = white100,
                                    windowInsets = WindowInsets(0, 0, 0, 0),
                                ) {
                                items.forEach { screen ->
                                    NavigationBarItem(
                                        selected = navControllerState?.destination?.route == screen.route,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }

                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = blue60,
                                            indicatorColor = white100
                                        ),
                                        interactionSource = MutableInteractionSource(),
                                        alwaysShowLabel = false,
                                        icon = {
                                            Icon(
                                                imageVector = screen.icon,
                                                contentDescription = screen.title
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        navController = navController
                    )
                }
            }
        }
    }
}