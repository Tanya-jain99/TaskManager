package com.tanya.planner.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Calendar : BottomNavScreen(
        route = "calendar",
        title = "Calendar",
        icon = Icons.Default.DateRange
    )

    object Projects : BottomNavScreen(
        route = "projects",
        title = "Projects",
        icon = Icons.Default.List
    )

}