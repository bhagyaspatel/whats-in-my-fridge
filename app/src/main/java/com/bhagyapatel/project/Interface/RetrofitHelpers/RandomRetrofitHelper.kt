package com.bhagyapatel.project.Interface.RetrofitHelpers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RandomRetrofitHelper {
    private const val BASE_URL = "https://api.spoonacular.com/"

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}