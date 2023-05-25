package com.example.androidstuff.compose

data class Restaurant(
    val id: Int,
    val title: String,
    val description: String,
    var isFavorite: Boolean = false,
)
