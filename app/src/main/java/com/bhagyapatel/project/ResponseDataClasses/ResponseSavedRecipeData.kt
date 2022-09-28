package com.bhagyapatel.project.ResponseDataClasses

import com.bhagyapatel.project.DataClasses.SelectedDish

data class ResponseSavedRecipeData(
    val success : Boolean,
    val message : String,
    val savedRecipes : List<SelectedDish>
)
