package com.example.recipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.model.MealDetail
import com.example.recipeapp.data.network.RetrofitInstance
import kotlinx.coroutines.launch

data class MealDetailState(
    val loading: Boolean = true,
    val meal: MealDetail? = null,
    val error: String? = null
)

class MealDetailViewModel : ViewModel() {
    private val repository = RecipeRepository(RetrofitInstance.api)
    private val _mealState = mutableStateOf(MealDetailState())
    val mealState: State<MealDetailState> = _mealState

    fun fetchMealDetails(mealId: String) {
        viewModelScope.launch {
            try {
                val meal = repository.getMealDetails(mealId)
                _mealState.value = _mealState.value.copy(
                    loading = false,
                    meal = meal,
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
