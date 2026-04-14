package com.tanya.planner.viewmodel.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.planner.model.RecentTaskItemModel
import com.tanya.planner.model.TaskPriority
import com.tanya.planner.model.TaskStatus
import com.tanya.planner.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel
class CalendarViewModel @Inject constructor(private val tasksRepository: TasksRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<List<RecentTaskItemModel>>(emptyList())
    val uiState = _uiState.asStateFlow()

    var job: Job? = null

    init {
        getTasksByDate(LocalDate.now())
    }

    fun getTasksByDate(selectedDate: LocalDate) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.getTasksByDate(selectedDate).collect {
                _uiState.value = it.map { work ->
                    RecentTaskItemModel(
                        work.id, work.title, work.description, work.dueDate,
                        TaskStatus.valueOf(work.status), TaskPriority.valueOf(work.priority)
                    )
                }
            }
        }
    }
}