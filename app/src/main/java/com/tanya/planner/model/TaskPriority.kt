package com.tanya.planner.model

import androidx.compose.ui.graphics.Color
import com.tanya.planner.design.grey1
import com.tanya.planner.design.grey10
import com.tanya.planner.design.grey100
import com.tanya.planner.design.grey15
import com.tanya.planner.design.grey20
import com.tanya.planner.design.grey30
import com.tanya.planner.design.grey5
import com.tanya.planner.design.grey60
import com.tanya.planner.design.grey80
import com.tanya.planner.design.grey90
import com.tanya.planner.design.red1
import com.tanya.planner.design.red10
import com.tanya.planner.design.red110
import com.tanya.planner.design.red20
import com.tanya.planner.design.red30
import com.tanya.planner.design.red5
import com.tanya.planner.design.red50
import com.tanya.planner.design.red60
import com.tanya.planner.design.red70
import com.tanya.planner.design.red80
import com.tanya.planner.design.white100
import com.tanya.planner.design.yellow1
import com.tanya.planner.design.yellow20
import com.tanya.planner.design.yellow30
import com.tanya.planner.design.yellow40
import com.tanya.planner.design.yellow5
import com.tanya.planner.design.yellow50
import com.tanya.planner.design.yellow60
import com.tanya.planner.design.yellow80
import com.tanya.planner.design.yellow90

enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH;

    fun getBackgroundColor() : Color {
        return when(this) {
            LOW -> grey5
            MEDIUM -> yellow1
            HIGH -> red1
        }
    }

    fun getTextColor() : Color {
        return when(this) {
            LOW -> grey100
            MEDIUM -> yellow50
            HIGH -> red110
        }
    }

    fun getIconColor() : Color {
        return when(this) {
            LOW -> grey60
            MEDIUM -> yellow40
            HIGH -> red110
        }
    }
}

