package com.bhagyapatel.project.MVVM.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bhagyapatel.project.DataClasses.ResponseData
import com.bhagyapatel.project.Interface.SignupInterface

class AuthRepository (private val signupInterface: SignupInterface) {
    private val responseBody = MutableLiveData<ResponseData>()

    val response : LiveData<ResponseData>
    get() = responseBody

    suspend fun signup (map : HashMap<String, String>){
        val response = signupInterface.signup(map)

        if (response.body() != null){
            Log.d("auth_repo", "signup: request : ${map}")
            Log.d("auth_repo", "signup: response : ${response.body()}")
            responseBody.postValue(response.body())
        }else{
            Log.d("auth_repo", "signup: response body is null")
        }
    }

    suspend fun saveRecipe (map : HashMap<String, String>){
        Log.d("auth_repo", "authRepository saveRecipe called")

        val response = signupInterface.saveRecipe(map)

        if (response.body() != null){
            Log.d("auth_repo", "saveRecipe: request : ${map}")
            Log.d("auth_repo", "saveRecipe: response : ${response.body()}")
            responseBody.postValue(response.body())
        }else{
            Log.d("auth_repo", "saveRecipe: response body is null")
        }
    }
}