package com.bhagyapatel.project.Interface.RetrofitHelpers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NodeRetrofitHelper {
    private const val BASE_URL = "http://192.168.1.12:4000/"
    //error if using http instead of https : UnknownServiceException: CLEARTEXT communication to 192.168.29.255 not permitted by network security policy

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}