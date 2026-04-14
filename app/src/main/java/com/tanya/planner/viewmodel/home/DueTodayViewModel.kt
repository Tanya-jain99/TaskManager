package com.tanya.planner.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.planner.model.RecentTaskItemModel
import com.tanya.planner.model.TaskPriority
import com.tanya.planner.model.TaskStatus
import com.tanya.planner.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DueTodayViewModel @Inject constructor(val tasksRepository: TasksRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<List<RecentTaskItemModel>>(emptyList())
    val uiState = _uiState.asStateFlow()

    init {
        getAllDueTodayTasks()
    }

    fun getAllDueTodayTasks() {
        viewModelScope.launch {
            tasksRepository.getDueTodayTasksList().collect {
                val list = it.map { task ->
                    RecentTaskItemModel(
                        id = task.id,
                        title = task.title,
                        description = task.description,
                        dueDate = task.dueDate,
                        status = TaskStatus.valueOf(task.status),
                        priority = TaskPriority.valueOf(task.priority)
                    )
                }
                _uiState.value = list
            }
        }
    }
}