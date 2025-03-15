package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import es.usj.groupapp.marcos.racingappmarcos_radeluis.DependencyProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.FailureComposable
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.LoadingComposable
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.viewmodel.RacesViewModel

@Composable
fun RacesScreen(viewModel: RacesViewModel = viewModel(factory = DependencyProvider.racesViewmodelFactory), navController: NavController) {
    val racesState = viewModel.state.collectAsState()
    Column(modifier = Modifier.padding(16.dp)) {
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
                racesStateValue.races.forEach { race ->
                    RaceCard(race = race, navController = navController)
                }
            }
        }
    }
}

@Composable
fun RaceCard(race: Race, navController: NavController) {
    val model =
        ImageRequest.Builder(LocalContext.current)
            .data(Uri.parse(race.track.image))
            .placeholder(R.drawable.track)
            .crossfade(true)
            .build()

    Card(
        modifier = Modifier.clickable {
            navController.navigate("race_form/${race.id}")
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .size(120.dp),
                contentScale = ContentScale.Crop
            )

            Text(text = "Track:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = race.track.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Distance:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = race.track.distance.toString(), style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Date:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = race.date.toString(), style = MaterialTheme.typography.headlineSmall)
        }
    }
}