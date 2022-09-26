package com.bhagyapatel.project.Fragments.DialogFragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bhagyapatel.project.Utils.Constants.FEMALE_AVTAR
import com.bhagyapatel.project.Utils.Constants.GALLERY
import com.bhagyapatel.project.Utils.Constants.MALE_AVTAR
import com.bhagyapatel.project.databinding.FragmentChangeAvtarDialogBinding
import java.lang.ClassCastException

class ChangeAvtarDialogFragment : androidx.fragment.app.DialogFragment() {

    interface OnInputSelcted{
        fun sendInput(tag : String)
    }
    lateinit var onInputSelcted : OnInputSelcted

    private val TAG = "change_avtar_frag"
    private lateinit var binding : FragmentChangeAvtarDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeAvtarDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.optionMaleAvtar.setOnClickListener {
            Log.d(TAG, "onViewCreated: male")
            onInputSelcted.sendInput(MALE_AVTAR)
            dialog?.dismiss()
        }

        binding.optionFemaleAvtar.setOnClickListener {
            Log.d(TAG, "onViewCreated: female")
            onInputSelcted.sendInput(FEMALE_AVTAR)
            dialog?.dismiss()
        }

        binding.optionGallery.setOnClickListener {
            Log.d(TAG, "onViewCreated: gallery")
            onInputSelcted.sendInput(GALLERY)
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