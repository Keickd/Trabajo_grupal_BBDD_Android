package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database.RacingAppDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModelFactory
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.view.NewsScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.viewmodel.NewsViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.viewmodel.NewsViewModelFactory
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.view.RacerFormScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.viewmodel.RacerFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.viewmodel.RacerFormViewModelFactory
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.view.RacesScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.settings.view.SettingScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.settings.viewmodel.SettingViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.settings.viewmodel.SettingViewModelFactory
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamFormScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel.TeamFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel.TeamFormViewModelFactory
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.view.TrackFormScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.viewmodel.TrackFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.viewmodel.TrackFormViewModelFactory

class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val database = RacingAppDatabase.provideDatabase(applicationContext)
            val homeFactory = HomeViewModelFactory(this, database)
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
                                val homeViewModel = homeFactory.create(HomeViewModel::class.java)
                                HomeScreen(viewModel = homeViewModel, navController = navController)
                            }

                            composable("news") {
                                val newsFactory = NewsViewModelFactory()
                                val newsViewModel = newsFactory.create(NewsViewModel::class.java)

                                NewsScreen(newsViewModel, navController)
                            }

                            composable("races") {

                                RacesScreen()
                            }

                            composable("settings") {
                                SettingScreen(settingViewModel)
                            }

                            composable("team_form") { backStackEntry ->
                                val teamFactory = TeamFormViewModelFactory(
                                    context = this@MainActivity,
                                    database = database,
                                    savedStateHandle = backStackEntry.savedStateHandle
                                )
                                val teamViewModel = teamFactory.create(TeamFormViewModel::class.java)

                                TeamFormScreen(viewModel = teamViewModel, navController = navController)
                            }

                            composable("racer_form") { backStackEntry ->
                                val racerFactory = RacerFormViewModelFactory(
                                    context = this@MainActivity,
                                    database = database,
                                    savedStateHandle = backStackEntry.savedStateHandle
                                )
                                val racerViewModel = racerFactory.create(RacerFormViewModel::class.java)

                                RacerFormScreen(
                                    viewModel = racerViewModel,
                                    navController = navController
                                )
                            }

                            composable("track_form") { backStackEntry ->
                                val trackFactory = TrackFormViewModelFactory(
                                    context = this@MainActivity,
                                    database = database,
                                    savedStateHandle = backStackEntry.savedStateHandle
                                )
                                val trackViewModel = trackFactory.create(TrackFormViewModel::class.java)

                                TrackFormScreen(
                                    viewModel = trackViewModel,
                                    navController = navController
                                )
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