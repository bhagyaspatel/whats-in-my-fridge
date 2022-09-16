package com.bhagyapatel.project.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExtendedIngredient(
    val original: String
) : Parcelable