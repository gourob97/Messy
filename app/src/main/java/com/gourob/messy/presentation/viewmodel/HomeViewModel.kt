package com.gourob.messy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.gourob.messy.ui.screens.HomeUiState
import com.gourob.messy.ui.screens.MealItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun updateSelectedDate(dateInMillis: Long) {
        _uiState.value = _uiState.value.copy(currentDateInMillis = dateInMillis)
    }
    fun onLunchCheckedChange(it: Boolean) {
        _uiState.value = _uiState.value.copy(lunchMealState = _uiState.value.lunchMealState.copy(isEnabled = it))
    }
    fun onDinnerCheckedChange(it: Boolean) {
        _uiState.value = _uiState.value.copy(dinnerMealState = _uiState.value.dinnerMealState.copy(isEnabled = it))
    }
    fun onGuestLunchCountChange(it: Int) {
        _uiState.value = _uiState.value.copy(lunchMealState = _uiState.value.lunchMealState.copy(guestCount = max(it, 0)))
    }
    fun onGuestDinnerCountChange(it: Int) {
        _uiState.value = _uiState.value.copy(dinnerMealState = _uiState.value.dinnerMealState.copy(guestCount = max(it, 0)))
    }
    fun onItemClicked(item: MealItem, isLiked: Boolean) {
        val updatedLunchItems = uiState.value.lunchMealState.menuItems.map {
            if (it.id == item.id) it.copy(isLiked = isLiked) else it
        }

        val updatedDinnerItems = uiState.value.dinnerMealState.menuItems.map {
            if (it.id == item.id) it.copy(isLiked = isLiked) else it
        }

        val updatedUiState = uiState.value.copy(
            lunchMealState = uiState.value.lunchMealState.copy(menuItems = updatedLunchItems),
            dinnerMealState = uiState.value.dinnerMealState.copy(menuItems = updatedDinnerItems)
        )
        _uiState.value = updatedUiState
    }
}