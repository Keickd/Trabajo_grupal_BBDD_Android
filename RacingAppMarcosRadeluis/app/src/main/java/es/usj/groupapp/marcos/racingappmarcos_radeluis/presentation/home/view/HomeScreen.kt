package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TrackRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetAllTracksUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state = viewModel.homeDataStateFlow.collectAsState()

    when(val stateValue = state.value) {
        is HomeState.Loading -> LoadingComposable()
        is HomeState.Failure -> FailureComposable()
        is HomeState.Data -> {
            val data = stateValue

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding(),
                    bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
                )

            ) {
                // Teams
                item {
                    when (data.teams) {
                        is HomeListState.Loading -> LoadingComposable()
                        is HomeListState.Failure -> FailureComposable()
                        is HomeListState.Success -> TeamsList(data.teams.data)
                    }
                }

                item {
                    when (data.racers) {
                        is HomeListState.Loading -> LoadingComposable()
                        is HomeListState.Failure -> FailureComposable()
                        is HomeListState.Success -> RacersList(data.racers.data)
                    }
                }

                item {
                    when (data.tracks) {
                        is HomeListState.Loading -> LoadingComposable()
                        is HomeListState.Failure -> FailureComposable()
                        is HomeListState.Success -> TracksList(data.tracks.data)
                    }
                }
            }
        }
    }
}


//////////////////////////////////////////TEAMS//////////////////////////////////////////

@Composable
fun TeamsList(teams: List<Team>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(
            text = "TEAMS",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 20.dp, top = 25.dp, bottom = 10.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 20.dp)
            ,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(teams) { team ->
                TeamCard(team = team)
            }
        }
    }
}

@Composable
fun TeamCard(team: Team) {
    Card {
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)
            .background(color = Color(0xfffad9dd))
            .padding(bottom = 20.dp)
            .width(200.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = team.image.ifBlank { R.drawable.ic_launcher_background }
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
                text = team.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    text = team.country.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 22.sp,
                )

                Spacer(modifier = Modifier.width(15.dp))

                FlagImage(
                    flagPath = "flags/${team.country.image}",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun FlagImage(flagPath: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()


    Image(
        painter = rememberAsyncImagePainter("file:///android_asset/$flagPath", imageLoader),
        contentDescription = null,
        modifier = modifier
    )
}

//////////////////////////////////////////RACERS//////////////////////////////////////////

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

//////////////////////////////////////////TRACKS//////////////////////////////////////////

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

@Composable
fun TrackCard(track: Track) {
    Card {
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)
            .padding(bottom = 20.dp)
            .width(200.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = track.image.ifBlank { R.drawable.ic_launcher_background }
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
                text = track.name,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = track.country.name,
                    fontSize = 23.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.width(15.dp))

                FlagImage(
                    flagPath = "flags/${track.country.image}",
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = track.distance.toString() + " km",
                fontSize = 23.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
        }
    }
}

//////////////////////////////////////////PREVIEWS//////////////////////////////////////////

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

@Preview(showBackground = true)
@Composable
fun TeamListPreview() {
    val list: List<Team> = listOf(
        Team(id = 123, name = "Escudería 1", image = "", country = Country(id = 1, name = "Spain", image = "es.svg")),
        Team(id = 1234, name = "Escudería 2", image = "", country = Country(id = 2, name = "Norway", image = "us.svg")),
        Team(id = 1235, name = "Escudería 3",  image = "", country = Country(id = 3, name = "France", image = "fr.svg"))
    )
    TeamsList(list)
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

//////////////////////////////////////////PREVIEW TRACKS//////////////////////////////////////////

@Preview(showBackground = true)
@Composable
fun TrackCardPreview() {
    TrackCard(
        Track(
            id = 123,
            name = "Le Mans",
            distance = 8.65,
            image = "",
            country = Country(id = 1, name = "Italy", image = "it.svg")
        )
    )
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

//////////////////////////////////////////PREVIEW WHOLE SCREEN//////////////////////////////////////////
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val teamsList: List<Team> = listOf(
        Team(id = 123, name = "Escudería 1", image = "", country = Country(id = 1, name = "Spain", image = "es.svg")),
        Team(id = 1234, name = "Escudería 2", image = "", country = Country(id = 2, name = "Norway", image = "us.svg")),
        Team(id = 1235, name = "Escudería 3",  image = "", country = Country(id = 3, name = "France", image = "fr.svg"))
    )

    val racersList: List<Racer> = listOf(
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

    val trackList: List<Track> = listOf(
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
            country = Country(id = 1, name = "Italy", image = "it.svg"))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding(),
                bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
            ),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { TeamsList(teamsList) }
        item { RacersList(racersList) }
        item { TracksList(trackList) }
    }
}

//////////////////////////////////////////GENERAL//////////////////////////////////////////

@Composable
fun LoadingComposable() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier
            .align(Alignment.Center)
            .size(48.dp))
    }
}

@Composable
fun FailureComposable() {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "An error occurred", modifier = Modifier.align(Alignment.Center))
    }
}

//////////////////////////////////////////PREVIEW GENERAL//////////////////////////////////////////

@Preview(showBackground = true)
@Composable
fun LoadingComposablePreview(){
    LoadingComposable()
}

@Preview(showBackground = true)
@Composable
fun FailureComposablePreview(){
    FailureComposable()
}
