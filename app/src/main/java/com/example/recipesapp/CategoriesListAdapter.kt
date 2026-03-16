package com.example.recipesapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.ItemCategoryBinding

class CategoriesListAdapter(
    private val dataSet: List<Category>
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemCategoryBinding = ItemCategoryBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]

        holder.binding.tvCategoryTitle.text = item.title
        holder.binding.tvCategoryDescription.text = item.description

        try {
            val context = holder.itemView.context
            context.assets.open(item.imageUrl).use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, item.imageUrl)
                holder.binding.ivCategory.setImageDrawable(drawable)
            }
        } catch (e: Exception) {
            Log.e("CategoriesListAdapter", "Error loading image: ${item.imageUrl}", e)
        }

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(item.id)
        }
    }

    override fun getItemCount(): Int = dataSet.size
}