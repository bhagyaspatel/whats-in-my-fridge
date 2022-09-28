package com.bhagyapatel.project.Adapters

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhagyapatel.project.DataClasses.RecipeItem
import com.bhagyapatel.project.DataClasses.SelectedDish
import com.bhagyapatel.project.R
import com.bumptech.glide.Glide

class DishRecipeAdapter (val context : Context, val list : List<RecipeItem>, val listener : (SelectedDish) -> Unit) : RecyclerView.Adapter<DishRecipeAdapter.viewHolder>() {

    val TAG = "dish_adapter"

    inner class viewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val dishPic = itemView.findViewById<ImageView>(R.id.dishPic)
        val dishName = itemView.findViewById<TextView>(R.id.dishName)
        val likes = itemView.findViewById<TextView>(R.id.likeCount)

        init{
            itemView.setOnClickListener {
                Log.d(TAG, "ItemView Clicked ${list[adapterPosition].id}")
                val item = list[adapterPosition]
                val list : MutableList<String> = ArrayList()

                Log.d(TAG, "missed and used counts: ${item.missedIngredientCount} ${item.usedIngredientCount}")

                if (item.missedIngredientCount != null){
                    for (i in 0 until item.missedIngredientCount){
                        list.add(item.missedIngredients!![i].original)
                        Log.d(TAG, "missed " + item.missedIngredients[i].original)
                    }
                    for (i in 0 until item.usedIngredientCount!!){
                        list.add(item.usedIngredients!![i].original)
                        Log.d(TAG, "used " + item.usedIngredients[i].original)
                    }
                    val selectedDish = SelectedDish(item.id,item.title, item.image, list)
                    Log.d(TAG, "list of ingredients : ${list} ")
                    listener.invoke(selectedDish)
                }else{
                    Log.d(TAG, "items.usedIngredients: ${item.usedIngredients} ")
                    item.usedIngredients!!.forEach {
                        Log.d(TAG, "inside for each: ${it} ")
                        list.add(it.original)
                    }
                    Log.d(TAG, "list of string is : ${list} ")
                    val selectedDish = SelectedDish(item.id,item.title, item.image, list)
                    listener.invoke(selectedDish)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_view, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = list[position]
        holder.dishName.text = currentItem.title
        holder.likes.text = currentItem.likes.toString()
        Glide.with(context).load(currentItem.image).into(holder.dishPic)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}