package com.example.androidstuff.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidstuff.compose.Restaurant

@Dao
interface RestaurantsDao {
    @Query("SELECT * FROM restaurants")
    suspend fun getAll(): List<Restaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurants: List<Restaurant>)


    @Update(entity = Restaurant::class)
    suspend fun update(restaurant: Restaurant)

    @Query("SELECT * FROM restaurants WHERE r_id=:id")
    suspend fun getById(id: Int): Restaurant

    @Query("SELECT * FROM restaurants WHERE is_favorite = 1")
    suspend fun getAllFavorited() : List<Restaurant>
}