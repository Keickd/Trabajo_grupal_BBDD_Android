package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country


@Composable
fun TeamFormScreen(viewModel: TeamFormViewModel) {
    val teamsState = viewModel.state.collectAsState().value
    var expanded = remember { mutableStateOf(false) }
    var selectedOption = remember { mutableStateOf(Country(null, "Select a country", "")) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        when (teamsState) {
            is TeamState.Error -> {
                Column {
                    Text(text = teamsState.message)
                }
            }

            TeamState.Loading -> {
                Text(text = "Loading")
            }

            is TeamState.Success -> {
                OutlinedTextField(
                    value = "",
                    onValueChange = { newValue: String -> {

                    } },
                    label = { Text("Team name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box {
                    OutlinedTextField(
                        value = selectedOption.value.name,
                        onValueChange = {},
                        label = { Text("Team name") },
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
                                    expanded.value = false
                                }
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Add Team")
                }
            }
        }
    }

}