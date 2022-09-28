package com.bhagyapatel.project.DataClasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectedDish(
    val recipeId: Int,
    val title: String,
    @SerializedName("imageUrl")
    val image: String,
    val ingredients: List<String>,
    val likes: String? = null
) : Parcelable
