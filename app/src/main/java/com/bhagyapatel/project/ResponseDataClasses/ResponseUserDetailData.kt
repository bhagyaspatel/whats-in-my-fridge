package com.bhagyapatel.project.ResponseDataClasses

import com.bhagyapatel.project.Utils.Constants.MALE_AVTAR

data class ResponseUserDetailData(
    val success: Boolean,
    val message: String,
    val username: String,
    val imageUri: String? = MALE_AVTAR
)
