package com.example.androidstuff.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstuff.BuildConfig
import com.example.androidstuff.compose.Restaurant
import com.example.androidstuff.koin.RestaurantRepository
import com.example.androidstuff.net.RestaurantsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantDetailsViewModel(private val restaurantRepository: RestaurantRepository): ViewModel() {

    val state = mutableStateOf<Restaurant?>(null)

    init {
        viewModelScope.launch {
            val restaurant = getRemoteRestaurant(2)

            state.value = restaurant
        }
    }

    private suspend fun getRemoteRestaurant(id: Int) : Restaurant {
        return withContext(Dispatchers.IO) {
            val restaurantData = restaurantRepository.getRestaurant(id)

            return@withContext restaurantData.values.first()
        }
    }
}