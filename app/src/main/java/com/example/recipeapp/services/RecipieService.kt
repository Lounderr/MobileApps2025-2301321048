package com.example.recipeapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)

data class CategoriesResponse(
    val categories: List<Category>
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

data class MealsResponse(
    val meals: List<Meal>
)

private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val recipeService = retrofit.create(RecipeService::class.java)

interface RecipeService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse
}

