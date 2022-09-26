package com.bhagyapatel.project.ResponseDataClasses

import com.bhagyapatel.project.DataClasses.CollectionRecipeData

data class ResponseCollectionRecipeData(
    val success: Boolean,
    val message: String,
    val collectionRecipeData: List<CollectionRecipeData>
)