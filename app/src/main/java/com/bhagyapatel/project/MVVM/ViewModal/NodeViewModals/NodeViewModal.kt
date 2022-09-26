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
        Log.d("node_VM", "viewmodal signup called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.signup(map)
        }
    }
    fun responseSignup() : LiveData<ResponseData>{
        Log.d("node_VM", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseSignup
    }

    fun updateUserDetail (map : HashMap<String, String>){
        Log.d("node_VM", "viewmodal update user called")
        viewModelScope.launch (Dispatchers.IO){
            nodeRepository.updateUserDetails(map)
        }
    }
    fun responseUpdateUser() : LiveData<ResponseData>{
        Log.d("node_VM", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseUpdateUser
    }

    fun saveRecipe(id : String, map : HashMap<String,String>){
        Log.d("node_VM", "viewmodal saveRecipe called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.saveRecipe(id, map)
        }
    }
    fun responseSaveRecipe() : LiveData<ResponseData>{
        Log.d("node_VM", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseSaveRecipe
    }

    fun collectionRecipe(map : HashMap<String,String>){
        Log.d("node_VM", "viewmodal collection called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.collectionRecipe(map)
        }
    }
    fun responseCollectionRecipe() : LiveData<ResponseData>{
        Log.d("node_VM", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseCollectionRecipe
    }

    fun getSavedRecipe(map : HashMap<String,String>){
        Log.d("node_VM", "viewmodal get all saved called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.getAllSavedRecipe(map)
        }
    }
    fun responseGetSave() : LiveData<List<SavedRecipeData>>{
        Log.d("node_VM", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseGetSave
    }

    fun getUserDetail(map : HashMap<String,String>){
        Log.d("node_VM", "viewmodal get user detail called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.getUserDetail(map)
        }
    }
    fun responseUserDetail() : LiveData<ResponseUserDetailData>{
        Log.d("node_VM", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseGetUserDetail
    }

    fun getCollectionRecipe(map : HashMap<String,String>){
        Log.d("node_VM", "viewmodal get collection recipe called")
        viewModelScope.launch(Dispatchers.IO){
            nodeRepository.getCollectionRecipe(map)
        }
    }
    fun responseGetCollectionRecipe() : LiveData<ResponseCollectionRecipeData>{
        Log.d("node_VM", "responseUserDetail: ${nodeRepository._responseGetUserDetail}")
        return nodeRepository._responseGetCollectionRecipe
    }

}