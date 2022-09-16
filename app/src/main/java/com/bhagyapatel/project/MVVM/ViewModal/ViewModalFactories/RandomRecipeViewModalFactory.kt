package com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhagyapatel.project.MVVM.Repository.RandomRecipeRepository
import com.bhagyapatel.project.MVVM.ViewModal.RandomRecipeViewModal

class RandomRecipeViewModalFactory(private val repository: RandomRecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RandomRecipeViewModal(repository) as T
    }
}