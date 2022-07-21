package co.composables.navigationtutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.composables.navigationtutorial.ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                TransparentSystemBars()

                Box(Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(onDetailsClick = { id ->
                                navController.navigate("details/id=$id?name=hi")
                            }, onAboutClick = {
                                navController.navigate("about")
                            })
                        }
                        composable("about") {
                            AboutScreen(onNavigateUp = {
                                navController.popBackStack()
                            })
                        }
                        composable(
                            "details/id={id}?name={name}",
                            arguments = listOf(navArgument("id") {
                                type = NavType.LongType
                            }, navArgument("name") {
                                type = NavType.StringType
                                nullable = true
                            }),
                        ) { backStackEntry ->
                            val arguments = requireNotNull(backStackEntry.arguments)
                            val id = arguments.getLong("id")
                            val name = arguments.getString("name")
                            DetailsScreen(id, name, onNavigateUp = {
                                navController.popBackStack()
                            })
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TransparentSystemBars() {
        val statusBarColor = MaterialTheme.colors.background
        SideEffect {
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = statusBarColor.toArgb()
        }
    }

    @Composable
    fun DetailsScreen(id: Long, name: String?, onNavigateUp: () -> Unit) {
        val article = allArticles.first { it.id == id }
        Scaffold { padding ->
            Column(Modifier.padding(padding)) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp)) {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Go back")
                    }
                }
                Image(painterResource(article.thumbnail),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop)
                Spacer(Modifier.height(20.dp))
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5,
                        text = article.title)
                    Spacer(Modifier.height(20.dp))
                    Text(modifier = Modifier.fillMaxSize(),
                        style = MaterialTheme.typography.body1,
                        text = article.body)
                }
            }
        }
    }

    @Composable
    fun HomeScreen(
        onDetailsClick: (id: Long) -> Unit,
        onAboutClick: () -> Unit,
    ) {
        Scaffold { padding ->
            LazyColumn(contentPadding = padding) {
                item {
                    HomeAppBar(onAboutClick)
                }
                item {
                    Spacer(Modifier.height(30.dp))
                }
                items(allArticles) { item ->
                    ArticleCard(item, onClick = {
                        onDetailsClick(item.id)
                    })
                }
            }
        }
    }

    @Composable
    private fun HomeAppBar(onAboutClick: () -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Text("Latest Health articles", style = MaterialTheme.typography.h6)
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onAboutClick) {
                Text("About")
            }
        }
    }

    @Composable
    fun ArticleCard(item: Content, onClick: () -> Unit) {
        Card(modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth(),
            onClick = onClick) {
            Column {
                Image(painterResource(item.thumbnail),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop)
                Column(Modifier
                    .fillMaxWidth()
                    .padding(20.dp)) {
                    Text(item.title)
                    Spacer(Modifier.height(8.dp))
                    Text(item.body,
                        maxLines = 1,
                        style = MaterialTheme.typography.body2)
                }
            }

        }
    }

    @Composable
    fun AboutScreen(onNavigateUp: () -> Unit) {
        Scaffold { padding ->
            Column(Modifier.padding(padding)) {
                AppBar(title = "About", onNavigateUp)
                Spacer(Modifier.height(20.dp))
                Column(Modifier.padding(16.dp)) {
                    Text("A sample app that demonstrates the basics of Jetpack Compose Navigation.")
                    Spacer(Modifier.height(20.dp))
                    val na = LocalUriHandler.current
                    Button(onClick = { na.openUri("https://www.composables.co/blog/navigation-tutorial") }) {
                        Text("Read the full article")
                    }
                }
            }
        }
    }

    @Composable
    fun AppBar(title: String, onNavigateUp: () -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)) {
            IconButton(onClick = onNavigateUp) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Go back")
            }
            Spacer(Modifier.width(10.dp))
            Text(title, style = MaterialTheme.typography.h6)
        }
    }
}
