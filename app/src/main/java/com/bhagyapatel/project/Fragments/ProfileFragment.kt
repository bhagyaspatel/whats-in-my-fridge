package com.bhagyapatel.project.Fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhagyapatel.project.Activities.HomeActivity
import com.bhagyapatel.project.Activities.MainActivity
import com.bhagyapatel.project.Activities.uuid
import com.bhagyapatel.project.Adapters.ProfileSavedRecipeAdapter
import com.bhagyapatel.project.Animations.MorphButton
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.DataClasses.ListSelectedDish
import com.bhagyapatel.project.Fragments.DialogFragments.ChangeAvtarDialogFragment
import com.bhagyapatel.project.Interface.RetrofitHelpers.NodeRetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals.NodeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.NodeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.Utils.Constants.DESSERT_COLLECTION
import com.bhagyapatel.project.Utils.Constants.FASTFOOD_COLLECTION
import com.bhagyapatel.project.Utils.Constants.FEMALE_AVTAR
import com.bhagyapatel.project.Utils.Constants.MALE_AVTAR
import com.bhagyapatel.project.Utils.Constants.NONVEG_COLLECTION
import com.bhagyapatel.project.Utils.Constants.PICK_IMAGE_CODE
import com.bhagyapatel.project.Utils.Constants.STORAGE_REQUESTCODE
import com.bhagyapatel.project.Utils.Constants.VEG_COLLECTION
import com.bhagyapatel.project.Utils.FilePickUtils
import com.bhagyapatel.project.Utils.getColorX
import com.bhagyapatel.project.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import java.io.File

class ProfileFragment : Fragment(), ChangeAvtarDialogFragment.OnInputSelcted {


    private lateinit var savedRecipeList: ListSelectedDish
    private val TAG = "profile_frag"
    private lateinit var binding : FragmentProfileBinding

    private lateinit var adapter : ProfileSavedRecipeAdapter
    private lateinit var nodeViewModal: NodeViewModal

    private var username : String? = null
    private var imageURI: String? = null

    override fun sendInput(tag: String) {
        Log.d(TAG, "sendInput: ${tag}")
        if(tag == MALE_AVTAR){
            Toast.makeText(requireContext(), "Avatar updated", Toast.LENGTH_SHORT).show()
            imageURI = MALE_AVTAR
            binding.profilePic.setImageDrawable(resources.getDrawable(R.drawable.male_chef_avtar))
        }
        else if(tag == FEMALE_AVTAR){
            Toast.makeText(requireContext(), "Avatar updated", Toast.LENGTH_SHORT).show()
            imageURI = FEMALE_AVTAR
            binding.profilePic.setImageDrawable(resources.getDrawable(R.drawable.female_chef_avtar))
        }
        else{
            pickImage()
        }
    }

    //TODO : get user detail : username and its profile uri

    //TODO : after changing usernmae/profile pic make "update profile" btn visible and on click of
    // it make it gone and then update backend data and on success message from backend change the
    // view and show the toast
    
    //TODO : every time after fetching user details and if imageUri == null || male_Avtar dont do anything else change

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // NODE VIEW MODAL INSTACIATION
        val nodeInterface = NodeRetrofitHelper.getInstance().create(NodeInterface::class.java)
        val reopsitory = NodeRepository(nodeInterface)
        nodeViewModal = ViewModelProvider(this, NodeViewModalFactory(reopsitory))
            .get(NodeViewModal::class.java)

        fetchUserData()

        fetchSavedRecipes(view)

        binding.editBtn.setOnClickListener {
            showUsernameDialog()
        }

        binding.profilePicBtn.setOnClickListener {
            checkPermission()
        }

        binding.viewMoreBtn.setOnClickListener {
            binding.viewMoreBtn.setTextColor(resources.getColor(R.color.white))
            val sendData = ProfileFragmentDirections.actionProfileFragment3ToRecipeFragment(savedRecipeList)
            Navigation.findNavController(view).navigate(sendData)
        }

