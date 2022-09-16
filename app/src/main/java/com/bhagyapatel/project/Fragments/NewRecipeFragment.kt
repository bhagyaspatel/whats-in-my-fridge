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
import com.bhagyapatel.project.Adapters.RandomRecipeAdapter
import com.bhagyapatel.project.Interface.RandomRecipeInterface
import com.bhagyapatel.project.Interface.RetrofitHelpers.RetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.RandomRecipeRepository
import com.bhagyapatel.project.MVVM.ViewModal.MainViewModal
import com.bhagyapatel.project.MVVM.ViewModal.RandomRecipeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.MainViewModalFactory
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.RandomRecipeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentHomeBinding
import com.bhagyapatel.project.databinding.FragmentNewRecipeBinding

class NewRecipeFragment : Fragment() {
    
    private val TAG = "new_recipe_frag"
    
    private lateinit var binding : FragmentNewRecipeBinding
    private lateinit var viewModal: RandomRecipeViewModal
    private lateinit var adapter : RandomRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tf : Typeface = resources.getFont(R.font.dancingscript)

        binding.collapsingToolbar.setExpandedTitleTypeface(tf);
        binding.collapsingToolbar.setCollapsedTitleTypeface(tf);

        val randomRecipe = RetrofitHelper.getInstance().create(RandomRecipeInterface::class.java)
        val repository = RandomRecipeRepository(randomRecipe)
        viewModal = ViewModelProvider(this, RandomRecipeViewModalFactory(repository))
            .get(RandomRecipeViewModal::class.java)    
        
        viewModal.recipe.observe(viewLifecycleOwner){ randomRecipe ->
            if (randomRecipe != null){
                binding.progressBar.visibility = View.GONE
                binding.randomRecipeRV.visibility = View.VISIBLE
                adapter = RandomRecipeAdapter(requireContext(), randomRecipe.recipes){ recipe ->
                    val sendData = NewRecipeFragmentDirections.actionNewRecipeFragmentToRandomSingleDishFragment(recipe)
                    Navigation.findNavController(view).navigate(sendData)
                }
                binding.randomRecipeRV.adapter = adapter
                binding.randomRecipeRV.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            }else{
                Log.d(TAG, "random recipes are null")
            }
        }
    }

}