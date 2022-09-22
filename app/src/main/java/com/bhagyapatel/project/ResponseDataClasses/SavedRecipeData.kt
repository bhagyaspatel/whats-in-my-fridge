package com.bhagyapatel.project.ResponseDataClasses

data class SavedRecipeData(
    val success : Boolean,
    val message : String,
    val recipeId: String,
    val title: String,
    val imageUrl: String,
    val ingredients: String
)
