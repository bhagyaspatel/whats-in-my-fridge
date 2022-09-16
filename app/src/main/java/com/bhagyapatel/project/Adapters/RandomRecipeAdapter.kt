package com.bhagyapatel.project.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhagyapatel.project.DataClasses.Recipe
import com.bhagyapatel.project.R
import com.bumptech.glide.Glide

class RandomRecipeAdapter (val context : Context, val list : List<Recipe>, val listener : (Recipe) -> Unit)
    : RecyclerView.Adapter<RandomRecipeAdapter.ViewHolder>() {

    val TAG = "Random_recipe_adapter"

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val dishImg = itemView.findViewById<ImageView>(R.id.randomDishPic)
        val dishName = itemView.findViewById<TextView>(R.id.randomDishName)
        val likes = itemView.findViewById<TextView>(R.id.randomLikes)

        init{
            itemView.setOnClickListener {
                Log.d(TAG, "ItemView Clicked ${list[adapterPosition].id}")
                val item = list[adapterPosition]
                val list : MutableList<String> = ArrayList()
                var recipe : Recipe
                item.apply {
                    recipe = Recipe(aggregateLikes,analyzedInstructions, id, image, instructions,
                        spoonacularSourceUrl, summary, title, vegan, vegetarian, readyInMinutes,
                        servings, extendedIngredients)
                }
                listener.invoke(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.random_recipe_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.dishName.text = currentItem.title
        holder.likes.text = currentItem.aggregateLikes.toString()
        Glide.with(context).load(currentItem.image).into(holder.dishImg)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}