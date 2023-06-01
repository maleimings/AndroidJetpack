package com.example.androidstuff.compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstuff.BuildConfig
import com.example.androidstuff.net.RestaurantsApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    val state = mutableStateOf(emptyList<Restaurant>())
    val loadingState = mutableStateOf(false)

    private val errorHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()}

    private fun getRestaurants() {

        loadingState.value = true

        viewModelScope.launch(Dispatchers.Main) {
            val restaurants = getRemoteRestaurants()

            state.value = restaurants.restoreSelections()
            loadingState.value = false
        }
    }

    private suspend fun getRemoteRestaurants() : List<Restaurant> {
        return withContext(Dispatchers.IO + errorHandler) {
            restaurantApi.getRestaurants()
        }
    }

    private val restaurantApi: RestaurantsApiService

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BaseUrl)
            .build()

        restaurantApi = retrofit.create(RestaurantsApiService::class.java)

        getRestaurants()
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

    private fun List<Restaurant>.restoreSelections() : List<Restaurant> {
        stateHandle.get<List<Int>?>(FAVORIATES)?.let {
            selectedIds ->
            val restaurantMap = this.associateBy { it.id }
            selectedIds.forEach {
                id ->
                restaurantMap[id]?.isFavorite = true
            }

            return restaurantMap.values.toList()
        }

        return this
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

        const val FAVORIATES = "favoriates"
    }
}