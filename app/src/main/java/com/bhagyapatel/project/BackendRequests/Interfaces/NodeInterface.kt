package com.bhagyapatel.project.BackendRequests.Interfaces

import com.bhagyapatel.project.ResponseDataClasses.ResponseCollectionRecipeData
import com.bhagyapatel.project.ResponseDataClasses.ResponseData
import com.bhagyapatel.project.ResponseDataClasses.SavedRecipeData
import com.bhagyapatel.project.ResponseDataClasses.ResponseUserDetailData
import retrofit2.Response
import retrofit2.http.*

interface NodeInterface {
    @POST("/signup")
    suspend fun signup(@Body map : HashMap<String, String>) : Response<ResponseData>

    @PUT("/updateUserDetails")
    suspend fun updateUserdetail (@Body map : HashMap<String,String>) : Response<ResponseData>

    @POST("/saveRecipe")
    suspend fun addToSaveRecipe(@Query("id") id : String,
                                @Body map : HashMap<String, String>) : Response<ResponseData>

    @POST("/collectionRecipe")
    suspend fun addToCollectionRecipe(@Query("id") id : String,
                                      @Body map : HashMap<String, String>) : Response<ResponseData>

    @GET("/get/saveRecipe")
    suspend fun getAllSavedRecipes (@Body map : HashMap<String, String>) : Response<List<SavedRecipeData>>

    @POST("/get/collectionRecipe")
    suspend fun getCollectionRecipe(@Query("collectionName") collectionName : String,
                           @Body map : HashMap<String, String>) : Response<ResponseCollectionRecipeData>

    @POST("/get/userDetail")
    suspend fun getUserDetail (@Body  map : HashMap<String, String>) : Response<ResponseUserDetailData>
}