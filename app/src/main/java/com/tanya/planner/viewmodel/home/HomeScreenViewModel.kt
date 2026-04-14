package com.tanya.planner.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.planner.database.PlannerDb
import com.tanya.planner.model.HomeScreenUIData
import com.tanya.planner.model.RecentTaskItemModel
import com.tanya.planner.model.TaskPriority
import com.tanya.planner.model.TaskStatus
import com.tanya.planner.repository.TasksRepository
import com.tanya.planner.ui.home.HomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val plannerDb: PlannerDb,
    private val repository: TasksRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(HomeScreenUIState.Loading)
    val uiState = _uiState.asStateFlow()

    var recentTasks = emptyList<RecentTaskItemModel>()

    init {
        getItems()
    }

    fun getItems() {
        viewModelScope.launch {
            plannerDb.taskDao().getAllTasks().collect { workItems ->
                runCatching {
                    recentTasks = workItems.map {
                        RecentTaskItemModel(
                            it.id,
                            it.title,
                            it.description,
                            it.dueDate,
                            TaskStatus.valueOf(it.status),
                            TaskPriority.valueOf(it.priority)
                        )
                    }

                    val overdueCount = repository.getOverdueTaskCount()

                    val dueToday = repository.getDueTodayTaskCount()

                    _uiState.value = HomeScreenUIState.Success(
                        data = HomeScreenUIData(
                            isLoading = false,
                            overdueCount = overdueCount,
                            dueCount = dueToday,
                            recentTasks = recentTasks.sortedBy { (_, _, _, dueDate, _, _) -> dueDate }
                                .take(5)
                        )
                    )
                }.getOrElse {
                    _uiState.value = HomeScreenUIState.Error("Something went wrong")
                }

            }
        }
    }

}