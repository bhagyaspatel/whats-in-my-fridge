package com.bhagyapatel.project.MVVM.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bhagyapatel.project.DataClasses.RandomRecipe
import com.bhagyapatel.project.DataClasses.Recipe
import com.bhagyapatel.project.Interface.RandomRecipeInterface

class RandomRecipeRepository (private val randomRecipeInterface: RandomRecipeInterface) {
    private val randomRecipe = MutableLiveData<RandomRecipe>()

    val recipe : LiveData<RandomRecipe>
    get() = randomRecipe

    suspend fun getRandomRecipe(number : Int, apiKey : String){
        val response = randomRecipeInterface.getRandomRecipe(number, apiKey)

        if (response.body() != null){
            Log.d("random_repo", "getRandomRecipe: response body ${response.body()}")
            randomRecipe.postValue(response.body())
        }else{
            Log.d("random_repo", "getRandomRecipe: response body is null")
        }
    }

}