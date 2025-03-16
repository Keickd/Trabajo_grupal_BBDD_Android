package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.form.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.TextButton
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
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.participations.ParticipationList
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils.FlagImage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceFormScreen(
    viewModel: RaceFormViewModel = viewModel(factory = DependencyProvider.raceFormViewModelFactory),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val tracks by viewModel.tracks.collectAsState()
    val racers by viewModel.racers.collectAsState()
    val participations by viewModel.participations.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        when (state) {
            is RaceState.Error -> FailureComposable()
            is RaceState.Loading -> LoadingComposable()
            is RaceState.Success -> {

                var selectedDateTime = remember { mutableStateOf("") }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Create race",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TextButton(
                        onClick = {
                            try {
                                val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                                val parsedDate = format.parse(selectedDateTime.value)

                                if (parsedDate != null) {
                                    coroutineScope.launch {
                                        viewModel.insertRace(
                                            Race(
                                                date = parsedDate,
                                                track_id = selectedOption.id
                                            )
                                        )
                                        navController.navigate("races")
                                    }
                                } else {
                                    Log.e("RaceFormScreen", "Invalid date format: $selectedDateTime")
                                }
                            } catch (e: Exception) {
                                Log.e("RaceFormScreen", "Error parsing date", e)
                            }
                        },
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(text = "Save")
                    }
                }

                DateTimePickerField(selectedDateTime = selectedDateTime)
                Spacer(modifier = Modifier.height(10.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
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
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        },
                        leadingIcon = {
                            selectedOption.image.takeIf { it.isNotBlank() }?.let { imageUrl ->
                                FlagImage(
                                    flagPath = imageUrl,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        tracks.forEach { track ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        FlagImage(
                                            flagPath = track.image,
                                            modifier = Modifier
                                                .size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(track.name, style = MaterialTheme.typography.bodyLarge)
                                    }
                                },
                                onClick = {
                                    selectedOption = track
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
        ParticipationList(participations, racers, onSave = { participation -> viewModel.addParticipationToList(participation) })
    }
}