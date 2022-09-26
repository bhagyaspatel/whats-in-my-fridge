package com.bhagyapatel.project.MVVM.ViewModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhagyapatel.project.DataClasses.RecipeItem
import com.bhagyapatel.project.MVVM.Repository.RecipeRepository
import com.bhagyapatel.project.Utils.Constants.APIKEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModal(private val repository: RecipeRepository, val ingredients: String) : ViewModel() {

    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipe(ingredients, APIKEY)
        }
    }

    val recipes : LiveData<List<RecipeItem>>
    get() = repository.recipe
}