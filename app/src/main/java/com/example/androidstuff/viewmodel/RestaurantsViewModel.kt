package com.example.androidstuff.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstuff.AndroidJetpackApplication
import com.example.androidstuff.compose.Restaurant
import com.example.androidstuff.koin.RestaurantRepository
import com.example.androidstuff.room.RestaurantsDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantsViewModel(
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {
    val state = mutableStateOf(emptyList<Restaurant>())
    val loadingState = mutableStateOf(false)

    private val restaurantsDao = RestaurantsDb.getDaoInstance(AndroidJetpackApplication.getAppContext())

    init {
        getRestaurants()
    }

    private fun getRestaurants() {

        loadingState.value = true

        viewModelScope.launch(Dispatchers.Main) {

            val restaurants = try {
                restaurantRepository.getRestaurants()
            } catch (e: Exception) {
                restaurantsDao.getAll()
            }

            restaurantsDao.addAll(restaurants)
            state.value = restaurants
            loadingState.value = false
        }
    }

    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch {
            val updatedRestaurants = toggleFavoriteRestaurant(id, oldValue)

            state.value = updatedRestaurants
        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restaurantRepository.getRestaurants()

        val favoriteRestaurants = restaurantsDao.getAllFavorited()

        restaurantsDao.addAll(remoteRestaurants)
//
//        restaurantsDao.updateAll(favoriteRestaurants.map {
//            restaurantsDao.getById(id = it.id)?.run {
//                this.copy(isFavorite = true)
//            }
//        })
    }

    private suspend fun toggleFavoriteRestaurant(id: Int, oldValue: Boolean) =
        withContext(Dispatchers.IO) {
            restaurantsDao.getById(id).let {
                restaurantsDao.update(it.copy(isFavorite = !oldValue))
            }
            restaurantsDao.getAll()
        }
}