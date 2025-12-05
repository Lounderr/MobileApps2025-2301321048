package com.example.recipeapp.data

import com.example.recipeapp.data.model.Category
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.data.model.MealDetail
import com.example.recipeapp.data.network.RecipeService

class RecipeRepository(private val api: RecipeService) {
    suspend fun getCategories(): List<Category> {
        return api.getCategories().categories
    }

    suspend fun getMealsByCategory(category: String): List<Meal> {
        return api.getMealsByCategory(category).meals
    }

    suspend fun getMealDetails(id: String): MealDetail? {
        return api.getMealDetails(id).meals.firstOrNull()
    }
}
