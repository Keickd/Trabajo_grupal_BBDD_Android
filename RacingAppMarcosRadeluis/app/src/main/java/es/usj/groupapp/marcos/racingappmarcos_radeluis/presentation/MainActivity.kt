package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database.RacingAppDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeScreen
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val database = RacingAppDatabase.provideDatabase(applicationContext)
            val factory = HomeViewModelFactory(this, database)
            val homeViewModel = factory.create(HomeViewModel::class.java)
            HomeScreen(viewModel = homeViewModel)
        }
    }
}
