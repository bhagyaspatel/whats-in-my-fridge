package com.bhagyapatel.project.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhagyapatel.project.Adapters.RandomRecipeAdapter
import com.bhagyapatel.project.Interface.RandomRecipeInterface
import com.bhagyapatel.project.Interface.RetrofitHelpers.RetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.RandomRecipeRepository
import com.bhagyapatel.project.MVVM.ViewModal.RandomRecipeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.RandomRecipeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private val TAG = "profile_frag"
    private lateinit var binding : FragmentProfileBinding

    private lateinit var adapter : RandomRecipeAdapter
    private lateinit var viewModal : RandomRecipeViewModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val randomRecipe = RetrofitHelper.getInstance().create(RandomRecipeInterface::class.java)
        val repository = RandomRecipeRepository(randomRecipe)
        viewModal = ViewModelProvider(this, RandomRecipeViewModalFactory(repository))
            .get(RandomRecipeViewModal::class.java)

        viewModal.recipe.observe(viewLifecycleOwner){ randomRecipe ->
            if (randomRecipe != null){
                Log.d(TAG, "onViewCreated: Profile recipes not null")
                binding.progressBar.visibility = View.GONE
                binding.savedRecipeRV.visibility = View.VISIBLE
                adapter = RandomRecipeAdapter(requireContext(), randomRecipe.recipes){ recipe ->
//                    val sendData = NewRecipeFragmentDirections.actionNewRecipeFragmentToRandomSingleDishFragment(recipe)
//                    Navigation.findNavController(view).navigate(sendData)
                }
                binding.savedRecipeRV.adapter = adapter
                binding.savedRecipeRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }else{
                Log.d(TAG, "random recipes are null")
                binding.savedRecipeRV.visibility = View.GONE
            }
        }

        binding.viewMoreBtn.setOnClickListener {
            binding.viewMoreBtn.setTextColor(resources.getColor(R.color.white))
        }

        binding.collectionRecipeBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: collection btn clicked")
            binding.collectionFrame.visibility = View.VISIBLE
            binding.savedFrame.visibility = View.GONE

            binding.collectionRecipeBtn.setBackgroundColor(resources.getColor(R.color.app_orange))
            binding.collectionRecipeBtn.setTextColor(resources.getColor(R.color.white))

            binding.savedRecipeBtn.setBackgroundColor(resources.getColor(R.color.white))
            binding.savedRecipeBtn.setTextColor(resources.getColor(R.color.app_orange))
        }

        binding.savedRecipeBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: saved btn clicked")
            binding.savedFrame.visibility = View.VISIBLE
            binding.collectionFrame.visibility = View.GONE

            binding.savedRecipeBtn.setBackgroundColor(resources.getColor(R.color.app_orange))
            binding.savedRecipeBtn.setTextColor(resources.getColor(R.color.white))

            binding.collectionRecipeBtn.setBackgroundColor(resources.getColor(R.color.white))
            binding.collectionRecipeBtn.setTextColor(resources.getColor(R.color.app_orange))
        }

    }
}