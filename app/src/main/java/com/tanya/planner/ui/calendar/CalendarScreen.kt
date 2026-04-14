package com.tanya.planner.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tanya.planner.design.grey3
import com.tanya.planner.design.white100
import com.tanya.planner.ui.home.RecentTaskItem
import com.tanya.planner.ui.theme.PlannerAppBar
import com.tanya.planner.viewmodel.calendar.CalendarViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun CalendarScreen(
    modifier: Modifier,
    navController: NavHostController,
    calendarViewModel: CalendarViewModel = hiltViewModel<CalendarViewModel>()
) {
    val datePickerState = rememberDatePickerState()
    var date by remember { mutableStateOf(datePickerState.selectedDateMillis) }
    val uiState = calendarViewModel.uiState.collectAsState()

    Scaffold(
        containerColor = white100,
        modifier = modifier,
        contentColor = grey3,
        topBar = {
            PlannerAppBar(title = "Calendar") { navController.popBackStack() }
        }
    ) { innerPadding ->

        Column() {
            DatePicker(
                modifier = Modifier.padding(innerPadding),
                state = datePickerState,
                showModeToggle = false,
                title = null,
                headline = null
            )
            LaunchedEffect(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let {
                    println("Calendar ${LocalDate.ofInstant(
                        Instant.ofEpochMilli(it),
                        ZoneOffset.UTC
                    )}")
                    calendarViewModel.getTasksByDate(
                        LocalDate.ofInstant(
                            Instant.ofEpochMilli(it),
                            ZoneOffset.UTC
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn() {
                items(uiState.value) { task ->
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
}