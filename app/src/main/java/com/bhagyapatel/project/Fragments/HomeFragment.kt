package com.bhagyapatel.project.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val TAG = "Home_fragment"
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fridgeCheckBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: Fridge check Btn clicked")

            replaceFragment(FridgeItemsFragment())
//            findNavController().navigate(
//                HomeFragmentDirections.actionHomeFragmentToFridgeItemsFragment()
//            )
//            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_fridgeItemsFragment, null)
        }

        binding.newRecipeBtn.setOnClickListener {
            replaceFragment(NewRecipeFragment())
//            findNavController().navigate(
//                HomeFragmentDirections.actionHomeFragmentToNewRecipeFragment()
//            )
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        Log.d(TAG, "replaceFragment: home fragment to fridge item addition")
        val fragmentManager : FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.remove(HomeFragment())
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}