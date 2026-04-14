package com.tanya.planner.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanya.planner.database.entity.WorkItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WorkDao {

    @Query("SELECT * FROM work_item")
    fun getAllTasks(): Flow<List<WorkItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: WorkItem): Long

    @Delete
    suspend fun deleteTask(workItem: WorkItem)

    @Query("SELECT COUNT(*) from work_item where dueDate = :currentDate and status != :status")
    suspend fun getDueTodayCount(currentDate: LocalDate, status: String): Long

    @Query("SELECT COUNT(*) from work_item where dueDate < :currentDate and status != :status")
    suspend fun getOverDueTaskCount(currentDate: LocalDate, status: String) : Long

    @Query("SELECT * from work_item where dueDate = :currentDate and status != :status")
    fun getDueTodayTasks(currentDate: LocalDate, status: String) : Flow<List<WorkItem>>
    @Query("SELECT * from work_item where dueDate < :currentDate and status != :status")

    fun getOverDueTasks(currentDate: LocalDate, status: String): Flow<List<WorkItem>>

    @Query("SELECT * from work_item where dueDate = :selectedDate")
    fun getTasksByDate(selectedDate: LocalDate) : Flow<List<WorkItem>>

    @Query("SELECT * from work_item where status = :status")
    fun getTasksByStatus(status: String): Flow<List<WorkItem>>

    @Query("SELECT * from work_item where title like '%' || :query || '%' OR description like '%' || :query || '%'")
    suspend fun getTasksByQuery(query: String): List<WorkItem>
}
