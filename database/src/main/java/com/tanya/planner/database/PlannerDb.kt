package com.tanya.planner.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tanya.planner.database.dao.WorkDao
import com.tanya.planner.database.entity.WorkItem

@Database(
    entities = [WorkItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PlannerDb : RoomDatabase() {

    abstract fun taskDao(): WorkDao
}
