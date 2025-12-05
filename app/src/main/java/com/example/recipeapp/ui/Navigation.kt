package com.example.recipeapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeapp.ui.screens.CategoryScreen
import com.example.recipeapp.ui.screens.MainScreen
import com.example.recipeapp.ui.screens.MealDetailScreen
import com.example.recipeapp.ui.screens.MyRecipesScreen

@Composable
fun RecipeApp(modifier: Modifier = Modifier) {
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
            MyRecipesScreen()
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
