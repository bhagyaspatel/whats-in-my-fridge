package com.bhagyapatel.project.MVVM.Repository.NodeRepositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.ResponseDataClasses.ResponseCollectionRecipeData
import com.bhagyapatel.project.ResponseDataClasses.ResponseData
import com.bhagyapatel.project.ResponseDataClasses.SavedRecipeData
import com.bhagyapatel.project.ResponseDataClasses.ResponseUserDetailData

class NodeRepository (private val nodeInterface: NodeInterface) {
    private val responseSignup = MutableLiveData<ResponseData>()
    private val responseUpdateUser = MutableLiveData<ResponseData>()
    private val responseSaveRecipe = MutableLiveData<ResponseData>()
    private val responseCollectionRecipe = MutableLiveData<ResponseData>()
    private val responseGetSaved = MutableLiveData<List<SavedRecipeData>>()

    private val responseGetUserDetail = MutableLiveData<ResponseUserDetailData>()
    private val responseGetCollectionRecipe = MutableLiveData<ResponseCollectionRecipeData>()

    val _responseSignup : LiveData<ResponseData>
    get() = responseSignup

    val _responseUpdateUser : LiveData<ResponseData>
    get() = responseUpdateUser

    val _responseSaveRecipe : LiveData<ResponseData>
    get() = responseSaveRecipe

    val _responseCollectionRecipe : LiveData<ResponseData>
    get() = responseCollectionRecipe

    val _responseGetSave : LiveData<List<SavedRecipeData>>
    get() = responseGetSaved

    val _responseGetUserDetail : LiveData<ResponseUserDetailData>
    get() = responseGetUserDetail

    val _responseGetCollectionRecipe : LiveData<ResponseCollectionRecipeData>
    get() = responseGetCollectionRecipe

    suspend fun signup (map : HashMap<String, String>){
        val response = nodeInterface.signup(map)

        if (response.body() != null){
            Log.d("auth_repo", "signup: request : ${map}")
            Log.d("auth_repo", "signup: response : ${response.body()}")
            responseSignup.postValue(response.body())
        }else{
            Log.d("auth_repo", "signup: response body is null")
        }
    }

    suspend fun updateUserDetails (map : HashMap<String, String>){
        val response = nodeInterface.updateUserdetail(map)

        if (response.body() != null){
            Log.d("auth_repo", "updateUser: request : ${map}")
            Log.d("auth_repo", "updateUser: response : ${response.body()}")
            responseUpdateUser.postValue(response.body())
        }else{
            Log.d("auth_repo", "updateUser: response body is null")
        }
    }

    suspend fun saveRecipe (id : String, map : HashMap<String, String>){
        Log.d("auth_repo", "authRepository saveRecipe called")

        val response = nodeInterface.addToSaveRecipe(id, map)

        if (response.body() != null){
            Log.d("auth_repo", "saveRecipe: request : ${map}")
            Log.d("auth_repo", "saveRecipe: response : ${response.body()}")
            responseSaveRecipe.postValue(response.body())
        }else{
            Log.d("auth_repo", "saveRecipe: response body is null")
        }
    }

    suspend fun collectionRecipe (map : HashMap<String, String>){
        Log.d("auth_repo", "authRepository add to collection recipe called")

        val response = nodeInterface.addToCollectionRecipe(map)

        if (response.body() != null){
            Log.d("auth_repo", "collectionRecipe: request : ${map}")
            Log.d("auth_repo", "collectionRecipe: response : ${response.body()}")
            responseCollectionRecipe.postValue(response.body())
        }else{
            Log.d("auth_repo", "collectionRecipe: response body is null")
        }
    }

    suspend fun getAllSavedRecipe (map : HashMap<String, String>){
        Log.d("auth_repo", "authRepository saveRecipe called")

        val response = nodeInterface.getAllSavedRecipes(map)

        if (response.body() != null){
            Log.d("auth_repo", "getSaveRecipe: request : ${map}")
            Log.d("auth_repo", "getsaveRecipe: response : ${response.body()}")
            responseGetSaved.postValue(response.body())
        }else{
            Log.d("auth_repo", "getsaveRecipe: response body is null")
        }
    }

    suspend fun getUserDetail (map : HashMap<String, String>){
        Log.d("auth_repo", "authRepository get user detail called")

        val response = nodeInterface.getUserDetail(map)
        if (response.body() != null){
            Log.d("auth_repo", "getUser: request : ${map}")
            Log.d("auth_repo", "getUser: response : ${response.body()}")
            responseGetUserDetail.postValue(response.body())
        }else{
            Log.d("auth_repo", "getUser: response body is null")
        }
    }

    suspend fun getCollectionRecipe(map : HashMap<String, String>){
        Log.d("auth_repo", "authRepository get collection recipe called")

        val response = nodeInterface.getCollectionRecipe(map)
        if (response.body() != null){
            Log.d("auth_repo", "getUser: request : ${map}")
            Log.d("auth_repo", "getUser: response : ${response.body()}")
            responseGetCollectionRecipe.postValue(response.body())
        }else{
            Log.d("auth_repo", "getUser: response body is null")
        }
    }
}