package com.example.recipesapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment() {

    companion object {
        const val ARG_RECIPE = "ARG_RECIPE"
    }

    private var recipe: Recipe? = null

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding ?: error("FragmentRecipeBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_RECIPE)
        }

        initUI()
        initRecycler()
    }

    private fun initUI() {
        val recipeData = recipe ?: return
        binding.tvHeaderTitle.text = recipeData.title

        try {
            requireContext().assets.open(recipeData.imageUrl).use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, recipeData.imageUrl)
                binding.ivHeader.setImageDrawable(drawable)
            }
        } catch (e: Exception) {
            Log.e("RecipeFragment", "Error loading recipe image: ${recipeData.imageUrl}", e)
        }
    }

    private fun initRecycler() {
        val recipeData = recipe ?: return

        binding.rvIngredients.adapter = IngredientsAdapter(recipeData.ingredients)
        binding.rvMethod.adapter = MethodAdapter(recipeData.method)

        addDivider(binding.rvIngredients)
        addDivider(binding.rvMethod)
    }

    private fun addDivider(recyclerView: RecyclerView) {
        val divider = MaterialDividerItemDecoration(
            requireContext(),
            MaterialDividerItemDecoration.VERTICAL
        ).apply {
            dividerColor = ContextCompat.getColor(requireContext(), R.color.text_secondary)
            dividerThickness = resources.getDimensionPixelSize(R.dimen.spacing_1)
            dividerInsetStart = resources.getDimensionPixelSize(R.dimen.spacing_12)
            dividerInsetEnd = resources.getDimensionPixelSize(R.dimen.spacing_12)
            isLastItemDecorated = false
        }

        recyclerView.addItemDecoration(divider)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
