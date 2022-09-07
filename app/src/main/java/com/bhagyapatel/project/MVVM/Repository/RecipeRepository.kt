package com.bhagyapatel.project.MVVM.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bhagyapatel.project.DataClasses.RecipeItem
import com.bhagyapatel.project.Interface.RecipeInterface

class RecipeRepository (private val recipeInterface: RecipeInterface) {
    private val recipeList = MutableLiveData<List<RecipeItem>>()

    val recipe : LiveData<List<RecipeItem>>
    get() = recipeList

    suspend fun getRecipe(ingredients: String,
                          number : Int,
                          ranking : Int,
                          pantry : Boolean,
                          apiKey : String){
        val response = recipeInterface.getRecipes(ingredients,number,ranking,pantry, apiKey)

        if(response.body() != null){
            Log.d("Recipe_repo", "getRecipe: response body not null ${response.body()}")
            recipeList.postValue(response.body())
        }
        else{
            Log.d("Recipe_repo", "getRecipe: response body is null : ${response.body()}")
        }
    }

}