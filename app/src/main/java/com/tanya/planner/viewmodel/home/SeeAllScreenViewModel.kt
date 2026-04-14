package com.tanya.planner.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.planner.model.RecentTaskItemModel
import com.tanya.planner.model.TaskPriority
import com.tanya.planner.model.TaskStatus
import com.tanya.planner.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllScreenViewModel @Inject constructor(val repository : TasksRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<List<RecentTaskItemModel>>(emptyList())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _uiState.value = it.map { task ->
                    RecentTaskItemModel(
                        task.id,
                        task.title,
                        task.description,
                        task.dueDate,
                        TaskStatus.valueOf(task.status),
                        TaskPriority.valueOf(task.priority)
                    )
                }
            }
        }

    }


}