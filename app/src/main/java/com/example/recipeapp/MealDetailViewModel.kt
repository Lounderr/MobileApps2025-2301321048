package com.example.recipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.services.MealDetail
import com.example.recipeapp.services.recipeService
import kotlinx.coroutines.launch

data class MealDetailState(
    val loading: Boolean = true,
    val meal: MealDetail? = null,
    val error: String? = null
)

class MealDetailViewModel : ViewModel() {
    private val _mealState = mutableStateOf(MealDetailState())
    val mealState: State<MealDetailState> = _mealState

    fun fetchMealDetails(mealId: String) {
        viewModelScope.launch {
            try {
                val response = recipeService.getMealDetails(mealId)
                _mealState.value = _mealState.value.copy(
                    loading = false,
                    meal = response.meals.firstOrNull(),
                    error = null
                )
            } catch (e: Exception) {
                _mealState.value = _mealState.value.copy(
                    loading = false,
                    error = "Error fetching meal details: ${e.message}"
                )
            }
        }
    }
}
