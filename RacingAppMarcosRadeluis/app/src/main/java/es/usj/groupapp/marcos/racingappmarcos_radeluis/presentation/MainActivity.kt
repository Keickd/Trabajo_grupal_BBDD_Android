package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database.RacingAppDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModelFactory
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamFormScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel.TeamFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel.TeamFormViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val database = RacingAppDatabase.provideDatabase(applicationContext)
            val homeFactory = HomeViewModelFactory(this, database)
            val homeViewModel = homeFactory.create(HomeViewModel::class.java)

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(viewModel = homeViewModel, navController = navController)
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
            }
        }
    }
}
