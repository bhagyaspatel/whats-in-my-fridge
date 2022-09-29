package com.bhagyapatel.project.RequestDataClasses

data class RequestCreateRecipe(
    val uuid : String,
    val imageUrl : String,
    val instructions : String,
    val summary : String,
    val title : String,
    val vegan : Boolean,
    val vegetarian : Boolean,
    val readyInMinutes : Int,
    val servings : Int,
    val ingredients : String
)
