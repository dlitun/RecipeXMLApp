package com.example.recipesapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipesapp.databinding.FragmentRecipesListBinding

class RecipesListFragment : Fragment() {

    companion object {
        const val ARG_CATEGORY_ID = "ARG_CATEGORY_ID"
        const val ARG_CATEGORY_NAME = "ARG_CATEGORY_NAME"
        const val ARG_CATEGORY_IMAGE_URL = "ARG_CATEGORY_IMAGE_URL"
    }

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    private var _binding: FragmentRecipesListBinding? = null
    private val binding get() = _binding ?: error("FragmentRecipesListBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = arguments?.getInt(ARG_CATEGORY_ID)
        categoryName = arguments?.getString(ARG_CATEGORY_NAME)
        categoryImageUrl = arguments?.getString(ARG_CATEGORY_IMAGE_URL)

        initHeader()
        initRecycler()
    }

    private fun initHeader() {
        binding.tvHeaderTitle.text = categoryName ?: getString(R.string.title_recipes)

        val imageName = categoryImageUrl ?: return
        try {
            requireContext().assets.open(imageName).use { inputStream ->
                val drawable = Drawable.createFromStream(inputStream, imageName)
                binding.ivHeader.setImageDrawable(drawable)
            }
        } catch (e: Exception) {
            Log.e("RecipesListFragment", "Error loading header image: $imageName", e)
        }
    }

    private fun initRecycler() {
        val recipes = STUB.getRecipesByCategoryId(categoryId ?: -1)
        val adapter = RecipesListAdapter(recipes)

        adapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })

        binding.rvRecipes.adapter = adapter
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        Log.d("RecipesListFragment", "Open recipeId=$recipeId")
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}