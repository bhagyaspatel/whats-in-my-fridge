package com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhagyapatel.project.MVVM.Repository.AuthRepository
import com.bhagyapatel.project.MVVM.ViewModal.AuthViewModal

class AuthViewModalFactory (private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModal(repository) as T
    }
}