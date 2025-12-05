package com.example.recipeapp.data

import com.example.recipeapp.data.local.RecipeDao
import com.example.recipeapp.data.local.RecipeEntity
import kotlinx.coroutines.flow.Flow

class LocalRecipeRepository(private val recipeDao: RecipeDao) {
    val allRecipes: Flow<List<RecipeEntity>> = recipeDao.getAllRecipes()

    suspend fun getRecipeById(id: Int): RecipeEntity? {
        return recipeDao.getRecipeById(id)
    }

    suspend fun insert(recipe: RecipeEntity): Long {
        return recipeDao.insertRecipe(recipe)
    }

    suspend fun update(recipe: RecipeEntity) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun delete(recipe: RecipeEntity) {
        recipeDao.deleteRecipe(recipe)
    }
}
