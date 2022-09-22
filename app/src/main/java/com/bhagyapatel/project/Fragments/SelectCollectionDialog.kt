package com.bhagyapatel.project.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bhagyapatel.project.R
import com.bhagyapatel.project.Utils.Constants
import com.bhagyapatel.project.databinding.FragmentChangeAvtarDialogBinding
import com.bhagyapatel.project.databinding.FragmentSelectCollectionDialogBinding
import java.lang.ClassCastException

class SelectCollectionDialog : androidx.fragment.app.DialogFragment() {

    interface OnInputSelcted{
        fun sendInput(option : String)
    }
    lateinit var onInputSelcted : OnInputSelcted

    private val TAG = "select_collection_dialog"
    private lateinit var binding : FragmentSelectCollectionDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectCollectionDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.optionDessert.setOnClickListener {
            Log.d(TAG, "onViewCreated: dessert")
            onInputSelcted.sendInput(Constants.DESSERT_COLLECTION)
            dialog?.dismiss()
        }

        binding.optionVeg.setOnClickListener {
            Log.d(TAG, "onViewCreated: veg")
            onInputSelcted.sendInput(Constants.VEG_COLLECTION)
            dialog?.dismiss()
        }

        binding.optionNonveg.setOnClickListener {
            Log.d(TAG, "onViewCreated: nonveg")
            onInputSelcted.sendInput(Constants.NONVEG_COLLECTION)
            dialog?.dismiss()
        }

        binding.optionFastfood.setOnClickListener {
            Log.d(TAG, "onViewCreated: fastfood")
            onInputSelcted.sendInput(Constants.FASTFOOD_COLLECTION)
            dialog?.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onInputSelcted = getTargetFragment() as OnInputSelcted
        }catch (e : ClassCastException){
            Log.d(TAG, "onAttach: ${e.message} :: ${e.printStackTrace()}")
        }
    }
}