package es.usj.groupapp.marcos.racingappmarcos_radeluis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.view.NewsFormScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.list.view.NewsScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.view.RacerFormScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.form.view.RaceFormScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.list.view.RacesScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.settings.view.SettingScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.settings.viewmodel.SettingViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.settings.viewmodel.SettingViewModelFactory
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamFormScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.view.TrackFormScreen

class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val settingFactory = SettingViewModelFactory(this)
            val settingViewModel = settingFactory.create(SettingViewModel::class.java)

            var isDarkMode = remember {settingViewModel.isDarkMode}

            MaterialTheme(
                colorScheme = if (isDarkMode.value) darkColorScheme() else lightColorScheme()
            ) {
                Scaffold(
                    bottomBar = { BottomNavigation(navController) },

                    ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                HomeScreen(navController = navController)
                            }

                            composable("news") {
                                NewsScreen(onAddNewsClick = {
                                    navController.navigate("news_form")
                                }, navController = navController)
                            }

                            composable("news_form") { backStackEntry ->
                                NewsFormScreen(navController = navController, newsId = null)
                            }

                             composable(
                                route = "news_form/{newsId}",
                                arguments = listOf(navArgument("newsId") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val newsId = backStackEntry.arguments?.getString("newsId") ?: ""
                                NewsFormScreen(navController = navController, newsId = newsId)
                            }

                            composable("races") {
                                RacesScreen(navController = navController)
                            }

                            composable("race_form") {
                                RaceFormScreen(navController = navController, raceId = null)
                            }

                            composable(
                                route = "race_form/{raceId}",
                                arguments = listOf(navArgument("raceId") { type = NavType.LongType })
                            ) { backStackEntry ->
                                val raceId = backStackEntry.arguments?.getLong("raceId") ?: 0L
                                RaceFormScreen(navController = navController, raceId = raceId)
                            }

                            composable("settings") {
                                SettingScreen(settingViewModel)
                            }

                            composable("team_form") {
                                TeamFormScreen(navController = navController, teamId = null)
                            }

                            composable(
                                route = "team_form/{teamId}",
                                arguments = listOf(navArgument("teamId") { type = NavType.LongType })
                            ) { backStackEntry ->
                                val teamId = backStackEntry.arguments?.getLong("teamId") ?: 0L
                                TeamFormScreen(navController = navController, teamId = teamId)
                            }

                            composable("racer_form") { backStackEntry ->
                                RacerFormScreen(navController = navController, racerId = null)
                            }

                            composable(
                                route = "racer_form/{racerId}",
                                arguments = listOf(navArgument("racerId") { type = NavType.LongType })
                            ) { backStackEntry ->
                                val racerId = backStackEntry.arguments?.getLong("racerId") ?: 0L
                                RacerFormScreen(navController = navController, racerId = racerId)
                            }

                            composable("track_form") {
                                TrackFormScreen(navController = navController, trackId = null)
                            }

                            composable(
                                route = "track_form/{trackId}",
                                arguments = listOf(navArgument("trackId") { type = NavType.LongType })
                            ) { backStackEntry ->
                                val trackId = backStackEntry.arguments?.getLong("trackId") ?: 0L
                                TrackFormScreen(navController = navController, trackId = trackId)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("home", Icons.Default.Home, "Home"),
        BottomNavItem("races", ImageVector.vectorResource(R.drawable.baseline_directions_car_24), "Races"),
        BottomNavItem("news", Icons.Rounded.Email, "News"),
        BottomNavItem("settings", Icons.Default.Settings, "Settings")
    )
    var selectedItem =  remember { mutableStateOf(items[0]) }

    BottomAppBar{
        items.forEach {
            NavigationBarItem(
                icon = {  Icon(it.icon, contentDescription = it.label) },
                label = { Text(it.label) },
                selected = selectedItem.value == it,
                onClick = {
                    selectedItem.value = it
                    navController.navigate(it.route)
                }
            )
        }
    }
}


data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)