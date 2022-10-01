package com.bhagyapatel.project.MVVM.Repository.NodeRepositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.DataClasses.Recipe
import com.bhagyapatel.project.RequestDataClasses.RequestCollectionRecipe
import com.bhagyapatel.project.RequestDataClasses.RequestCreateRecipe
import com.bhagyapatel.project.RequestDataClasses.RequestSaveRecipe
import com.bhagyapatel.project.ResponseDataClasses.*

class NodeRepository (private val nodeInterface: NodeInterface) {
    private val responseSignup = MutableLiveData<ResponseData>()
    private val responseUpdateUser = MutableLiveData<ResponseData>()
    private val responseSaveRecipe = MutableLiveData<ResponseData>()
    private val responseCollectionRecipe = MutableLiveData<ResponseData>()
    private val responseGetSaved = MutableLiveData<ResponseSavedRecipeData>()
    private val responseGetUserDetail = MutableLiveData<ResponseUserDetailData>()
    private val responseGetCollectionRecipe = MutableLiveData<ResponseCollectionRecipeData>()
    private val responseCreateRecipe = MutableLiveData<ResponseData>()
    private val responseGetCreateRecipe = MutableLiveData<ResponseCreateRecipe>()

    val _responseSignup : LiveData<ResponseData>
    get() = responseSignup

    val _responseUpdateUser : LiveData<ResponseData>
    get() = responseUpdateUser

    val _responseSaveRecipe : LiveData<ResponseData>
    get() = responseSaveRecipe

    val _responseCollectionRecipe : LiveData<ResponseData>
    get() = responseCollectionRecipe

    val _responseGetSave : LiveData<ResponseSavedRecipeData>
    get() = responseGetSaved

    val _responseGetUserDetail : LiveData<ResponseUserDetailData>
    get() = responseGetUserDetail

    val _responseGetCollectionRecipe : LiveData<ResponseCollectionRecipeData>
    get() = responseGetCollectionRecipe

    val _responseCreateRecipe : LiveData<ResponseData>
    get() = responseCreateRecipe

    val _responseGetCreateRecipe : LiveData<ResponseCreateRecipe>
    get() = responseGetCreateRecipe

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

    suspend fun saveRecipe (map : HashMap<String, RequestSaveRecipe>){
        Log.d("auth_repo", "authRepository saveRecipe called")

        val response = nodeInterface.addToSaveRecipe(map)

        if (response.body() != null){
            Log.d("auth_repo", "saveRecipe: request : ${map}")
            Log.d("auth_repo", "saveRecipe: response : ${response.body()}")
            responseSaveRecipe.postValue(response.body())
        }else{
            Log.d("auth_repo", "saveRecipe: response body is null")
        }
    }

    suspend fun collectionRecipe (map : HashMap<String, RequestCollectionRecipe>){
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

    suspend fun createRecipe(map : HashMap<String, RequestCreateRecipe>){
        Log.d("auth_repo", "authRepository add created recipe called")

        val response = nodeInterface.addToCreatedRecipe(map)
        if (response.body() != null){
            Log.d("auth_repo", "getUser: request : ${map}")
            Log.d("auth_repo", "getUser: response : ${response.body()}")
            responseCreateRecipe.postValue(response.body())
        }else{
            Log.d("auth_repo", "getUser: response body is null")
        }
    }

    suspend fun getCreatedRecipe(map : HashMap<String, String>){
        Log.d("auth_repo", "authRepository get create recipe called")

        val response = nodeInterface.getCreateRecipe(map)

        if (response.body() != null){
            Log.d("auth_repo", "getUser: request : ${map}")
            Log.d("auth_repo", "getUser: response : ${response.body()}")
            responseGetCreateRecipe.postValue(response.body())
        }else{
            Log.d("auth_repo", "getUser: response body is null")
        }
    }

}