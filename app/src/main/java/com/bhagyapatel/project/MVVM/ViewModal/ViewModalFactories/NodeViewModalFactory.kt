package com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals.NodeViewModal

class NodeViewModalFactory (private val repository: NodeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NodeViewModal(repository) as T
    }
}