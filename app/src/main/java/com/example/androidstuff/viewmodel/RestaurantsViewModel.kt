package com.example.androidstuff.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstuff.compose.Restaurant
import com.example.androidstuff.koin.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantsViewModel(
    private val stateHandle: SavedStateHandle,
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {
    val state = mutableStateOf(emptyList<Restaurant>())
    val loadingState = mutableStateOf(false)

    init {
        getRestaurants()
    }

    private fun getRestaurants() {

        loadingState.value = true

        viewModelScope.launch(Dispatchers.Main) {
            val restaurants = restaurantRepository.getRestaurants()

            state.value = restaurants.restoreSelections()
            loadingState.value = false
        }
    }

    fun toggleFavorite(index: Int) {
        val restaurants = state.value.toMutableList()

        val item = restaurants[index]

        restaurants[index] = item.copy(isFavorite = !item.isFavorite)
        storeSelection(restaurants[index])
        state.value = restaurants
    }

    private fun storeSelection(item: Restaurant) {
        val savedToggled = stateHandle
            .get<List<Int>?>(FAVORIATES)
            .orEmpty()
            .toMutableList()

        if (item.isFavorite) {
            savedToggled.add(item.id)
        } else {
            savedToggled.remove(item.id)
        }

        stateHandle[FAVORIATES] = savedToggled
    }

    private fun List<Restaurant>.restoreSelections(): List<Restaurant> {
        stateHandle.get<List<Int>?>(FAVORIATES)?.let { selectedIds ->
            val restaurantMap = this.associateBy { it.id }
            selectedIds.forEach { id ->
                restaurantMap[id]?.isFavorite = true
            }

            return restaurantMap.values.toList()
        }

        return this
    }

    companion object {
        const val FAVORIATES = "favoriates"
    }
}