package com.example.recipesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace

internal fun Fragment.openRecipeByRecipeId(recipeId: Int) {
    val recipe = STUB.getRecipeById(recipeId) ?: return
    val bundle = Bundle().apply {
        putParcelable(RecipeFragment.ARG_RECIPE, recipe)
    }

    parentFragmentManager.commit {
        setReorderingAllowed(true)
        replace<RecipeFragment>(R.id.mainContainer, args = bundle)
        addToBackStack(null)
    }
}
