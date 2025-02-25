package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils.FlagImage


@Composable
fun RacerCard(racer: Racer) {
    Card {
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)
            .padding(bottom = 20.dp)
            .width(200.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = racer.image.ifBlank { R.drawable.ic_launcher_background }
                ),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .size(120.dp),
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = racer.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    text = racer.country.name,
                    fontSize = 23.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.width(15.dp))

                FlagImage(
                    flagPath = "flags/${racer.country.image}",
                    modifier = Modifier.size(24.dp).align(Alignment.CenterVertically)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = racer.age.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 23.sp,
                )

                Spacer(modifier = Modifier.width(8.dp))


                Text(
                    text = racer.team.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 23.sp,
                )
            }
        }
    }
}

//////////////////////////////////////////PREVIEW RACERS//////////////////////////////////////////

@Preview(showBackground = true)
@Composable
fun RacerCardPreview() {
    RacerCard(
        Racer(
            id = 123,
            name = "Marcos Salas",
            age = 25,
            image = "",
            team = Team(id = 1, name = "USJ",  image = "", country = Country(id = 1, name = "Spain", image = "es.svg")),
            country = Country(id = 1, name = "Spain", image = "es.svg")
        )
    )
}