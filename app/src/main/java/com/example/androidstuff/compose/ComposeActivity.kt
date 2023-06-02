package com.example.androidstuff.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.example.androidstuff.ui.theme.AndroidStuffTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStuffTheme {
                RestaurantsApp()
            }
        }
    }
}

@Composable
fun RestaurantsApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "restaurants"
    ) {
        composable(route = "restaurants") {
            RestaurantsScreen {
                navController.navigate("restaurants/$it")
            }
        }

        composable(route = "restaurants/{restaurant_id}", arguments = listOf(navArgument("restaurant_id") {
            type = NavType.IntType
        })) {
            RestaurantDetailsScreen()
        }
    }
}

@Composable
fun FriendlyMessage(message: String) {
    Text(text = message, color = Color.Blue)
}

@Preview
@Composable
fun ClickableButton() {
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Blue,
            contentColor = Color.Red
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = "Press me")
    }
}

@Preview
@Composable
fun ShowEditText() {
    val input = remember {
        mutableStateOf("")
    }

    TextField(value = input.value, onValueChange = { newValue ->
        input.value = newValue
    },
        label = { Text("Input something here") })
}

@Preview
@Composable
fun ShowBox() {
    Box(
        modifier =
        Modifier
            .size(120.dp)
            .background(Color.Green)
            .padding(16.dp)
            .clip(RoundedCornerShape(size = 20.dp))
            .background(Color.Red)
            .padding(10.dp)
            .clip(RoundedCornerShape(size = 30.dp))
            .background(Color.Black)
    )
}