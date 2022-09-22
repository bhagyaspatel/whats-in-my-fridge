package com.bhagyapatel.project.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val aggregateLikes: Int,
//    val analyzedInstructions: List<AnalyzedInstruction>,
    val id: Int,
    val image: String,
    val instructions: String,
//    val spoonacularSourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val readyInMinutes : Int,
    val servings : Int,
    val extendedIngredients: List<ExtendedIngredient>
) : Parcelable