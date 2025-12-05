package com.example.recipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.model.Category
import com.example.recipeapp.data.network.RetrofitInstance
import kotlinx.coroutines.launch

data class MainState(
    val loading: Boolean = true,
    val list: List<Category> = emptyList(),
    val error: String? = null
)

class MainViewModel : ViewModel() {
    private val repository = RecipeRepository(RetrofitInstance.api)
    private val _categoriesState = mutableStateOf(MainState())
    val categoriesState: State<MainState> = _categoriesState

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val categories = repository.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    list = categories,
                    error = null
                )
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error fetching categories: ${e.message}"
                )
            }
        }
    }
}
