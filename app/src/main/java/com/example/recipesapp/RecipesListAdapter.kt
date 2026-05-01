package com.example.recipesapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.ItemRecipeBinding

class RecipesListAdapter(
	private val dataSet: List<Recipe>
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

	interface OnItemClickListener {
		fun onItemClick(recipeId: Int)
	}

	private var itemClickListener: OnItemClickListener? = null

	fun setOnItemClickListener(listener: OnItemClickListener) {
		itemClickListener = listener
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val binding: ItemRecipeBinding = ItemRecipeBinding.bind(itemView)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_recipe, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = dataSet[position]

		holder.binding.tvRecipeTitle.text = item.title

		try {
			val context = holder.itemView.context
			context.assets.open(item.imageUrl).use { inputStream ->
				val drawable = Drawable.createFromStream(inputStream, item.imageUrl)
				holder.binding.ivRecipe.setImageDrawable(drawable)
			}
		} catch (e: Exception) {
			Log.e("RecipesListAdapter", "Error loading image: ${item.imageUrl}", e)
		}

		holder.itemView.setOnClickListener {
			itemClickListener?.onItemClick(item.id)
		}
	}

	override fun getItemCount(): Int = dataSet.size
}

