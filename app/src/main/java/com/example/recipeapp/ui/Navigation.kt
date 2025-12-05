package com.example.recipeapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.MyRecipesViewModel
import com.example.recipeapp.MyRecipesViewModelFactory
import com.example.recipeapp.data.LocalRecipeRepository
import com.example.recipeapp.ui.screens.CategoryScreen
import com.example.recipeapp.ui.screens.MainScreen
import com.example.recipeapp.ui.screens.MealDetailScreen
import com.example.recipeapp.ui.screens.MyRecipeDetailsScreen
import com.example.recipeapp.ui.screens.MyRecipesScreen

@Composable
fun RecipeApp(
    modifier: Modifier = Modifier,
    repository: LocalRecipeRepository
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main_screen",
        modifier = modifier
    ) {
        composable("main_screen") {
            MainScreen(
                onCategoryClick = { category ->
                    navController.navigate("category_screen/$category")
                },
                onMyRecipesClick = {
                    navController.navigate("my_recipes_screen")
                }
            )
        }
        composable("my_recipes_screen") {
            val viewModel: MyRecipesViewModel = viewModel(
                factory = MyRecipesViewModelFactory(repository)
            )
            MyRecipesScreen(
                viewModel = viewModel,
                onRecipeClick = { recipeId ->
                    navController.navigate("my_recipe_details_screen/$recipeId")
                }
            )
        }
        composable(
            route = "my_recipe_details_screen/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            val viewModel: MyRecipesViewModel = viewModel(
                factory = MyRecipesViewModelFactory(repository)
            )
            MyRecipeDetailsScreen(
                recipeId = recipeId,
                viewModel = viewModel,
                onDelete = { navController.popBackStack() }
            )
        }
        composable(
            route = "category_screen/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryScreen(
                categoryName = categoryName,
                onMealClick = { mealId ->
                    navController.navigate("meal_detail_screen/$mealId")
                }
            )
        }
        composable(
            route = "meal_detail_screen/{mealId}",
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
            MealDetailScreen(mealId = mealId)
        }
    }
}
