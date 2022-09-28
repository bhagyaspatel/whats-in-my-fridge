package com.bhagyapatel.project.DataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListSelectedDish(
    val list : List<SelectedDish>?
) : Parcelable
