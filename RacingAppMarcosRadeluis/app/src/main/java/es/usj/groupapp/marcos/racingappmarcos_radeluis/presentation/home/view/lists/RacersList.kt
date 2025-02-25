package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.cards.RacerCard


@Composable
fun RacersList(racers: List<Racer>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "RACERS",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 20.dp, top = 25.dp, bottom = 10.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(racers) { racer ->
                RacerCard(racer = racer)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RacersListPreview() {
    val list: List<Racer> = listOf(
        Racer(
            id = 123,
            name = "Marcos Salas",
            age = 25,
            image = "",
            team = Team(id = 1, name = "USJ", image = "", country = Country(id = 1, name = "Spain", image = "es.svg")),
            country = Country(id = 1, name = "Spain", image = "es.svg"),),
        Racer(
            id = 123,
            name = "Marcos Salas",
            age = 25,
            image = "",
            team = Team(id = 1, name = "USJ",  image = "", country = Country(id = 1, name = "Spain", image = "es.svg")),
            country = Country(id = 1, name = "Spain", image = "es.svg"),),
        Racer(
            id = 123,
            name = "Marcos Salas",
            age = 25,
            image = "",
            team = Team(id = 1, name = "USJ",  image = "", country = Country(id = 1, name = "Spain", image = "es.svg")),
            country = Country(id = 1, name = "Spain", image = "es.svg"),),
        Racer(
            id = 123,
            name = "Marcos Salas",
            age = 25,
            image = "",
            team = Team(id = 1, name = "USJ",  image = "", country = Country(id = 1, name = "Spain", image = "es.svg")),
            country = Country(id = 1, name = "Spain", image = "es.svg"),),
    )
    RacersList(list)
}
