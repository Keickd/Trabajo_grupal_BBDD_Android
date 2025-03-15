package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.list.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.usj.groupapp.marcos.racingappmarcos_radeluis.DependencyProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.FailureComposable
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.LoadingComposable
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.list.viewmodel.RacesViewModel

@Composable
fun RacesScreen(viewModel: RacesViewModel = viewModel(factory = DependencyProvider.racesViewmodelFactory), navController: NavController) {
    val racesState = viewModel.state.collectAsState()
    Column(
        modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Races",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = { navController.navigate("race_form") }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar carrera",
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }

        }

        when(val racesStateValue = racesState.value){
            is RaceState.Loading -> LoadingComposable()
            is RaceState.Error -> FailureComposable()
            is RaceState.Success -> {
                racesStateValue.races?.forEach { race ->
                    RaceCard(race = race, navController = navController)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}