package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.form.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.usj.groupapp.marcos.racingappmarcos_radeluis.DependencyProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.FailureComposable
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.LoadingComposable
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.form.viewmodel.RaceFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.list.view.RaceState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils.FlagImage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceFormScreen(viewModel: RaceFormViewModel = viewModel(factory = DependencyProvider.raceFormViewModelFactory), navController: NavController) {
    val state = viewModel.state.collectAsState().value
    val tracks = viewModel.tracks.collectAsState().value
    var selectedOption by remember {
        mutableStateOf(
            Track(
                id = 0,
                name = "Select a track",
                image = "",
                country = Country(0, "Select a country", ""),
                distance = 0.00
            )
        )
    }

    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .systemBarsPadding(),
    ) {
        when(state) {
            is RaceState.Error -> FailureComposable()
            is RaceState.Loading -> LoadingComposable()
            is RaceState.Success -> {
                Text(
                    text = "Create race",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp)
                )


                var selectedDateTime = remember { mutableStateOf("") }
                DateTimePickerField(selectedDateTime)
                Spacer(modifier = Modifier.padding(10.dp))

                Box {
                    OutlinedTextField(
                        value = selectedOption.name,
                        onValueChange = {},
                        label = { Text("Track", style = MaterialTheme.typography.headlineSmall) },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "options",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        },
                        leadingIcon = {
                            selectedOption.image.takeIf { it.isNotBlank() }?.let { imageUrl ->
                                FlagImage(
                                    flagPath = imageUrl,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        tracks.forEach { track ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        FlagImage(
                                            flagPath = track.image,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(track.name, style = MaterialTheme.typography.headlineMedium)
                                    }
                                },
                                onClick = {
                                    selectedOption = track
                                    expanded = false

                                    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

                                    coroutineScope.launch {
                                        viewModel.insertRace(
                                            Race(
                                                date = format.parse(selectedDateTime.value)!!,
                                                track_id = selectedOption.id
                                            )
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}
