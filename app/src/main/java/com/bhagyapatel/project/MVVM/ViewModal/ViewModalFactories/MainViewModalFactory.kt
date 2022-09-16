package com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhagyapatel.project.MVVM.Repository.RecipeRepository
import com.bhagyapatel.project.MVVM.ViewModal.MainViewModal

class MainViewModalFactory(private val repository: RecipeRepository, val ingredient: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModal(repository, ingredient) as T
    }
}