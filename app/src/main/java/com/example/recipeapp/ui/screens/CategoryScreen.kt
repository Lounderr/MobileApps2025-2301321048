package com.example.recipeapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.CategoryViewModel
import com.example.recipeapp.R
import com.example.recipeapp.ui.components.MealCard
import com.example.recipeapp.ui.theme.Dimens

@Composable
fun CategoryScreen(
    categoryName: String,
    onMealClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = viewModel()
) {
    val viewState by viewModel.categoryState

    LaunchedEffect(categoryName) {
        viewModel.fetchMeals(categoryName)
    }

    Column(modifier = modifier.padding(Dimens.PaddingMedium)) {
        Text(
            text = stringResource(R.string.recipes_title, categoryName),
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

        when {
            viewState.loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            viewState.error != null -> {
                Text(text = stringResource(R.string.error_msg, viewState.error ?: ""))
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(viewState.list) { meal ->
                        MealCard(
                            meal = meal,
                            onClick = { onMealClick(meal.idMeal) }
                        )
                    }
                }
            }
        }
    }
}
