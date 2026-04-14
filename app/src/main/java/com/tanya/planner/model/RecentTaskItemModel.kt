package com.tanya.planner.model

import android.os.Parcelable
import java.time.LocalDate
import java.time.LocalDateTime

@kotlinx.parcelize.Parcelize
data class RecentTaskItemModel(
    val id: Long,
    val title: String,
    val description: String,
    val dueDate: LocalDate?,
    val status: TaskStatus,
    val priority: TaskPriority
): Parcelable
