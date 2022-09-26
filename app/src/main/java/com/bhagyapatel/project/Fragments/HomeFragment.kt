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
import com.bhagyapatel.project.Utils.Constants.RANDOM
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
        Log.d(TAG, "onViewCreated: home fragment")

        binding.fridgeCheckBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: Fridge check Btn clicked")
            val sendData = HomeFragmentDirections.actionHomeFragment2ToFridgeItemsFragment2()
            Navigation.findNavController(view).navigate(sendData)
//            findNavController().navigate(
//               HomeFragmentDirections.actionHomeFragment2ToFridgeItemsFragment2()
//            )
//            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_fridgeItemsFragment, null)
        }

        binding.newRecipeBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: New Recipe Btn clicked")

            val sendData = HomeFragmentDirections.actionHomeFragment2ToNewRecipeFragment(RANDOM)
            Navigation.findNavController(view).navigate(sendData)
        }
    }
}