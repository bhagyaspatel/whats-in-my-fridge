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
import com.bhagyapatel.project.Interface.RecipeInterface
import com.bhagyapatel.project.Interface.RetrofitHelpers.RetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.RecipeRepository
import com.bhagyapatel.project.MVVM.ViewModal.MainViewModal
import com.bhagyapatel.project.MVVM.ViewModal.MainViewModalFactory
import com.bhagyapatel.project.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    private val TAG = "Recipe_fragment"
    private lateinit var binding : FragmentRecipeBinding

    private lateinit var viewModal : MainViewModal
    private lateinit var adapter : DishRecipeAdapter

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

        val commaSeperatedString = myList.joinToString(",+")
        Log.d(TAG, "onViewCreated: ${commaSeperatedString}")

        val recipe = RetrofitHelper.getInstance().create(RecipeInterface::class.java)
        val repository = RecipeRepository(recipe)
        viewModal = ViewModelProvider(this, MainViewModalFactory(repository, commaSeperatedString))
            .get(MainViewModal::class.java)

        viewModal.recipes.observe(viewLifecycleOwner){ recipes->
            Log.d(TAG, "onViewCreated: ${recipes}")
            if(recipes != null){
                binding.progressBar.visibility = View.GONE
                binding.dishRV.visibility = View.VISIBLE
                adapter = DishRecipeAdapter(requireContext(), recipes){
//                    val bundle = Bundle()
//                    bundle.putParcelable("selectedDish", it)
//                    val fragment = Fragment()
//                    Log.d(TAG, "bundle is : ${bundle}")
//                    fragment.arguments = bundle
                    val sendData = RecipeFragmentDirections.actionRecipeFragment2ToSingleDishFragment(it)
                    Navigation.findNavController(view).navigate(sendData)
//                    findNavController().navigate(
//                        RecipeFragmentDirections.actionRecipeFragment2ToSingleDishFragment(it)
//                    )
//                    replaceFragment(SingleDishFragment())
                }
                binding.dishRV.adapter = adapter
                binding.dishRV.layoutManager = LinearLayoutManager(requireContext())
            }else{
                Log.d(TAG, "onViewCreated: API call successful but quotes is null")
            }
        }
    }

//    private fun replaceFragment(fragment: Fragment) {
//        Log.d(TAG, "replaceFragment: home fragment to ${fragment}")
//        val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
//        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
//        fragmentTransaction.commit()
//    }

}