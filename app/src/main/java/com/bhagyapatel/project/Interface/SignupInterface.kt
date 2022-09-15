package com.bhagyapatel.project.Interface

import com.bhagyapatel.project.DataClasses.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface SignupInterface {
    @POST("/signup")
    suspend fun signup(@Body map : HashMap<String, String>) : Response<ResponseData>

    @PUT("/saveRecipe")
    suspend fun saveRecipe(@Body map : HashMap<String, String>) : Response<ResponseData>
}