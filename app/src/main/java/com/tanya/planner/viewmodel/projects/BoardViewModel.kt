package com.tanya.planner.viewmodel.projects

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
class BoardViewModel @Inject constructor(val repository: TasksRepository) : ViewModel() {

    private val _uiState =
        MutableStateFlow<HashMap<TaskStatus, List<RecentTaskItemModel>>>(hashMapOf())
    val uiState = _uiState.asStateFlow()


    var hashMap = hashMapOf<TaskStatus, List<RecentTaskItemModel>>()
    fun getTasks(status: TaskStatus) {
        viewModelScope.launch {
            val flow = repository.getTasksByStatus(status.name)

            flow.collect { tasks ->
                val mappedTasks = tasks.map { task ->
                    RecentTaskItemModel(
                        task.id,
                        task.title,
                        task.description,
                        task.dueDate,
                        TaskStatus.valueOf(task.status),
                        TaskPriority.valueOf(task.priority)
                    )
                }

                hashMap[status] = mappedTasks
                _uiState.value = HashMap(hashMap)
            }
        }
    }
}