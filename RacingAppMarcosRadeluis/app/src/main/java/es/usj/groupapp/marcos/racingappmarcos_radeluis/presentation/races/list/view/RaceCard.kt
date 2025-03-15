package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.list.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race

@Composable
fun RaceCard(race: Race, navController: NavController) {
    val model =
        ImageRequest.Builder(LocalContext.current)
            .data(Uri.parse(race.track?.image))
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
            Text(text = race.track?.name ?: "", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Distance:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = race.track?.distance.toString(), style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Date:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = race.date.toString(), style = MaterialTheme.typography.headlineSmall)
        }
    }
}