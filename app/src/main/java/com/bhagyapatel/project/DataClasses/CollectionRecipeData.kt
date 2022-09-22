package com.bhagyapatel.project.DataClasses

import com.bhagyapatel.project.DataClasses.Recipe

data class CollectionRecipeData(
    val collectionName: String,
    val recipeList: List<Recipe>? = null
)
