package com.example.androidstuff.compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RestaurantsViewModel() : ViewModel() {
    val state = mutableStateOf(dummyRestaurants)

    fun getRestaurants() = dummyRestaurants

    fun toggleFavorite(index: Int) {
        val restaurants = state.value.toMutableList()

        val item = restaurants[index]

        restaurants[index] = item.copy(isFavorite = !item.isFavorite)

        state.value = restaurants
    }

    companion object {
        val dummyRestaurants = listOf<Restaurant>(
            Restaurant(0, "first", "first description"),
            Restaurant(1, "first", "first description"),
            Restaurant(2, "first", "first description"),
            Restaurant(3, "first", "first description"),
            Restaurant(4, "first", "first description"),
            Restaurant(5, "first", "first description"),
            Restaurant(6, "first", "first description"),
            Restaurant(7, "first", "first description"),
            Restaurant(8, "first", "first description"),
            Restaurant(9, "first", "first description"),
            Restaurant(10, "first", "first description"),
            Restaurant(11, "first", "first description"),
            Restaurant(0, "first", "first description"),
            Restaurant(1, "first", "first description"),
            Restaurant(2, "first", "first description"),
            Restaurant(3, "first", "first description"),
            Restaurant(4, "first", "first description"),
            Restaurant(5, "first", "first description"),
            Restaurant(6, "first", "first description"),
            Restaurant(7, "first", "first description"),
            Restaurant(8, "first", "first description"),
            Restaurant(9, "first", "first description"),
            Restaurant(10, "first", "first description"),
            Restaurant(11, "first", "first description"),
            )
    }
}