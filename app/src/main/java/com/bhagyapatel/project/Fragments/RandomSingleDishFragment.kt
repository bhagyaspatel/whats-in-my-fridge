package com.bhagyapatel.project.Fragments

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bhagyapatel.project.Activities.uuid
import com.bhagyapatel.project.Animations.MorphButton
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.DataClasses.Recipe
import com.bhagyapatel.project.Fragments.DialogFragments.SelectCollectionDialog
import com.bhagyapatel.project.Interface.RetrofitHelpers.NodeRetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals.NodeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.NodeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.RequestDataClasses.RequestCollectionRecipe
import com.bhagyapatel.project.ResponseDataClasses.ResponseRecipe
import com.bhagyapatel.project.Utils.getColorX
import com.bhagyapatel.project.databinding.FragmentRandomSingleDishBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay

class RandomSingleDishFragment : Fragment(), SelectCollectionDialog.OnInputSelcted {

    private val TAG = "random_single_dish"

    private lateinit var binding : FragmentRandomSingleDishBinding
    private lateinit var dish : ResponseRecipe
    private lateinit var nodeViewModal: NodeViewModal

    override fun sendInput(option: String) {
        Log.d(TAG, "sendInput: ${option}")
        saveCollection(option)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomSingleDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dish = RandomSingleDishFragmentArgs.fromBundle(requireArguments()).dish
        Log.d(TAG, "onCreate single dish frag ${dish}")

        setView(dish)

        binding.morphSaveButton.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                binding.morphSaveButton.apply {
                    iconDrawable.setTint(getColorX(R.color.app_light_orange))
                    setUIState(MorphButton.UIState.Loading)
                    delay(1500)
                    showCollectionOptionDialog()
                }

            }
        }
    }

    private fun showCollectionOptionDialog() {
        val dialogFragment = SelectCollectionDialog()
        dialogFragment.setTargetFragment(this, 1)
        dialogFragment.show(this.requireFragmentManager(), "saveCollection dialog")
    }

    private fun saveCollection(option: String) {
        val nodeInterface = NodeRetrofitHelper.getInstance().create(NodeInterface::class.java)
        val reopsitory = NodeRepository(nodeInterface)
        nodeViewModal = ViewModelProvider(this, NodeViewModalFactory(reopsitory))
            .get(NodeViewModal::class.java)

//        val map = HashMap<String, String>()
//        map.put("uuid", uuid!!)
//        map.put("recipeId", dish.id.toString())
//        map.put("collectionName", option)
//        map.put("aggregateLikes", dish.aggregateLikes.toString())
//        map.put("imageUrl", dish.image)
//        map.put("instructions", dish.instructions)
//        map.put("summary", dish.summary)
//        map.put("title", dish.title)
//        map.put("readyInMinutes", dish.readyInMinutes.toString())
//        map.put("servings", dish.servings.toString())
//
////        val list = ArrayList<String>()
////        dish.extendedIngredients.forEach{
////            list.add(it.original)
////        }
////        map.put("extendedIngredients", list.toString())
//
//        map.put("extendedIngredients", dish.extendedIngredients.toString()) //TODO : this is saved as array of string we want array of objects
//        map.put("vegan", dish.vegan.toString())
//        map.put("vegetarian", dish.vegetarian.toString())

        val map = HashMap<String, RequestCollectionRecipe>()
//        val extended = ArrayList<String>()
//        dish.extendedIngredients.forEach {
//            extended.add(it.original)
//        }

        Log.d(TAG, "saveCollection: extended : ${dish.extendedIngredients}")

        val requestCollectionRecipe = RequestCollectionRecipe(uuid!!, dish.id, option, dish.aggregateLikes,
            dish.image, dish.instructions, dish.summary, dish.title, dish.readyInMinutes, dish.servings,
            dish.extendedIngredients, dish.vegan, dish.vegetarian)

//        Log.d(TAG, "saveCollection: " + dish.extendedIngredients.toString())
//        Log.d(TAG, "saveCollection: " + dish.extendedIngredients)

        map.put("recipe", requestCollectionRecipe)
        nodeViewModal.collectionRecipe(map)

        nodeViewModal.responseCollectionRecipe().observe(viewLifecycleOwner){
            Log.d(TAG, "saveCollection: ${it}")
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

            if (it.success == true){
                binding.morphSaveButton.setUIState(MorphButton.UIState.Button)
            }
        }
    }

    private fun setView(dish: ResponseRecipe) {
        if(dish.vegan)
            binding.veganLL.visibility = View.VISIBLE
        if(dish.vegetarian)
            binding.vegLL.visibility = View.VISIBLE

        binding.likeCount.text = dish.aggregateLikes.toString()
        binding.serveCount.text = "serves ${dish.servings}"

        binding.dishName.text = dish.title
        Glide.with(requireContext()).load(dish.image).into(binding.dishPic)

        binding.summaryTV.setText(HtmlCompat.fromHtml(dish.summary, 0))

        dish.extendedIngredients.forEach { ingredient ->
            val textView = TextView(requireContext())
            Log.d(TAG, "setView: ${ingredient}")
            textView.apply {
                text = "\u2022 ${ingredient}"
                textSize = 18F
                setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                val typeFace : Typeface = resources.getFont(R.font.acuminregular)
                typeface = typeFace
            }
            binding.ingredientLL.addView(textView)
        }
        binding.instructionTV.setText(HtmlCompat.fromHtml(dish.instructions,0))
    }
}