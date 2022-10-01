package com.bhagyapatel.project.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bhagyapatel.project.R
import com.bhagyapatel.project.RequestDataClasses.RequestCreateRecipe
import com.bhagyapatel.project.ResponseDataClasses.ResponseRecipe
import com.bumptech.glide.Glide

class CreateRecipeAdapter (val context : Context, val list : List<RequestCreateRecipe>, val listener : (ResponseRecipe) -> Unit)
    : RecyclerView.Adapter<CreateRecipeAdapter.ViewHolder>() {

    val TAG = "create_recipe_adapter"

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishImg = itemView.findViewById<ImageView>(R.id.dishPic)
        val dishName = itemView.findViewById<TextView>(R.id.dishName)

        init {
            itemView.setOnClickListener {
                val item = list[adapterPosition]

                val ingredients : ArrayList<String> = arrayListOf(*item.ingredients.split(",").toTypedArray())

                val responseRecipe = ResponseRecipe(0,(1000..4000).random(),item.imageUrl,
                item.instructions, item.summary, item.title, item.vegan, item.vegetarian, item.readyInMinutes,
                item.servings, ingredients)
                Log.d(TAG, "responseRecipe clicked: ${responseRecipe}")
                listener.invoke(responseRecipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.created_recipe_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.dishName.text = currentItem.title
        Log.d(TAG, "onBindViewHolder: ${holder.dishName.text}, ${currentItem.title}")
//        Glide.with(context).load(currentItem.imageUrl).into(holder.dishImg)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}