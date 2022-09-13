package com.bhagyapatel.project.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectedDish(val title : String,
                        val image : String,
                        val ingredients : List<String>) : Parcelable
