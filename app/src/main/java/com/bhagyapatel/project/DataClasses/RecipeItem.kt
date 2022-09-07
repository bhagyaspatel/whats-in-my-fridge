package com.bhagyapatel.project.DataClasses

data class RecipeItem(
    val id: Int,
    val image: String,
    val likes: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<MissedIngredient>,
    val title: String,
    val usedIngredientCount: Int,
    val usedIngredients: List<UsedIngredient>
)