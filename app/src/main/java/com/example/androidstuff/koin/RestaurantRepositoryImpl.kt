package com.example.androidstuff.koin

import com.example.androidstuff.BuildConfig
import com.example.androidstuff.compose.Restaurant
import com.example.androidstuff.net.RestaurantsApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantRepositoryImpl : RestaurantRepository {
    private val restaurantApi: RestaurantsApiService
    private val errorHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()}


    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BaseUrl)
            .build()

        restaurantApi = retrofit.create(RestaurantsApiService::class.java)
    }

    override suspend fun getRestaurants(): List<Restaurant> {
        return withContext(Dispatchers.IO + errorHandler) {
            restaurantApi.getRestaurants()
        }
    }

    override suspend fun getRestaurant(id: Int): Map<String, Restaurant> {
        return withContext(Dispatchers.IO + errorHandler) {
            restaurantApi.getRestaurant(id)
        }
    }
}