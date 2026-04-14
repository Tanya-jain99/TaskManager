package com.tanya.planner.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.planner.database.PlannerDb
import com.tanya.planner.database.entity.WorkItem
import com.tanya.planner.model.RecentTaskItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val plannerDb: PlannerDb,
) : ViewModel() {

    fun saveTask(workItem: WorkItem) {
        viewModelScope.launch {
            plannerDb.taskDao().insertTask(workItem)
        }
    }

    fun deleteTask(recentTaskItem: RecentTaskItemModel) {
        viewModelScope.launch {
            val workItem = WorkItem(
                recentTaskItem.id,
                recentTaskItem.title,
                recentTaskItem.description,
                recentTaskItem.dueDate,
                recentTaskItem.status.uiLabel,
                recentTaskItem.priority.name
            )
            plannerDb.taskDao().deleteTask(workItem)
        }
    }
}