package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.recipeapp.data.LocalRecipeRepository
import com.example.recipeapp.data.local.RecipeDatabase
import com.example.recipeapp.ui.RecipeApp
import com.example.recipeapp.ui.theme.RecipeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"
        ).build()
        
        val repository = LocalRecipeRepository(database.recipeDao())
        
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RecipeApp(
                        modifier = Modifier.padding(innerPadding),
                        repository = repository
                    )
                }
            }
        }
    }
}
