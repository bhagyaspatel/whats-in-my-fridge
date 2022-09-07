package com.bhagyapatel.project.Interface

import com.bhagyapatel.project.DataClasses.MissedIngredient
import com.bhagyapatel.project.DataClasses.RecipeItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeInterface {

    @GET("/recipes/findByIngredients")
    suspend fun getRecipes (@Query("ingredients")ingredient: String,
                            @Query("number")number : Int,
                            @Query("ranking")ranking : Int,//2
                            @Query("ignorePantry")pantry : Boolean) : Response<List<RecipeItem>>
}