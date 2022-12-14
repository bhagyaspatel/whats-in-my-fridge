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
import com.bhagyapatel.project.ResponseDataClasses.ResponseCollectionRecipeData
import com.bhagyapatel.project.ResponseDataClasses.ResponseRecipe
import com.bumptech.glide.Glide

class RandomRecipeAdapter (val context : Context, val list : List<ResponseRecipe>, val listener : (ResponseRecipe) -> Unit)
    : RecyclerView.Adapter<RandomRecipeAdapter.ViewHolder>() {

    val TAG = "random_recipe_adapter"

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val dishImg = itemView.findViewById<ImageView>(R.id.dishPic)
        val dishName = itemView.findViewById<TextView>(R.id.dishName)
        val likes = itemView.findViewById<TextView>(R.id.likeCount)

        init{
            itemView.setOnClickListener {
                Log.d(TAG, "ItemView Clicked ${list[adapterPosition].id}")
                val item = list[adapterPosition]
                Log.d(TAG, "clicked extended: ${item.extendedIngredients}")
                listener.invoke(item)
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