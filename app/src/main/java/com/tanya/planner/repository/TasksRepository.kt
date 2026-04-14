package com.tanya.planner.repository

import com.tanya.planner.database.PlannerDb
import com.tanya.planner.database.entity.WorkItem
import com.tanya.planner.model.RecentTaskItemModel
import com.tanya.planner.model.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject


class TasksRepository @Inject constructor(val plannerDb: PlannerDb) {

    fun getAllTasks() : Flow<List<WorkItem>> {
        return plannerDb.taskDao().getAllTasks()
    }

    suspend fun getDueTodayTaskCount() : Long {
        return plannerDb.taskDao().getDueTodayCount(LocalDate.now(), TaskStatus.COMPLETED.name)
    }

    suspend fun getOverdueTaskCount(): Long {
        return plannerDb.taskDao().getOverDueTaskCount(LocalDate.now(), TaskStatus.COMPLETED.name)
    }

    fun getDueTodayTasksList(): Flow<List<WorkItem>> {
        return plannerDb.taskDao().getDueTodayTasks(LocalDate.now(), TaskStatus.COMPLETED.name)
    }
     fun getOverDueTasksList(): Flow<List<WorkItem>> {
        return plannerDb.taskDao().getOverDueTasks(LocalDate.now(), TaskStatus.COMPLETED.name)
    }

    fun getTasksByDate(selectedDate: LocalDate) : Flow<List<WorkItem>>{
        return plannerDb.taskDao().getTasksByDate(selectedDate)
    }

    fun getTasksByStatus(status: String): Flow<List<WorkItem>> {
        return plannerDb.taskDao().getTasksByStatus(status)
    }

    suspend fun getTasksByQuery(query: String): List<WorkItem> {
        return plannerDb.taskDao().getTasksByQuery(query)
    }
}