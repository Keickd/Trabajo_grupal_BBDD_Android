package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.cards

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils.FlagImage


@Composable
fun TeamCard(team: Team) {
    Card( elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),) {
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)
            .background(color = Color(0xF5F5DC))
            .padding(bottom = 20.dp)
            .width(205.dp)) {

            val model =
                ImageRequest.Builder(LocalContext.current)
                    .data(Uri.parse(team.image))
                    .placeholder(R.drawable.team)
                    .crossfade(true)
                    .build()


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

            /*
            *   val context = LocalContext.current
            val accessibleUri = getAccessibleUri(context, Uri.parse(team.image))

            val model = ImageRequest.Builder(context)
                .data(accessibleUri ?: R.drawable.team)
                .placeholder(R.drawable.team)
                .crossfade(true)
                .build()

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

            * */

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = team.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp).fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp).fillMaxWidth()) {
                Text(
                    text = team.country.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 22.sp,
                )

                Spacer(modifier = Modifier.width(15.dp))

                FlagImage(
                    flagPath = team.country.image,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(24.dp)
                )
            }
        }
    }
}

//////////////////////////////////////////PREVIEW TEAMS//////////////////////////////////////////
@Preview(showBackground = true)
@Composable
fun TeamCardPreview() {
    TeamCard(
        Team(
            id = 123,
            name = "Escudería 1",
            image = "",
            country = Country(id = 4, name = "Spain", image = "es.svg")
        )
    )
}