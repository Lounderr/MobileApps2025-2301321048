package com.example.recipeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.LocalRecipeRepository
import com.example.recipeapp.data.local.RecipeEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyRecipesViewModel(private val repository: LocalRecipeRepository) : ViewModel() {

    val allRecipes: StateFlow<List<RecipeEntity>> = repository.allRecipes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

//    fun addRecipe(name: String, category: String, instructions: String, imageUrl: String) {
//        viewModelScope.launch {
//            repository.insert(
//                RecipeEntity(
//                    name = name,
//                    category = category,
//                    instructions = instructions,
//                    imageUrl = imageUrl
//                )
//            )
//        }
//    }

    fun addPlaceholderRecipe(onRecipeCreated: (Int) -> Unit) {
        viewModelScope.launch {
            val id = repository.insert(
                RecipeEntity(
                    name = "New Recipe",
                    category = null,
                    instructions = null,
                    imageUrl = null
                )
            )
            onRecipeCreated(id.toInt())
        }
    }

    suspend fun getRecipeById(id: Int): RecipeEntity? {
        return repository.getRecipeById(id)
    }

    fun updateRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.update(recipe)
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.delete(recipe)
        }
    }
}

class MyRecipesViewModelFactory(private val repository: LocalRecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyRecipesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyRecipesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
