package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.cards.TeamCard

@Composable
fun TeamsList(teams: List<Team>, navController: NavHostController, onAddTeamClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "TEAMS",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = onAddTeamClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar equipo",
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(teams) { team ->
                TeamCard(team = team, navController)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TeamListPreview() {
    val list: List<Team> = listOf(
        Team(id = 123, name = "Escudería 1", image = "", country = Country(id = 1, name = "Spain", image = "es.svg")),
        Team(id = 1234, name = "Escudería 2", image = "", country = Country(id = 2, name = "Norway", image = "us.svg")),
        Team(id = 1235, name = "Escudería 3",  image = "", country = Country(id = 3, name = "France", image = "fr.svg"))
    )
    TeamsList(list, rememberNavController(), { })
}
