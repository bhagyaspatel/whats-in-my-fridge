package com.bhagyapatel.project.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentHomeBinding
import com.bhagyapatel.project.databinding.FragmentSavedRecipeBinding

class SavedRecipeFragment : Fragment() {

    private val TAG = "Saved_recipe"
    private lateinit var binding : FragmentSavedRecipeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

}