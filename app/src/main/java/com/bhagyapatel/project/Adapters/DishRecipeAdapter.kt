package com.bhagyapatel.project.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhagyapatel.project.DataClasses.RecipeItem
import com.bhagyapatel.project.R
import com.bumptech.glide.Glide

class DishRecipeAdapter (val context : Context, val list : List<RecipeItem>) : RecyclerView.Adapter<DishRecipeAdapter.viewHoler>() {
    inner class viewHoler (itemView : View): RecyclerView.ViewHolder(itemView) {
        val dishPic = itemView.findViewById<ImageView>(R.id.dishPic)
        val dishName = itemView.findViewById<TextView>(R.id.dishName)
        val likes = itemView.findViewById<TextView>(R.id.likeCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHoler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_view, parent, false)
        return viewHoler(view)
    }

    override fun onBindViewHolder(holder: viewHoler, position: Int) {
        val currentItem = list[position]
        holder.dishName.text = currentItem.title
        holder.likes.text = currentItem.likes.toString()
        Glide.with(context).load(currentItem.image).into(holder.dishPic)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}