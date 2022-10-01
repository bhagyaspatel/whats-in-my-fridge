package com.bhagyapatel.project.Interface.RetrofitHelpers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NodeRetrofitHelper {
    private const val BASE_URL = "http://192.168.1.5:4000/"

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}