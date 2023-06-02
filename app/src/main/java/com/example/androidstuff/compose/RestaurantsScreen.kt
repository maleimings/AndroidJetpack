package com.example.androidstuff.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.example.androidstuff.viewmodel.RestaurantsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RestaurantsScreen(restaurantsViewModel: RestaurantsViewModel = koinViewModel()) {

    Column {
        TextInput()

        restaurantsViewModel.loadingState.value.let {
            LoadingIndicator(it)
        }

        LazyColumn {

            restaurantsViewModel.state.value.let {
                items(it.size) { index ->
                    val item = it[index]
                    RestaurantItem(item, index) {
                        restaurantsViewModel.toggleFavorite(index)
                    }
                }
            }
        }


    }
}

@Composable
fun LoadingIndicator(isShowLoading: Boolean) {
    if (isShowLoading) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun TextInput() {
    val textState = remember {
        mutableStateOf("")
    }

    TextField(modifier = Modifier.fillMaxWidth(),
        value = textState.value,
        onValueChange = {
                newValue -> textState.value = newValue
        },
        label = {Text("Input text")}
    )
}

@Composable
fun RestaurantItem(item: Restaurant, index: Int, onClick: (id: Int) -> Unit) {

    val favoriteIcon = if (item.isFavorite) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp,
        modifier = Modifier.padding(8.dp),
        backgroundColor = Color.LightGray,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp),
        ) {
            RestaurantIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            RestaurantDetail(Modifier.weight(0.7f), item)
            RestaurantFavorite(icon = favoriteIcon, modifier = Modifier.weight(0.15f)) {
                onClick(item.id)
            }
        }
    }
}

@Composable
fun RestaurantIcon(icon: ImageVector, modifier: Modifier) {
    Image(
        painter = rememberVectorPainter(image = icon),
        contentDescription = "Icon",
        modifier = modifier.padding(8.dp),
    )
}

@Composable
fun RestaurantDetail(modifier: Modifier, item: Restaurant) {
    Column(modifier = modifier) {
        Text(text = item.title, style = MaterialTheme.typography.h6)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = "${item.description}", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun RestaurantFavorite(icon: ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Image(
        painter = rememberVectorPainter(image = icon),
        contentDescription = "Favorite restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() }
    )
}