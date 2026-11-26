package com.example.recipesapp

import android.content.Context

private const val PREFERENCES_NAME = "recipe_preferences"
private const val FAVORITES_KEY = "favorite_recipe_ids"

private val Context.favoritePreferences
    get() = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

internal fun Context.getFavorites(): MutableSet<String> {
    return favoritePreferences.getStringSet(FAVORITES_KEY, null).orEmpty().toMutableSet()
}

internal fun Context.saveFavorites(favorites: Set<String>) {
    favoritePreferences.edit().putStringSet(FAVORITES_KEY, favorites).apply()
}
