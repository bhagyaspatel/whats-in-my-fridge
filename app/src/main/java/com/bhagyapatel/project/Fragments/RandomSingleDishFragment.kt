package com.bhagyapatel.project.Fragments

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.bhagyapatel.project.Animations.MorphButton
import com.bhagyapatel.project.DataClasses.Recipe
import com.bhagyapatel.project.R
import com.bhagyapatel.project.Utils.getColorX
import com.bhagyapatel.project.databinding.FragmentNewRecipeBinding
import com.bhagyapatel.project.databinding.FragmentRandomSingleDishBinding
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.delay

class RandomSingleDishFragment : Fragment() {

    private val TAG = "random_single_dish"

    private lateinit var binding : FragmentRandomSingleDishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomSingleDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dish = RandomSingleDishFragmentArgs.fromBundle(requireArguments()).dish
        Log.d(TAG, "onCreate single dish frag ${dish}")

//        var isShow = true
//        var scrollRange = -1
//
//        binding.appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
//            if (scrollRange == -1){
//                scrollRange = barLayout?.totalScrollRange!!
//            }
//            if (scrollRange + verticalOffset == 0){
//                binding.collapsingToolbar.title = "Title Collapse"
//                isShow = true
//            } else if (isShow){
//                binding.collapsingToolbar.title = " " //careful there should a space between double quote otherwise it wont work
//                isShow = false
//            }
//        })

        setView(dish)

        binding.morphSaveButton.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                binding.morphSaveButton.apply {
                    iconDrawable.setTint(getColorX(R.color.app_light_orange))
                    setUIState(MorphButton.UIState.Loading)
                    delay(1500)
                    setUIState(MorphButton.UIState.Button)
                }
            }
        }
    }

    private fun setView(dish: Recipe) {
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
                text = "\u2022 ${ingredient.original}"
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