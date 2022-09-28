package com.bhagyapatel.project.DataClasses

import com.bhagyapatel.project.ResponseDataClasses.ResponseRecipe

data class CollectionRecipeData(
    val user : String,
    val collectionName: String,
    val recipeList: ArrayList<ResponseRecipe>
)
