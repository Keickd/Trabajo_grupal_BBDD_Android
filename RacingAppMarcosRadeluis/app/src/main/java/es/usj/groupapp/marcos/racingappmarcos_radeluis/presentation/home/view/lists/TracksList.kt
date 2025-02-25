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
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.cards.TrackCard

@Composable
fun TracksList(tracks: List<Track>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "TRACKS",
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
            items(tracks) { track ->
                TrackCard(track = track)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TracksListPreview() {
    val list: List<Track> = listOf(
        Track(
            id = 123,
            name = "Le Mans",
            distance = 8.65,
            image = "",
            country = Country(id = 1, name = "Italy", image = "it.svg")
        ),
        Track(
            id = 12,
            name = "Le Mans",
            distance = 8.65,
            image = "",
            country = Country(id = 1, name = "Italy", image = "it.svg")
        ),
        Track(
            id = 1,
            name = "Le Mans",
            distance = 8.65,
            image = "",
            country = Country(id = 1, name = "Italy", image = "it.svg")
        ),
        Track(
            id = 1234,
            name = "Le Mans",
            distance = 8.65,
            image = "",
            country = Country(id = 1, name = "Italy", image = "it.svg")
        ),)

    TracksList(list)
}
