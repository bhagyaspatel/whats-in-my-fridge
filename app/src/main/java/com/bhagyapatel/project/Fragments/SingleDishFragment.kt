package com.bhagyapatel.project.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bhagyapatel.project.DataClasses.SelectedDish
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentRecipeBinding
import com.bhagyapatel.project.databinding.FragmentSingleDishBinding

class SingleDishFragment : Fragment() {
    private val TAG = "Single_Dish_frag"
    private lateinit var binding : FragmentSingleDishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Single dish frag")
        val bundle = this.arguments
        if (bundle != null) {
            val selectedDish = bundle.getParcelable<SelectedDish>("selectedDish")
            Log.d(TAG, "onCreate: ${selectedDish}")
        }
        else{
            Log.d(TAG, "bundle is null")
        }
    }
}