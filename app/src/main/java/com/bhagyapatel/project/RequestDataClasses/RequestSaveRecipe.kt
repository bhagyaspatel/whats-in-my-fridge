package com.bhagyapatel.project.RequestDataClasses

data class RequestSaveRecipe(val recipeId : String,
                             val title : String,
                             val imageUrl : String,
                             val ingredients : List<String>,
                             val likes: String? = "0",
                             val uuid : String)
