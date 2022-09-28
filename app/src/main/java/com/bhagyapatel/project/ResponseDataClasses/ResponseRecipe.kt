package com.bhagyapatel.project.ResponseDataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseRecipe(
    val aggregateLikes: Int,
    val id: Int,
    val image: String,
    val instructions: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val readyInMinutes : Int,
    val servings : Int,
    val extendedIngredients: ArrayList<String>
) : Parcelable
