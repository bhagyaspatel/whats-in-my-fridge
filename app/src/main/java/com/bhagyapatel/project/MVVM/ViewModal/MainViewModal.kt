package com.bhagyapatel.project.MVVM.ViewModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhagyapatel.project.DataClasses.RecipeItem
import com.bhagyapatel.project.MVVM.Repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModal(private val repository: RecipeRepository, val ingredients: String) : ViewModel() {

    private val API_KEY = "1483da310768438380cdaf564ca241c6"

    init{
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecipe(ingredients, 2, 2, true, API_KEY)
        }
    }

    val recipes : LiveData<List<RecipeItem>>
    get() = repository.recipe
}