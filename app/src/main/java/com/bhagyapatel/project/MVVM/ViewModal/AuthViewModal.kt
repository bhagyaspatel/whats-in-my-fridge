package com.bhagyapatel.project.MVVM.ViewModal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhagyapatel.project.DataClasses.ResponseData
import com.bhagyapatel.project.MVVM.Repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModal(val authRepository: AuthRepository) : ViewModel() {
    lateinit var response : LiveData<ResponseData>

    fun signup(map : HashMap<String,String>) {
        viewModelScope.launch(Dispatchers.IO){
            authRepository.signup(map)
        }
        response = authRepository.response
    }

    fun saveRecipe(map : HashMap<String,String>){
        Log.d("Single_Dish_frag", "viewmodal saveRecipe called")
        viewModelScope.launch(Dispatchers.IO){
            authRepository.saveRecipe(map)
        }
        response = authRepository.response
    }

}