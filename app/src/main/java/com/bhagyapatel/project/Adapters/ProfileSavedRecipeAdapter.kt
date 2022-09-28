package com.bhagyapatel.project.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhagyapatel.project.DataClasses.SelectedDish
import com.bhagyapatel.project.R
import com.bumptech.glide.Glide

class ProfileSavedRecipeAdapter (val context: Context, val list : List<SelectedDish>, val listener : (SelectedDish)-> Unit)
    : RecyclerView.Adapter<ProfileSavedRecipeAdapter.ViewHolder>(){

    val TAG = "profile_save_recipe_adapter"

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val dishPic = itemView.findViewById<ImageView>(R.id.dishPic)
        val dishName = itemView.findViewById<TextView>(R.id.dishName)
        val likes = itemView.findViewById<TextView>(R.id.likeCount)

        init{
            itemView.setOnClickListener {
                Log.d(TAG, "ItemView Clicked ${list[adapterPosition].recipeId}")
                val item = list[adapterPosition]

                val selectedDish = SelectedDish(item.recipeId, item.title, item.image, item.ingredients)
                listener.invoke(selectedDish)
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
        holder.likes.text = currentItem.likes
        Glide.with(context).load(currentItem.image).into(holder.dishPic)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}