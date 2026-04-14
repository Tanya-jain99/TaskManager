package com.tanya.planner.ui.home

import com.tanya.planner.model.HomeScreenUIData

sealed class HomeScreenUIState {
    object Loading : HomeScreenUIState()
    data class Success(val data: HomeScreenUIData) : HomeScreenUIState()
    data class Error(val message: String) : HomeScreenUIState()

}