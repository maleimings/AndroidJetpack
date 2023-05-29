package com.example.androidstuff.compose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.androidstuff.net.RestaurantsApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RestaurantsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    val state = mutableStateOf(emptyList<Restaurant>())

    private lateinit var  getRestaurantCall: Call<List<Restaurant>>

    override fun onCleared() {
        super.onCleared()
        getRestaurantCall.cancel()
    }

    private fun getRestaurants() {

        getRestaurantCall = restaurantApi.getRestaurants()

        getRestaurantCall
            .enqueue(object : Callback<List<Restaurant>> {
                override fun onResponse(
                    call: Call<List<Restaurant>>,
                    response: Response<List<Restaurant>>
                ) {
                    response.body()
                        ?.let {
                                restaurants ->
                            state.value = restaurants.restoreSelections()
                        }
                }

                override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private val restaurantApi: RestaurantsApiService

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://myfirstfirebase-d3179-default-rtdb.firebaseio.com/")
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