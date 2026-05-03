package com.example.recipesapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipesapp.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    companion object {
        const val ARG_RECIPE = "ARG_RECIPE"
    }

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

        val recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_RECIPE) as? Recipe
        }

        if (recipe != null) {
            binding.tvRecipeTitle.text = recipe.title
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

