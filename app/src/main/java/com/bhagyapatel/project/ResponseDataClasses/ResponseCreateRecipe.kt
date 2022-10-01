package com.bhagyapatel.project.ResponseDataClasses

import com.bhagyapatel.project.RequestDataClasses.RequestCreateRecipe

data class ResponseCreateRecipe(
    val success : Boolean,
    val message : String,
    val createdRecipeList : List<RequestCreateRecipe>
)
