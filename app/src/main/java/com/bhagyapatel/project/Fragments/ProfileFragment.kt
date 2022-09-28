package com.bhagyapatel.project.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhagyapatel.project.Activities.uuid
import com.bhagyapatel.project.Adapters.DishRecipeAdapter
import com.bhagyapatel.project.Adapters.ProfileSavedRecipeAdapter
import com.bhagyapatel.project.Adapters.RandomRecipeAdapter
import com.bhagyapatel.project.Animations.MorphButton
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.DataClasses.ListSelectedDish
import com.bhagyapatel.project.DataClasses.RecipeItem
import com.bhagyapatel.project.DataClasses.SelectedDish
import com.bhagyapatel.project.Fragments.DialogFragments.ChangeAvtarDialogFragment
import com.bhagyapatel.project.Interface.RandomRecipeInterface
import com.bhagyapatel.project.Interface.RetrofitHelpers.NodeRetrofitHelper
import com.bhagyapatel.project.Interface.RetrofitHelpers.RetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.MVVM.Repository.RandomRecipeRepository
import com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals.NodeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.RandomRecipeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.NodeViewModalFactory
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.RandomRecipeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.Utils.Constants.DESSERT_COLLECTION
import com.bhagyapatel.project.Utils.Constants.FASTFOOD_COLLECTION
import com.bhagyapatel.project.Utils.Constants.FEMALE_AVTAR
import com.bhagyapatel.project.Utils.Constants.MALE_AVTAR
import com.bhagyapatel.project.Utils.Constants.NONVEG_COLLECTION
import com.bhagyapatel.project.Utils.Constants.PICK_IMAGE_CODE
import com.bhagyapatel.project.Utils.Constants.VEG_COLLECTION
import com.bhagyapatel.project.Utils.getColorX
import com.bhagyapatel.project.databinding.FragmentProfileBinding
import kotlinx.coroutines.delay

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
            showProfileDialog()
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

                adapter = ProfileSavedRecipeAdapter(requireContext(), savedRecipeList.list!!.subList(0,3)){ recipe ->
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
            else if (it.imageUri != MALE_AVTAR)
                binding.profilePic.setImageURI(Uri.parse(it.imageUri))
        //TODO : error while having picture as profile : Permission Denial: opening provider
        // com.android.providers.media.MediaDocumentsProvider from ProcessRecord{8c84bb0 12622:com.bhagyapatel.project/u0a519}
        //  (pid=12622, uid=10519) requires that you obtain access using ACTION_OPEN_DOCUMENT or related APIs
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
            Log.d(TAG, "onActivityResult: image uri: ${uri}")
            binding.profilePic.setImageURI(uri)
            imageURI = uri.toString()
            binding.morphUpdateProfileBtn.visibility = View.VISIBLE
            //TODO : save this uri in Bacekend; also store selected avtar string
        }
    }

}