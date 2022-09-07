package com.bhagyapatel.project.MVVM.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bhagyapatel.project.DataClasses.RecipeItem
import com.bhagyapatel.project.Interface.RecipeInterface

class RecipeRepository (private val recipeInterface: RecipeInterface) {
    private val recipeList = MutableLiveData<List<RecipeItem>>()

    val recipe : LiveData<List<RecipeItem>>
    get() = recipeList

    suspend fun getRecipe(ingredient: String,
                          number : Int,
                          ranking : Int,
                          pantry : Boolean){
        val response = recipeInterface.getRecipes(ingredient,number,ranking,pantry)

        if(response.body() != null){
            recipeList.postValue(response.body())
        }
    }

}