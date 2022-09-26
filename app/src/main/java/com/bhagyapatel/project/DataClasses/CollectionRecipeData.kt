package com.bhagyapatel.project.DataClasses

data class CollectionRecipeData(
    val user : String,
    val collectionName: String,
    val recipeList: ArrayList<Recipe>
)
