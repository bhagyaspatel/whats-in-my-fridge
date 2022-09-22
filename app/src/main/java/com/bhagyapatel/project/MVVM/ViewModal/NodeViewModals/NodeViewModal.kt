package com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhagyapatel.project.ResponseDataClasses.ResponseData
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.ResponseDataClasses.ResponseCollectionRecipeData
import com.bhagyapatel.project.ResponseDataClasses.SavedRecipeData
import com.bhagyapatel.project.ResponseDataClasses.ResponseUserDetailData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NodeViewModal(val nodeRepository: NodeRepository) : ViewModel() {

//    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
//        throwable.printStackTrace()
//    }

    fun signup(map : HashMap<String,String>) {
        Log.d("Single_Dish_frag", "viewmodal signup called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.signup(map)
        }
    }
    fun responseSignup() : LiveData<ResponseData>{
        Log.d("view modal", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseSignup
    }

    fun updateUserDetail (map : HashMap<String, String>){
        Log.d("Single_Dish_frag", "viewmodal update user called")
        viewModelScope.launch (Dispatchers.IO){
            nodeRepository.updateUserDetails(map)
        }
    }
    fun responseUpdateUser() : LiveData<ResponseData>{
        Log.d("view modal", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseUpdateUser
    }

    fun saveRecipe(id : String, map : HashMap<String,String>){
        Log.d("Single_Dish_frag", "viewmodal saveRecipe called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.saveRecipe(id, map)
        }
    }
    fun responseSaveRecipe() : LiveData<ResponseData>{
        Log.d("view modal", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseSaveRecipe
    }

    fun collectionRecipe(id : String, map : HashMap<String,String>){
        Log.d("Single_Dish_frag", "viewmodal collection called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.saveRecipe(id, map)
        }
    }
    fun responseCollectionRecipe() : LiveData<ResponseData>{
        Log.d("view modal", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseCollectionRecipe
    }

    fun getSavedRecipe(map : HashMap<String,String>){
        Log.d("Single_Dish_frag", "viewmodal get all saved called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.getAllSavedRecipe(map)
        }
    }
    fun responseGetSave() : LiveData<List<SavedRecipeData>>{
        Log.d("view modal", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseGetSave
    }

    fun getUserDetail(map : HashMap<String,String>){
        Log.d("view modal", "viewmodal get user detail called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.getUserDetail(map)
        }
    }
    fun responseUserDetail() : LiveData<ResponseUserDetailData>{
        Log.d("view modal", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseGetUserDetail
    }

    fun getCollectionRecipe(collectionName : String, map : HashMap<String,String>){
        Log.d("view modal", "viewmodal get collection recipe called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.getCollectionRecipe(collectionName, map)
        }
    }
    fun responseGetCollectionRecipe() : LiveData<ResponseCollectionRecipeData>{
        Log.d("view modal", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseGetCollectionRecipe
    }

}