package com.bhagyapatel.project.Fragments

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bhagyapatel.project.Animations.MorphButton
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.DataClasses.SelectedDish
import com.bhagyapatel.project.Interface.RetrofitHelpers.NodeRetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals.NodeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.NodeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.Utils.getColorX
import com.bhagyapatel.project.databinding.FragmentSingleDishBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay

class SingleDishFragment : Fragment() {
    private val TAG = "Single_Dish_frag"
    private lateinit var binding : FragmentSingleDishBinding

    private var dish : SelectedDish? = null
    private lateinit var nodeViewModal: NodeViewModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onCreate Single dish frag")
        dish = SingleDishFragmentArgs.fromBundle(requireArguments()).dish
        Log.d(TAG, "onCreate: ${dish}, ${binding.dishName}")

        val nodeInterface = NodeRetrofitHelper.getInstance().create(NodeInterface::class.java)
        val reopsitory = NodeRepository(nodeInterface)
        nodeViewModal = ViewModelProvider(this, NodeViewModalFactory(reopsitory))
            .get(NodeViewModal::class.java)

        if (dish != null){
            setView()

            binding.morphSaveButton.setOnClickListener {
                lifecycleScope.launchWhenStarted {
                    binding.morphSaveButton.apply {
                        iconDrawable.setTint(getColorX(R.color.app_light_orange))
                        setUIState(MorphButton.UIState.Loading)
                        delay(1500)
                        setUIState(MorphButton.UIState.Button)
                    }
                }
                val map = HashMap<String, String>()
                map.put("recipeId", dish!!.recipeId.toString())
                map.put("title", dish!!.title)
                map.put("imageUrl", dish!!.image)
//                map.put ("userObjectId", )
//                map.put("uuid", "234fasf")
//                nodeViewModal.saveRecipe(map)
//                nodeViewModal.response.observe(viewLifecycleOwner){ response ->
//                    Log.d(TAG, "response on save recipe: ${response}")
//                    if (response != null){
//                        Log.d(TAG, "response is success status and message: ${response.success}; ${response.message}")
//                    }else{
//                        Log.d(TAG, "response is null")
//                    }
//                }
            }
        }
        else{
            Log.d(TAG, "onViewCreated: selected dish from prev frag is null")
        }

    }

    private fun setView() {
        Glide.with(requireContext()).load(dish!!.image).into(binding.dishPic)
        binding.dishName.text = dish!!.title

        dish!!.ingredients.forEach { ingredient ->
            val textView = TextView(requireContext())
            Log.d(TAG, "setView: ${ingredient}")
            textView.apply {
                text = "\u2022 ${ingredient}"
                textSize = 22F
                setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                val typeFace : Typeface = resources.getFont(R.font.acuminregular)
                typeface = typeFace
            }
            binding.ingredientLL.addView(textView)
        }
    }
}