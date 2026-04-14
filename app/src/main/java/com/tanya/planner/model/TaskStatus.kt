package com.tanya.planner.model

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.Color
import com.tanya.planner.R
import com.tanya.planner.design.green10
import com.tanya.planner.design.green20
import com.tanya.planner.design.green30
import com.tanya.planner.design.green5
import com.tanya.planner.design.green50
import com.tanya.planner.design.green60
import com.tanya.planner.design.green70
import com.tanya.planner.design.grey5
import com.tanya.planner.design.yellow1
import com.tanya.planner.design.yellow50
import com.tanya.planner.design.yellow60

enum class TaskStatus(val uiLabel: String) {
    TODO("TODO"),
    IN_PROGRESS("IN PROGRESS"),
    COMPLETED("DONE");


    fun textColor(): Color = when (this) {
        TODO -> Color(0xFF374151)
        IN_PROGRESS -> yellow60
        COMPLETED -> green60
    }

    fun iconColor(): Color = when (this) {
        TODO -> Color(0xFF6B7280)
        IN_PROGRESS -> yellow50
        COMPLETED -> green50
    }

    fun borderColor(): Color = when (this) {
        TODO -> Color(0xFFE5E7EB)
        IN_PROGRESS -> Color(0xFF3B82F6)
        COMPLETED -> green30
    }

    fun backgroundColor(): Color = when (this) {
        TODO -> grey5
        IN_PROGRESS -> yellow1
        COMPLETED -> green10
    }

    fun icon(): Int = when(this){
        TODO -> R.drawable.ic_circle
        IN_PROGRESS -> R.drawable.ic_in_progress
        COMPLETED -> R.drawable.ic_check
    }
}

