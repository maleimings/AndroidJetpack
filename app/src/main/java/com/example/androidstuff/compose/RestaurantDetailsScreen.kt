package com.example.androidstuff.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidstuff.viewmodel.RestaurantDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RestaurantDetailsScreen(restaurantDetailsViewModel: RestaurantDetailsViewModel = koinViewModel()) {
    restaurantDetailsViewModel.state.value?.let {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            RestaurantIcon(
                icon = Icons.Filled.Place,
                modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
            )
            RestaurantDetail(Modifier.padding(bottom = 32.dp), it)
            Text(text = "More")
        }
    }
}