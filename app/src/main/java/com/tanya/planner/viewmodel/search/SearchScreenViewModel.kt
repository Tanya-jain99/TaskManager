package com.tanya.planner.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.planner.model.RecentTaskItemModel
import com.tanya.planner.model.TaskPriority
import com.tanya.planner.model.TaskStatus
import com.tanya.planner.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchScreenViewModel @Inject constructor(val repository: TasksRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<List<RecentTaskItemModel>>(emptyList())
    val uiState = _uiState.asStateFlow()

    val searchQuery = MutableStateFlow<String>("")

    init {
        viewModelScope.launch {
            searchQuery.debounce(300).distinctUntilChanged()
                .flatMapLatest { query ->
                    val result = if (query.isEmpty()) emptyList() else
                        repository.getTasksByQuery(query).map { it ->
                            RecentTaskItemModel(
                                it.id,
                                it.title,
                                it.description,
                                it.dueDate,
                                TaskStatus.valueOf(it.status),
                                TaskPriority.valueOf(it.priority)
                            )
                        }
                    return@flatMapLatest flowOf(result)
                }.collect { result ->
                    _uiState.value = result
                }
        }
    }

    fun queryUpdated(query: String) {
        searchQuery.value = query
    }

    fun getLastQuery() = searchQuery.value
}