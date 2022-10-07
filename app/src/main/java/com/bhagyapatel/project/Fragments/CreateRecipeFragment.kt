package com.bhagyapatel.project.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets.Type.ime
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat.toWindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bhagyapatel.project.Activities.uuid
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.Interface.RetrofitHelpers.NodeRetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals.NodeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.NodeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.RequestDataClasses.RequestCreateRecipe
import com.bhagyapatel.project.Utils.Constants
import com.bhagyapatel.project.Utils.FilePickUtils
import com.bhagyapatel.project.databinding.FragmentCreateRecipeBinding
import kotlinx.android.synthetic.main.fragment_create_recipe.*
import java.net.URL

class CreateRecipeFragment : Fragment() {

    private lateinit var binding : FragmentCreateRecipeBinding
    private val TAG = "create_recipe_frag"

    private var imageURI: String? = null
    private lateinit var nodeViewModal: NodeViewModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onResume() {
//        super.onResume()
//        activity?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
//
//        activity?.window?.decorView?.setOnApplyWindowInsetsListener { view, insets ->
//            val insetsCompat = toWindowInsetsCompat(insets, view)
//            mainNAvigation.isGone = insetsCompat.isVisible(ime())
//            view.onApplyWindowInsets(insets)
//        }
//
//        activity?.window?.decorView?.viewTreeObserver.addOnGlobalFocusChangeListener { oldView, newView ->
//            if (newView !is EditText) imm.hideSoftInputFromWindow(
//                (oldView ?: newView)?.windowToken
//                    ?: activity?.window?.attributes.token,
//                0 // or HIDE_IMPLICIT_ONLY
//            )
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.uploadImgBtn.setOnClickListener {
            pickImage()
        }

        binding.publishRecipeBtn.setOnClickListener {
            if (!validateData()) {

            } else {
                publishRecipe()
            }
        }
    }

    private fun publishRecipe() {
        Log.d(TAG, "publishRecipe: called")
        val nodeInterface = NodeRetrofitHelper.getInstance().create(NodeInterface::class.java)
        val reopsitory = NodeRepository(nodeInterface)
        nodeViewModal = ViewModelProvider(this, NodeViewModalFactory(reopsitory))
            .get(NodeViewModal::class.java)

        val isVegan = if (binding.radioVegan.isChecked) true else false
        val isVegetarian = if (binding.radioVeg.isChecked) true else false

        Log.d(TAG, "publishRecipe: ${binding.prepareTime.text.toString().toInt()}")
        Log.d(TAG, "publishRecipe: ${binding.serving.text.toString().toInt()}")

        val createdRecipe = RequestCreateRecipe(uuid!!, imageURI.toString(), binding.instruction.text.toString(),
            binding.summary.text.toString(), binding.title.text.toString(), isVegan, isVegetarian,
            binding.prepareTime.text.toString().toInt(), binding.serving.text.toString().toInt(),
            binding.ingredients.text.toString())

        Log.d(TAG, "publishRecipe: $createdRecipe")

        val map = HashMap<String, RequestCreateRecipe>()
        map.put("createdRecipe", createdRecipe)

        nodeViewModal.createRecipe(map)

        nodeViewModal.responseCreateRecipe().observe(viewLifecycleOwner){
            Log.d(TAG, "publishRecipe: ${it}")
            Toast.makeText(requireContext(), "Recipe created successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickImage() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")

        val chooserIntent = Intent.createChooser(getIntent, "Select application")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, Constants.PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK || resultCode == Constants.PICK_IMAGE_CODE){
            val uri : Uri? = data!!.data
            Log.d(TAG, "onActivityResult: image uri: ${uri}")
            val selectedImagePath = FilePickUtils.getPath(requireContext(), uri!!)

            imageURI = selectedImagePath
            Log.d(TAG, "onActivityResult: imageUri (uri.toString) = ${imageURI}")
        }
    }

    private fun validateData() : Boolean{
        var valid = true

        binding.title.setOnFocusChangeListener { view, hasFocus ->
            if (binding.title.text.isNullOrBlank()){
                binding.titleTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.invalid_red))
                valid = false
            }
            else{
                binding.titleTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                valid = true
            }
        }

        binding.ingredients.setOnFocusChangeListener { view, hasFocus ->
            if (binding.ingredients.text.isNullOrBlank()){
                binding.ingredientsTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.invalid_red))
                valid = false
            }
            else{
                binding.ingredientsTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                valid = true
            }
        }

        binding.serving.setOnFocusChangeListener { view, hasFocus ->
            if (binding.serving.text.isNullOrBlank()){
                binding.servingTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.invalid_red))
                valid = false
            }
            else{
                binding.servingTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                valid = true
            }
        }

        binding.prepareTime.setOnFocusChangeListener { view, hasFocus ->
            if (binding.prepareTime.text.isNullOrBlank()){
                binding.preparationTimeTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.invalid_red))
                valid = false
            }
            else{
                binding.preparationTimeTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                valid = true
            }
        }

        binding.instruction.setOnFocusChangeListener { view, hasFocus ->
            if (binding.instruction.text.isNullOrBlank()){
                binding.instructionTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.invalid_red))
                valid = false
            }
            else{
                binding.instructionTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                valid = true
            }
        }

        binding.summary.setOnFocusChangeListener { view, hasFocus ->
            if (binding.summary.text.isNullOrBlank()){
                binding.summaryTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.invalid_red))
                valid = false
            }
            else{
                binding.summaryTV.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                valid = true
            }
        }

        if (imageURI == null){
            valid = false
            Toast.makeText(requireContext(), "Make sure all fields are filled and upload the image", Toast.LENGTH_SHORT).show()
        }

        return valid
    }

}