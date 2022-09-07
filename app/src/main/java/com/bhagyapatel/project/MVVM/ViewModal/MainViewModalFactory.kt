package com.bhagyapatel.project.MVVM.ViewModal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhagyapatel.project.MVVM.Repository.RecipeRepository

class MainViewModalFactory(private val repository: RecipeRepository, val ingredient: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModal(repository, ingredient) as T
    }
}