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
import com.bhagyapatel.project.Adapters.RandomRecipeAdapter
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.Interface.RandomRecipeInterface
import com.bhagyapatel.project.Interface.RetrofitHelpers.NodeRetrofitHelper
import com.bhagyapatel.project.Interface.RetrofitHelpers.RandomRetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.MVVM.Repository.RandomRecipeRepository
import com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals.NodeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.RandomRecipeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.NodeViewModalFactory
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.RandomRecipeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.ResponseDataClasses.ResponseRecipe
import com.bhagyapatel.project.Utils.Constants.RANDOM
import com.bhagyapatel.project.databinding.FragmentNewRecipeBinding

class NewRecipeFragment : Fragment() {
    
    private val TAG = "new_recipe_frag"
    
    private lateinit var binding : FragmentNewRecipeBinding
    private lateinit var viewModal: RandomRecipeViewModal
    private lateinit var adapter : RandomRecipeAdapter

    private lateinit var nodeViewModal: NodeViewModal

    private lateinit var collection : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collection = NewRecipeFragmentArgs.fromBundle(requireArguments()).collection

        if (collection == RANDOM){
            Log.d(TAG, "onViewCreated: random recipes has to be fetched")
            fetchRandomDishesh(view)
        }else{
            Log.d(TAG, "onViewCreated: specific recipe collection ${collection}")
            fetchCollectionRecipes(view, collection)
        }

        val tf : Typeface = resources.getFont(R.font.dancingscript)

        binding.collapsingToolbar.setExpandedTitleTypeface(tf);
        binding.collapsingToolbar.setCollapsedTitleTypeface(tf);
    }

    private fun fetchCollectionRecipes(view: View, collectionName : String) {
        val nodeInterface = NodeRetrofitHelper.getInstance().create(NodeInterface::class.java)
        val reopsitory = NodeRepository(nodeInterface)
        nodeViewModal = ViewModelProvider(this, NodeViewModalFactory(reopsitory))
            .get(NodeViewModal::class.java)

        val map = HashMap<String, String>()
        map.put("uuid", uuid!!)
        map.put("collectionName", collectionName)
        nodeViewModal.getCollectionRecipe(map)

        nodeViewModal.responseGetCollectionRecipe().observe(viewLifecycleOwner){
            if (it != null){
                binding.progressBar.visibility = View.GONE
                binding.randomRecipeRV.visibility = View.VISIBLE

                if (it.collectionRecipeData.size == 0){
                    setNoRecipeView(view)
                }
                else {
                    val list = ArrayList<ResponseRecipe>()
                    it.collectionRecipeData.forEach{
                        it.recipeList.forEach{
                            list.add(it)
                        }
                    }

                    adapter = RandomRecipeAdapter(requireContext(), list){
                        val sendData = NewRecipeFragmentDirections.actionNewRecipeFragmentToRandomSingleDishFragment(it)
                        Navigation.findNavController(view).navigate(sendData)
                    }
                    binding.randomRecipeRV.adapter = adapter
                    binding.randomRecipeRV.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                }
            }else{
                Log.d(TAG, "response of get collection recipes are null")
                binding.noRecipeText.visibility = View.VISIBLE
            }
        }
    }

    private fun setNoRecipeView(view : View) {
        binding.noRecipeText.visibility = View.VISIBLE
        binding.exploreDishBtn.visibility = View.VISIBLE

        binding.exploreDishBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.noRecipeText.visibility = View.GONE
            binding.exploreDishBtn.visibility = View.GONE
            fetchRandomDishesh(view)
        }
    }

    private fun fetchRandomDishesh(view: View) {
        val randomRecipe = RandomRetrofitHelper.getInstance().create(RandomRecipeInterface::class.java)
        val repository = RandomRecipeRepository(randomRecipe)
        viewModal = ViewModelProvider(this, RandomRecipeViewModalFactory(repository))
            .get(RandomRecipeViewModal::class.java)

        viewModal.recipe.observe(viewLifecycleOwner){ randomRecipe ->
            if (randomRecipe != null){
                binding.progressBar.visibility = View.GONE
                binding.randomRecipeRV.visibility = View.VISIBLE
                val list = ArrayList<ResponseRecipe>()
                randomRecipe.recipes.forEach{
                    val extended = ArrayList<String>()
                    it.extendedIngredients.forEach {
                        extended.add(it.original)
                    }
                    val responseRecipe = ResponseRecipe(it.aggregateLikes,it.id,it.image,it.instructions,it.summary,
                    it.title, it.vegan,it.vegetarian,it.readyInMinutes,it.servings,extended)
                    list.add(responseRecipe)
                }
                adapter = RandomRecipeAdapter(requireContext(), list){ recipe ->
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