package com.example.recipesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.ItemMethodStepBinding

class MethodAdapter(
    private val dataSet: List<String>
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMethodStepBinding = ItemMethodStepBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_method_step, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvMethodStepText.text = "${position + 1}. ${dataSet[position]}"

        val resources = holder.itemView.resources
        val topPadding = if (position == 0) {
            resources.getDimensionPixelSize(R.dimen.spacing_12)
        } else {
            resources.getDimensionPixelSize(R.dimen.spacing_8)
        }
        val bottomPadding = if (position == dataSet.lastIndex) {
            resources.getDimensionPixelSize(R.dimen.spacing_12)
        } else {
            resources.getDimensionPixelSize(R.dimen.spacing_8)
        }

        holder.itemView.setPadding(
            holder.itemView.paddingLeft,
            topPadding,
            holder.itemView.paddingRight,
            bottomPadding
        )
    }

    override fun getItemCount(): Int = dataSet.size
}
