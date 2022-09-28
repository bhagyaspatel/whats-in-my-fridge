package com.bhagyapatel.project.BackendRequests.Interfaces

import com.bhagyapatel.project.DataClasses.Recipe
import com.bhagyapatel.project.RequestDataClasses.RequestCollectionRecipe
import com.bhagyapatel.project.RequestDataClasses.RequestSaveRecipe
import com.bhagyapatel.project.ResponseDataClasses.ResponseCollectionRecipeData
import com.bhagyapatel.project.ResponseDataClasses.ResponseData
import com.bhagyapatel.project.ResponseDataClasses.ResponseSavedRecipeData
import com.bhagyapatel.project.ResponseDataClasses.ResponseUserDetailData
import retrofit2.Response
import retrofit2.http.*

interface NodeInterface {
    @POST("/signup")
    suspend fun signup(@Body map : HashMap<String, String>) : Response<ResponseData>

    @PUT("/updateUserDetails")
    suspend fun updateUserdetail (@Body map : HashMap<String,String>) : Response<ResponseData>

    @POST("/saveRecipe")
    suspend fun addToSaveRecipe(@Body map : HashMap<String, RequestSaveRecipe>) : Response<ResponseData>

    @POST("/collectionRecipe")
    suspend fun addToCollectionRecipe(@Body map : HashMap<String, RequestCollectionRecipe>) : Response<ResponseData>

    @POST("/get/saveRecipe")
    suspend fun getAllSavedRecipes (@Body map : HashMap<String, String>) : Response<ResponseSavedRecipeData>

    @POST("/get/collectionRecipe")
    suspend fun getCollectionRecipe(@Body map : HashMap<String, String>) : Response<ResponseCollectionRecipeData>

    @POST("/get/userDetail")
    suspend fun getUserDetail (@Body  map : HashMap<String, String>) : Response<ResponseUserDetailData>
}