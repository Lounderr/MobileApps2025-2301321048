package com.example.recipeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.recipeapp.MyRecipesViewModel
import com.example.recipeapp.R
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.ui.components.MealCard
import com.example.recipeapp.ui.theme.Dimens

@Composable
fun MyRecipesScreen(
    viewModel: MyRecipesViewModel,
    onRecipeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val recipes by viewModel.allRecipes.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addPlaceholderRecipe { newId ->
                    onRecipeClick(newId.toString())
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_recipe))
            }
        }
    ) { padding ->
        Column(modifier = modifier.padding(Dimens.PaddingMedium)) {
            Text(
                text = stringResource(R.string.my_recipes),
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

            if (recipes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.no_recipes))
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(recipes) { recipe ->
                        val meal = Meal(
                            idMeal = recipe.id.toString(),
                            strMeal = recipe.name ?: "New Recipe",
                            strMealThumb = recipe.imageUrl ?: "https://www.themealdb.com/images/media/meals/uyqrrv1511553350.jpg"
                        )
                        MealCard(
                            meal = meal,
                            onClick = { onRecipeClick(recipe.id.toString()) }
                        )
                    }
                }
            }
        }
    }
}


