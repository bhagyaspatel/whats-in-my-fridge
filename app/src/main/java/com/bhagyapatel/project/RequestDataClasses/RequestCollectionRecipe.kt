package com.bhagyapatel.project.RequestDataClasses

data class RequestCollectionRecipe (
    val uuid : String,
    val recipeId : Int,
    val collectionName : String,
    val aggregateLikes : Int,
    val imageUrl : String,
    val instructions : String,
    val summary : String,
    val title : String,
    val readyInMinutes : Int,
    val servings : Int,
    val extendedIngredients : ArrayList<String>,
    val vegan : Boolean,
    val vegetarian : Boolean
)