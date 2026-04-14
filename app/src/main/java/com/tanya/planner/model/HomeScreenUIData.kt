package com.tanya.planner.model

data class HomeScreenUIData(
    val isLoading: Boolean = false,
    val overdueCount: Long = 0,
    val dueCount: Long = 0,
    val recentTasks: List<RecentTaskItemModel> = emptyList()
)