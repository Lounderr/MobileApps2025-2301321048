package com.example.recipeapp.data.network

import com.example.recipeapp.data.model.CategoriesResponse
import com.example.recipeapp.data.model.MealDetailResponse
import com.example.recipeapp.data.model.MealsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") id: String): MealDetailResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val api: RecipeService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeService::class.java)
    }
}
