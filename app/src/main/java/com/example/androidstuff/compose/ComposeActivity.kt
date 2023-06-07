package com.example.androidstuff.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.androidstuff.net.Repository
import com.example.androidstuff.ui.theme.AndroidStuffTheme
import com.example.androidstuff.viewmodel.RepositoriesViewModel
import org.koin.core.context.GlobalContext.get
import org.koin.java.KoinJavaComponent.inject

class ComposeActivity : ComponentActivity() {

    private val repositoriesViewModel: RepositoriesViewModel by inject<RepositoriesViewModel>(
        RepositoriesViewModel::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStuffTheme {
                val reposFlow = repositoriesViewModel.repositories
                val lazyRepoItems: LazyPagingItems<Repository> = reposFlow.collectAsLazyPagingItems()
                RepositoriesApp(lazyRepoItems)
            }
        }
    }
}

@Composable
fun RepositoriesApp(repos: LazyPagingItems<Repository>) {
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)) {
        itemsIndexed(repos) { index, item ->
            item?.let {
                RepositoryItem(index, it)
            }
        }
    }
}

@Composable
fun RepositoryItem(index: Int, item: Repository) {
    Card(
        elevation = 4.dp, modifier = Modifier
            .padding(8.dp)
            .height(120.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = index.toString(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .weight(0.2f)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(1.dp)
            ) {
                Text(text = item.name ?: " ", style = MaterialTheme.typography.h6)
                Text(
                    text = item.description ?: "", style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
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

        composable(
            route = "restaurant/{restaurant_id}",
            arguments = listOf(navArgument("restaurant_id") {
                type = NavType.IntType
            }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "restaurant/{restaurant_id}"
            })
        ) {
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