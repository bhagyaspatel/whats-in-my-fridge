package com.bhagyapatel.project.MVVM.ViewModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhagyapatel.project.DataClasses.RandomRecipe
import com.bhagyapatel.project.DataClasses.Recipe
import com.bhagyapatel.project.MVVM.Repository.RandomRecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RandomRecipeViewModal(private val repository: RandomRecipeRepository) : ViewModel() {
    private val apiKey = "1483da310768438380cdaf564ca241c6"

    init{
        viewModelScope.launch(Dispatchers.IO){
            repository.getRandomRecipe(10, apiKey)
        }
    }

    val recipe : LiveData<RandomRecipe>
    get() = repository.recipe
}