        binding.circularImageDessert.setOnClickListener{
            Log.d(TAG, "onViewCreated: dessert clicked")
            showCollection(view, DESSERT_COLLECTION)
        }
        binding.circularImageVegetarian.setOnClickListener{
            Log.d(TAG, "onViewCreated: veg clicked")
            showCollection(view, VEG_COLLECTION)
        }
        binding.circularImageNonVeg.setOnClickListener{
            Log.d(TAG, "onViewCreated: nonveg clicked")
            showCollection(view, NONVEG_COLLECTION)
        }
        binding.circularImageFastfood.setOnClickListener{
            Log.d(TAG, "onViewCreated: fastfood clicked")
            showCollection(view, FASTFOOD_COLLECTION)
        }

        binding.collectionRecipeBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: collection btn clicked")
            binding.collectionFrame.visibility = View.VISIBLE
            binding.savedFrame.visibility = View.GONE

            binding.collectionRecipeBtn.setBackgroundColor(resources.getColor(R.color.app_orange))
            binding.collectionRecipeBtn.setTextColor(resources.getColor(R.color.white))

            binding.savedRecipeBtn.setBackgroundColor(resources.getColor(R.color.white))
            binding.savedRecipeBtn.setTextColor(resources.getColor(R.color.app_orange))
        }

        binding.savedRecipeBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: saved btn clicked")
            binding.savedFrame.visibility = View.VISIBLE
            binding.collectionFrame.visibility = View.GONE

            binding.savedRecipeBtn.setBackgroundColor(resources.getColor(R.color.app_orange))
            binding.savedRecipeBtn.setTextColor(resources.getColor(R.color.white))

            binding.collectionRecipeBtn.setBackgroundColor(resources.getColor(R.color.white))
            binding.collectionRecipeBtn.setTextColor(resources.getColor(R.color.app_orange))
        }

        binding.morphUpdateProfileBtn.setOnClickListener {
            saveUserDetails()
        }

        binding.createRecipeBtn.setOnClickListener {
            val sendData = ProfileFragmentDirections.actionProfileFragment3ToCreateRecipeFragment()
            Navigation.findNavController(view).navigate(sendData)
        }
    }

    private fun checkPermission() {
        Log.d(TAG, "checkPermission: called")
        if(checkSelfPermission(requireContext(), android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermission: permission has to be asked")
                requestPermissions(
                    arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE), STORAGE_REQUESTCODE)
        }else{
            Log.d(TAG, "checkPermission: permission already granted")
            showProfileDialog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == STORAGE_REQUESTCODE){
            if(grantResults.isNotEmpty()){
                if(checkSelfPermission(requireContext(), Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                    Log.d(TAG, "onRequestPermissionsResult: checkSelfPermission granted")
                    showProfileDialog()
                }
                else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.MANAGE_EXTERNAL_STORAGE)){
                        val snackbar = Snackbar.make(
                            binding.container,
                            getString(R.string.permission_snackbar),
                            Snackbar.LENGTH_INDEFINITE
                        )
                        snackbar.setAction("OK") {
                            requestPermissions(
                                permissions,
                                STORAGE_REQUESTCODE
                            )
                        }
                        snackbar.show()
                    }else{
                        Toast.makeText(requireContext(), getString(R.string.permissionAllowGuidance), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Log.d(TAG, "else part in onRqstPermsnResult with toast")
                Toast.makeText(requireContext(), "Request to Permission was Denied", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "storage permission not granted")
        }
    }

    private fun fetchSavedRecipes(view : View) {
        val map = HashMap<String, String>()
        map.put("uuid", uuid!!)
        nodeViewModal.getSavedRecipe(map)

        nodeViewModal.responseGetSave().observe(viewLifecycleOwner){
            Log.d(TAG, "fetchSavedRecipes: ${it}")
            Log.d(TAG, "fetchSavedRecipes: ${it.savedRecipes}")

            if (it != null){
                Log.d(TAG, "onViewCreated: Profile recipes not null")
                binding.progressBar.visibility = View.GONE
                binding.savedRecipeRV.visibility = View.VISIBLE

                savedRecipeList = ListSelectedDish(it.savedRecipes)
                //TODO : we are just showing 4 items here show more on click of "view more"

                adapter = ProfileSavedRecipeAdapter(requireContext(), savedRecipeList.list!!.subList(0,4)){ recipe ->
                    val sendData = ProfileFragmentDirections.actionProfileFragment3ToSingleDishFragment(recipe)
                    Navigation.findNavController(view).navigate(sendData)
                    Log.d(TAG, "fetchSavedRecipes: itemview clicked from profile fragment")
                }
                binding.savedRecipeRV.adapter = adapter
                binding.savedRecipeRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }else{
                Log.d(TAG, "random recipes are null")
                binding.savedRecipeRV.visibility = View.GONE
            }
        }
    }

    private fun showCollection(view: View, collectionType: String) {
        val sendData = ProfileFragmentDirections.actionProfileFragment3ToNewRecipeFragment2(collectionType)
        Navigation.findNavController(view).navigate(sendData)
    }

    private fun fetchUserData() {
        val map = HashMap<String, String>()
        map.put("uuid", uuid!!)
        nodeViewModal.getUserDetail(map)

        nodeViewModal.responseUserDetail().observe(viewLifecycleOwner){
            Log.d(TAG, "fetchUserData: ${it}")
            binding.username.text = "@${it.username}"

            if (it.imageUri == FEMALE_AVTAR)
                binding.profilePic.setImageDrawable(resources.getDrawable(R.drawable.female_chef_avtar))
            else if (it.imageUri != MALE_AVTAR){
                val bitmap : Bitmap? = getBitmapFromString(it.imageUri!!)
                Log.d(TAG, "fetchUserData: bitmap ${bitmap}")
                if(bitmap != null)
                    binding.profilePic.setImageBitmap(bitmap)
            }
        }
    }

    private fun saveUserDetails() {
        lifecycleScope.launchWhenStarted {
            binding.morphUpdateProfileBtn.apply {
                iconDrawable.setTint(getColorX(R.color.app_light_orange))
                setUIState(MorphButton.UIState.Loading)
                delay(1500)
                setUIState(MorphButton.UIState.Button)
                visibility = View.GONE
            }
            val map = HashMap<String,String>()
            map.put("uuid", uuid!!)
            username?.let { map.put("username", it) }
            imageURI?.let { map.put("imageUri", it) }
            nodeViewModal.updateUserDetail(map)
            Toast.makeText(requireContext(), "Username Updated Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProfileDialog() {
        Log.d(TAG, "onViewCreated: profilepic btn clicked")
        val dialogFragment = ChangeAvtarDialogFragment()
        dialogFragment.setTargetFragment(this, 1)
        dialogFragment.show(this.requireFragmentManager(), "profileDialog")
    }

    private fun showUsernameDialog() {
        val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("New Username")

        val dialogLayout = layoutInflater.inflate(R.layout.alertdialogue_username, null)
        builder.setView(dialogLayout)

        builder.setPositiveButton("Update"){ dialog, which ->
            username = dialogLayout.findViewById<EditText>(R.id.et_username).text.toString()
            Log.d(TAG, "onViewCreated: ${username}")
            binding.username.text = "@${username}"
            binding.morphUpdateProfileBtn.visibility = View.VISIBLE
        }

        builder.setNegativeButton("Cancel"){ dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun pickImage() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        pickIntent.type = "image/*"
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")

        val chooserIntent = Intent.createChooser(getIntent, "Select application")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK || resultCode == PICK_IMAGE_CODE){
            val uri : Uri? = data!!.data

            val selectedImagePath = FilePickUtils.getPath(requireContext(), uri!!)

            val bitmap = BitmapFactory.decodeFile(selectedImagePath)

            Log.d(TAG, "onActivityResult: selectedImagePath ${selectedImagePath}")
            Log.d(TAG, "onActivityResult: bitmap ${bitmap}")
            Log.d(TAG, "onActivityResult: image uri: ${uri}")

            if(selectedImagePath == null){
                Toast.makeText(requireContext(), "Please select the image from gallery", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.profilePic.setImageURI(uri)
                imageURI = selectedImagePath
                binding.morphUpdateProfileBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun getBitmapFromString(fileString : String) : Bitmap? {
        val imageFile = File(fileString)
        val bmOptions = BitmapFactory.Options()
        var bitmap = BitmapFactory.decodeFile(imageFile.absolutePath, bmOptions)
        Log.d(TAG, "getBitmapFromString: ${bitmap}")
        return bitmap
    }


}