package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state = viewModel.homeDataStateFlow.collectAsState()

    when(val stateValue = state.value) {
        is HomeState.Loading -> LoadingComposable()
        is HomeState.Failure -> FailureComposable()
        is HomeState.Data -> {
            val data = state as HomeState.Data

            Column {
                when(data.teams) {
                    is HomeListState.Loading -> LoadingComposable()
                    is HomeListState.Failure -> FailureComposable()
                    is HomeListState.Success -> FailureComposable()
                }

                when(data.racers) {
                    is HomeListState.Loading -> LoadingComposable()
                    is HomeListState.Failure -> FailureComposable()
                    is HomeListState.Success -> FailureComposable()
                }

                when(data.tracks) {
                    is HomeListState.Loading -> LoadingComposable()
                    is HomeListState.Failure -> FailureComposable()
                    is HomeListState.Success -> FailureComposable()
                }
            }
        }
    }
}

///TEAMS///

@Composable
fun TeamsList(teams: List<Team>) {
    LazyRow(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        ) {
            items(teams) { team ->
                TeamCard(team = team, modifier = Modifier.padding(end = 16.dp))
                Spacer(modifier = Modifier.height(16.dp))
            }
    }
}

@Composable
fun TeamCard(team: Team, modifier: Modifier) {
    Card(modifier = modifier
        .height(LocalConfiguration.current.screenHeightDp.dp * 0.3f)) {
        Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = team.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Pais",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }
        }
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


///PREVIEWS///

@Preview(showBackground = true)
@Composable
fun TeamCardPreview() {
    TeamCard(Team(id = 123, name = "Escudería 1", country = Country(id = 1, name = "Spain", image = "")), modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun TeamListPreview() {
    val list: List<Team> = listOf(
        Team(id = 123, name = "Escudería 1", country = Country(id = 1, name = "Spain", image = "")),
        Team(id = 1234, name = "Escudería 2", country = Country(id = 1, name = "Norway", image = "")),
        Team(id = 1235, name = "Escudería 3", country = Country(id = 1, name = "France", image = ""))
    )
    TeamsList(list)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(viewModel = HomeViewModel())
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