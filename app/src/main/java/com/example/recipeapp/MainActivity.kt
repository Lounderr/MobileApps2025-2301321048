package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.recipeapp.services.Category
import com.example.recipeapp.ui.theme.RecipeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("main_screen") {
                            MainScreen(
                                onCategoryClick = { category ->
                                    navController.navigate("category_screen/$category")
                                }
                            )
                        }
                        composable(
                            route = "category_screen/{categoryName}",
                            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                            CategoryScreen(categoryName = categoryName)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val viewState by viewModel.categoriesState

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Hello!",
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = "Choose a recipe.",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        when {
            viewState.loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            viewState.error != null -> {
                Text(text = "Error: ${viewState.error}")
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

@Composable
fun RecipeCategoryCard(
    category: Category,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = category.strCategoryThumb,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category.strCategory,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CategoryScreen(categoryName: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Selected Category: $categoryName",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    RecipeAppTheme {
        // MainScreen(onCategoryClick = {})
    }
}