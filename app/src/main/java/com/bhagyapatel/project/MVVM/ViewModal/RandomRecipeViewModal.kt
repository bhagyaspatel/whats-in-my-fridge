package com.bhagyapatel.project.MVVM.ViewModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhagyapatel.project.DataClasses.RandomRecipe
import com.bhagyapatel.project.MVVM.Repository.RandomRecipeRepository
import com.bhagyapatel.project.Utils.Constants.APIKEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RandomRecipeViewModal(private val repository: RandomRecipeRepository) : ViewModel() {
    init{
        viewModelScope.launch(Dispatchers.IO){
            repository.getRandomRecipe(10, APIKEY)
        }
    }

    val recipe : LiveData<RandomRecipe>
    get() = repository.recipe
}