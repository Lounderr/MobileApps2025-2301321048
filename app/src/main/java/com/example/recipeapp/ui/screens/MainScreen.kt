package com.example.recipeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
    onMyRecipesClick: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val viewState by viewModel.categoriesState

    Column(modifier = modifier.padding(Dimens.PaddingMedium)) {
        Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
        Text(
            text = stringResource(R.string.welcome_display),
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = stringResource(R.string.welcome_subtitle),
            style = MaterialTheme.typography.titleLarge
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
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimens.CardHeightLarge)
                                .padding(vertical = Dimens.PaddingSmall)
                                .clickable { onMyRecipesClick() }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.my_recipes),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                    item {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = Dimens.PaddingSmall), // Add breathing room
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant // Optional: Custom color
                        )
                    }
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
