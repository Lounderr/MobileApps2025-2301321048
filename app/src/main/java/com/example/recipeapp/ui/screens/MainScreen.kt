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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.MainViewModel
import com.example.recipeapp.R
import com.example.recipeapp.ui.components.RecipeCategoryCard
import com.example.recipeapp.ui.theme.Dimens

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val viewState by viewModel.categoriesState

    Column(modifier = modifier.padding(Dimens.PaddingMedium)) {
        Text(
            text = stringResource(R.string.hello),
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = stringResource(R.string.choose_a_recipe),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

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
                    items(viewState.list) { category ->
                        RecipeCategoryCard(
                            category = category,
                            onClick = { onCategoryClick(category.strCategory) }
                        )
                    }
                }
            }
        }
    }
}
