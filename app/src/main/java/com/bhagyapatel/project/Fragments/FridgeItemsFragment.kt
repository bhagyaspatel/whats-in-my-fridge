package com.bhagyapatel.project.Fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bhagyapatel.project.DataClasses.ListSelectedDish
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentFridgeItemsBinding
import com.google.android.material.chip.Chip

val myList : MutableList<String> = ArrayList()

class FridgeItemsFragment : Fragment() {

    private val TAG = "Fridge_fragment"
    private lateinit var binding : FragmentFridgeItemsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFridgeItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBtn.setOnClickListener {
            val item = binding.etItem.text.toString()

            if(item.isNullOrBlank()){
                Toast.makeText(requireContext(), "Please enter ingredient first", Toast.LENGTH_SHORT).show()
            } else{
                addChip(item)
                binding.etItem.text?.clear()
            }
        }

        binding.exploreDishBtn.setOnClickListener {

            for (i in 0 until binding.chipGroup.getChildCount()) {
                val chip : Chip = binding.chipGroup.getChildAt(i) as Chip
                val ingredient = chip.text.toString()
                myList.add(ingredient)
            }
            val FRIDGEITEM = ListSelectedDish(null)
            val sendData = FridgeItemsFragmentDirections.actionFridgeItemsFragment2ToRecipeFragment2(FRIDGEITEM)
            Navigation.findNavController(view).navigate(sendData)
        }
    }

    private fun addChip(item: String) {
        val chip = Chip(requireContext())
        chip.apply {
            text = item
            chipIcon = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_launcher_background
            )
            isChipIconVisible = false
            isCloseIconVisible = true
            isClickable = true
            isCheckable = true
            chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.app_orange))
            binding.apply {
                chipGroup.addView(chip as View)
                chip.setOnClickListener {
                    chipGroup.removeView(chip as View)
                }
            }
        }
    }
}