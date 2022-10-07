package com.bhagyapatel.project.Fragments

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.bhagyapatel.project.Activities.uuid
import com.bhagyapatel.project.Adapters.CreateRecipeAdapter
import com.bhagyapatel.project.Adapters.RandomRecipeAdapter
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.Interface.RetrofitHelpers.NodeRetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals.NodeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.NodeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentShowCreatedRecipeBinding


class ShowCreatedRecipeFragment : Fragment() {

    private lateinit var binding : FragmentShowCreatedRecipeBinding
    private val TAG = "show_created_frag"

    private lateinit var nodeViewModal: NodeViewModal
    private lateinit var adapter : CreateRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowCreatedRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nodeInterface = NodeRetrofitHelper.getInstance().create(NodeInterface::class.java)
        val reopsitory = NodeRepository(nodeInterface)
        nodeViewModal = ViewModelProvider(this, NodeViewModalFactory(reopsitory))
            .get(NodeViewModal::class.java)

        fetchCreatedRecipes()

        setDataObserver(view)

        val tf : Typeface = resources.getFont(R.font.dancingscript)

        binding.collapsingToolbar.setExpandedTitleTypeface(tf);
        binding.collapsingToolbar.setCollapsedTitleTypeface(tf);
    }

    private fun setDataObserver(view : View) {
        nodeViewModal.responseGetCreateRecipe().observe(viewLifecycleOwner){
            if(it!=null){
                if(it.success == true){
                    Log.d(TAG, "setDataObserver: ${it}")
                    
                    binding.progressBar.visibility = View.GONE

                    if(it.createdRecipeList.size ==0){
                        binding.noRecipeText.visibility = View.VISIBLE
                    }
                    else{
                        binding.createdRecipeRV.visibility = View.VISIBLE

                        it.createdRecipeList.forEach {

                        }

                        adapter = CreateRecipeAdapter(requireContext(), it.createdRecipeList){ recipe ->
                            val sendData = ShowCreatedRecipeFragmentDirections.actionShowCreatedRecipeFragment2ToRandomSingleDishFragment2(recipe)
                            Navigation.findNavController(view).navigate(sendData)
                        }
                        binding.createdRecipeRV.adapter = adapter
                        binding.createdRecipeRV.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                    }
                }
            }
        }
    }

    private fun fetchCreatedRecipes() {
        val map = HashMap<String, String>()
        map.put("uuid", uuid!!)

        nodeViewModal.getCreatedRecipe(map)
    }

}