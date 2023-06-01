package com.example.androidstuff.koin

import com.example.androidstuff.compose.Restaurant

interface RestaurantRepository {
    suspend fun getRestaurants(): List<Restaurant>

    suspend fun getRestaurant(id:Int): Map<String, Restaurant>
}