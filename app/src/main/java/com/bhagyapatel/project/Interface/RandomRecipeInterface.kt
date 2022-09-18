package com.bhagyapatel.project.Interface

import com.bhagyapatel.project.DataClasses.RandomRecipe
import com.bhagyapatel.project.DataClasses.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomRecipeInterface {

    @GET("/recipes/random")
    suspend fun getRandomRecipe (@Query("number")number : Int,
                                 @Query("apiKey")apiKey : String) : Response<RandomRecipe>

}