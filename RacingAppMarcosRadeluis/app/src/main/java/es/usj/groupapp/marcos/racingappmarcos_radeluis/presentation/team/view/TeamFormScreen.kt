package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel.TeamFormViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@Composable
fun TeamFormScreen(viewModel: TeamFormViewModel) {
    val teamsState = viewModel.state.collectAsState().value
    var expanded = remember { mutableStateOf(false) }
    var selectedOption = remember { mutableStateOf(Country(123, "Select a country", "")) }//TODO()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        val coroutineScope = rememberCoroutineScope()

        when (teamsState) {
            is TeamState.Error -> {
                FailureComposable()
            }

            TeamState.Loading -> {
                LoadingComposable()
            }

            is TeamState.Success -> {

                OutlinedTextField(
                    value =  viewModel.teamName,
                    onValueChange = { newValue: String -> viewModel.updateTeamName(newValue)  },
                    label = { Text("Team name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box {
                    OutlinedTextField(
                        value = selectedOption.value.name,
                        onValueChange = { },
                        label = { Text("Country") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded.value = !expanded.value }) {
                                Icon(
                                    Icons.Default.KeyboardArrowDown,
                                    contentDescription = "options"
                                )
                            }
                        },
                    )

                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                    ) {
                        val countries = teamsState.countries

                        countries.forEach { country ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        country.name
                                    )
                                },
                                onClick = {
                                    selectedOption.value = country
                                    viewModel.updateCountryId(country.id)
                                    expanded.value = false
                                }
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { coroutineScope.launch { viewModel.addTeam() } },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Add Team")
                }
            }
        }
    }

}



////////////////////////////////////////// GENERAL//////////////////////////////////////////

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

//////////////////////////////////////////PREVIEW//////////////////////////////////////////

@Composable
@Preview(showBackground = true)
fun PreviewTeamFormScreen() {
    val fakeViewModel = object : TeamFormViewModel() {
        override val state: StateFlow<TeamState> = MutableStateFlow(
            TeamState.Success(
                countries = listOf(
                    Country(1, "Spain", ""),
                    Country(2, "France", ""),
                    Country(3, "Germany", "")
                )
            )
        )
    }
    TeamFormScreen(viewModel = fakeViewModel)
}


//////////////////////////////////////////PREVIEW GENERAL//////////////////////////////////////////

@Preview(showBackground = true)
@Composable
fun LoadingComposablePreview(){
    es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.LoadingComposable()
}

@Preview(showBackground = true)
@Composable
fun FailureComposablePreview(){
    es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.FailureComposable()
}
