package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.lists.RacersList
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.lists.TeamsList
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.lists.TracksList
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModel


@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
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

                item {
                    when (data.teams) {
                        is HomeListState.Loading -> LoadingComposable()
                        is HomeListState.Failure -> FailureComposable()
                        is HomeListState.Success -> TeamsList(data.teams.data, {
                            navController.navigate("team_form")
                        })
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

//////////////////////////////////////////PREVIEWS//////////////////////////////////////////

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
        item { TeamsList(teamsList, {}) }
        item { RacersList(racersList) }
        item { TracksList(trackList) }
    }
}

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
