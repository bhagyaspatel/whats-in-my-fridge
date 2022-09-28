package com.bhagyapatel.project.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhagyapatel.project.Adapters.DishRecipeAdapter
import com.bhagyapatel.project.DataClasses.ListSelectedDish
import com.bhagyapatel.project.DataClasses.RecipeItem
import com.bhagyapatel.project.DataClasses.SelectedDish
import com.bhagyapatel.project.DataClasses.UsedIngredient
import com.bhagyapatel.project.Interface.RecipeInterface
import com.bhagyapatel.project.Interface.RetrofitHelpers.RetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.RecipeRepository
import com.bhagyapatel.project.MVVM.ViewModal.MainViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.MainViewModalFactory
import com.bhagyapatel.project.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    private val TAG = "Recipe_fragment"
    private lateinit var binding: FragmentRecipeBinding

    private lateinit var viewModal: MainViewModal
    private lateinit var adapter: DishRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Recipe fragment")


        val list = RecipeFragmentArgs.fromBundle(requireArguments()).list

        if (list.list == null) {
            fetchFridgeItems(view)
        } else {
            showProfileSavedRecipes(view, list.list)
        }

    }

    private fun showProfileSavedRecipes(view: View, list : List<SelectedDish>) {
        binding.progressBar.visibility = View.GONE
        binding.dishRV.visibility = View.VISIBLE
        val recipeList = ArrayList<RecipeItem>()
        list.forEach {
            val ingredientsList = ArrayList<UsedIngredient>()
            it.ingredients.forEach {
                val usedIngredient = UsedIngredient(it)
                ingredientsList.add(usedIngredient)
            }
            val recipeItem = RecipeItem(it.recipeId, it.image, it.likes!!.toInt(),
                null, null, it.title, null,
                ingredientsList)
            recipeList.add(recipeItem)
        }
        adapter = DishRecipeAdapter(requireContext(), recipeList) { recipe ->
            val sendData = RecipeFragmentDirections.actionRecipeFragment2ToSingleDishFragment(recipe)
            Navigation.findNavController(view).navigate(sendData)
        }
        binding.dishRV.adapter = adapter
        binding.dishRV.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun fetchFridgeItems(view: View) {
        val commaSeperatedString = myList.joinToString(",+")

        val recipe = RetrofitHelper.getInstance().create(RecipeInterface::class.java)
        val repository = RecipeRepository(recipe)
        viewModal = ViewModelProvider(this, MainViewModalFactory(repository, commaSeperatedString))
            .get(MainViewModal::class.java)

        viewModal.recipes.observe(viewLifecycleOwner) { recipes ->
            Log.d(TAG, "onViewCreated: ${recipes}")
            if (recipes != null) {
                binding.progressBar.visibility = View.GONE
                binding.dishRV.visibility = View.VISIBLE
                adapter = DishRecipeAdapter(requireContext(), recipes) { recipe ->
                    val sendData =
                        RecipeFragmentDirections.actionRecipeFragment2ToSingleDishFragment(recipe)
                    Navigation.findNavController(view).navigate(sendData)
                }
                binding.dishRV.adapter = adapter
                binding.dishRV.layoutManager = LinearLayoutManager(requireContext())
            } else {
                Log.d(TAG, "onViewCreated: API call successful but quotes is null")
            }
        }
    }
}