package com.example.androidstuff.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidstuff.BuildConfig
import com.example.androidstuff.koin.RestaurantRepository
import com.example.androidstuff.net.RestaurantsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantDetailsViewModel(private val restaurantRepository: RestaurantRepository): ViewModel() {

}