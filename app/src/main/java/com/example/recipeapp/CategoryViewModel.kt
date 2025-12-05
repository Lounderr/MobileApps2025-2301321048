package com.example.recipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.network.RetrofitInstance
import kotlinx.coroutines.launch

data class CategoryState(
    val loading: Boolean = true,
    val list: List<Meal> = emptyList(),
    val error: String? = null
)

class CategoryViewModel : ViewModel() {
    private val repository = RecipeRepository(RetrofitInstance.api)
    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    fun fetchMeals(category: String) {
        viewModelScope.launch {
            try {
                val meals = repository.getMealsByCategory(category)
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    list = meals.shuffled(),
                    error = null
                )
            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(
                    loading = false,
                    error = "Error fetching meals: ${e.message}"
                )
            }
        }
    }
}
