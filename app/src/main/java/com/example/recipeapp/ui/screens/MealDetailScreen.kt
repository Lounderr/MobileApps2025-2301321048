package com.example.recipeapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.recipeapp.MealDetailViewModel
import com.example.recipeapp.R
import com.example.recipeapp.data.model.MealDetail
import com.example.recipeapp.ui.theme.Dimens

@Composable
fun MealDetailScreen(
    mealId: String,
    viewModel: MealDetailViewModel = viewModel()
) {
    val viewState by viewModel.mealState

    LaunchedEffect(mealId) {
        viewModel.fetchMealDetails(mealId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(
                    text = stringResource(R.string.error_msg, viewState.error ?: ""),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            viewState.meal != null -> {
                val meal = viewState.meal!!
                LazyColumn(modifier = Modifier.fillMaxSize().padding(Dimens.PaddingMedium)) {
                    item {
                        AsyncImage(
                            model = meal.strMealThumb,
                            contentDescription = stringResource(R.string.meal_image_content_desc, meal.strMeal),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimens.MealImageHeight)
                                .clip(RoundedCornerShape(Dimens.CornerRadius))
                        )
                        Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                        Text(
                            text = meal.strMeal,
                            style = MaterialTheme.typography.displaySmall
                        )
                        Text(
                            text = stringResource(R.string.category_area_format, meal.strCategory, meal.strArea),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                        Text(
                            text = stringResource(R.string.instructions),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = meal.strInstructions,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                        Text(
                            text = stringResource(R.string.ingredients),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    val ingredients = getIngredients(meal)
                    items(ingredients) { ingredient ->
                        Text(
                            text = stringResource(R.string.ingredient_item, ingredient),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

private fun getIngredients(meal: MealDetail): List<String> {
    val list = mutableListOf<String>()

    val ingredients = listOf(
        meal.strIngredient1 to meal.strMeasure1,
        meal.strIngredient2 to meal.strMeasure2,
        meal.strIngredient3 to meal.strMeasure3,
        meal.strIngredient4 to meal.strMeasure4,
        meal.strIngredient5 to meal.strMeasure5,
        meal.strIngredient6 to meal.strMeasure6,
        meal.strIngredient7 to meal.strMeasure7,
        meal.strIngredient8 to meal.strMeasure8,
        meal.strIngredient9 to meal.strMeasure9,
        meal.strIngredient10 to meal.strMeasure10,
        meal.strIngredient11 to meal.strMeasure11,
        meal.strIngredient12 to meal.strMeasure12,
        meal.strIngredient13 to meal.strMeasure13,
        meal.strIngredient14 to meal.strMeasure14,
        meal.strIngredient15 to meal.strMeasure15,
        meal.strIngredient16 to meal.strMeasure16,
        meal.strIngredient17 to meal.strMeasure17,
        meal.strIngredient18 to meal.strMeasure18,
        meal.strIngredient19 to meal.strMeasure19,
        meal.strIngredient20 to meal.strMeasure20
    )

    ingredients.forEach { (ingredient, measure) ->
        if (!ingredient.isNullOrBlank()) {
            val text = if (!measure.isNullOrBlank()) "$measure $ingredient" else ingredient
            list.add(text)
        }
    }
    return list
}
