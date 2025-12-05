package com.example.recipeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.recipeapp.MyRecipesViewModel
import com.example.recipeapp.R
import com.example.recipeapp.data.local.RecipeEntity
import com.example.recipeapp.ui.theme.Dimens
import androidx.compose.material.icons.filled.Delete

enum class EditableField {
    NAME, CATEGORY, INSTRUCTIONS, IMAGE
}

@Composable
fun MyRecipeDetailsScreen(
    recipeId: String,
    viewModel: MyRecipesViewModel,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var recipe by remember { mutableStateOf<RecipeEntity?>(null) }
    var editingField by remember { mutableStateOf<EditableField?>(null) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    LaunchedEffect(recipeId) {
        recipeId.toIntOrNull()?.let { id ->
            recipe = viewModel.getRecipeById(id)
        }
    }

    recipe?.let { currentRecipe ->
        Box(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box {
                    AsyncImage(
                        model = currentRecipe.imageUrl ?: "https://www.themealdb.com/images/media/meals/uyqrrv1511553350.jpg",
                        contentDescription = currentRecipe.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.MealImageHeight)
                    )
                    IconButton(
                        onClick = { editingField = EditableField.IMAGE },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(Dimens.PaddingSmall)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Image",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                
                Column(modifier = Modifier.padding(Dimens.PaddingMedium)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentRecipe.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { editingField = EditableField.NAME }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Title")
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentRecipe.category ?: "Uncategorized",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { editingField = EditableField.CATEGORY }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Category")
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.instructions),
                            style = MaterialTheme.typography.titleLarge
                        )
                        IconButton(onClick = { editingField = EditableField.INSTRUCTIONS }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Instructions")
                        }
                    }

                    Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
                    Text(
                        text = currentRecipe.instructions ?: "Add instructions here...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                    
                    Button(
                        onClick = { showDeleteConfirmation = true },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                        Spacer(modifier = Modifier.padding(Dimens.PaddingSmall))
                        Text("Delete Recipe")
                    }
                }
            }

            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    title = { Text("Delete Recipe") },
                    text = { Text("Are you sure you want to delete this recipe?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.deleteRecipe(currentRecipe)
                                onDelete()
                            },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteConfirmation = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            if (editingField != null) {
                EditDialog(
                    field = editingField!!,
                    currentValue = when (editingField!!) {
                        EditableField.NAME -> currentRecipe.name
                        EditableField.CATEGORY -> currentRecipe.category ?: ""
                        EditableField.INSTRUCTIONS -> currentRecipe.instructions ?: ""
                        EditableField.IMAGE -> currentRecipe.imageUrl ?: ""
                    },
                    onDismiss = { editingField = null },
                    onSave = { newValue ->
                        val updatedRecipe = when (editingField!!) {
                            EditableField.NAME -> currentRecipe.copy(name = newValue)
                            EditableField.CATEGORY -> currentRecipe.copy(category = newValue)
                            EditableField.INSTRUCTIONS -> currentRecipe.copy(instructions = newValue)
                            EditableField.IMAGE -> currentRecipe.copy(imageUrl = newValue)
                        }
                        viewModel.updateRecipe(updatedRecipe)
                        recipe = updatedRecipe
                        editingField = null
                    }
                )
            }
        }
    }
}

@Composable
fun EditDialog(
    field: EditableField,
    currentValue: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf(currentValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit ${field.name.lowercase().capitalize()}") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("New ${field.name.lowercase().capitalize()}") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = field != EditableField.INSTRUCTIONS,
                minLines = if (field == EditableField.INSTRUCTIONS) 5 else 1
            )
        },
        confirmButton = {
            Button(onClick = { onSave(text) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
