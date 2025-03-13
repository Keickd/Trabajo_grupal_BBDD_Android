package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.view

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.viewmodel.RacerFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils.FlagImage
import kotlinx.coroutines.launch

@Composable
fun RacerFormScreen(viewModel: RacerFormViewModel, navController: NavController, racerId: Long?) {
    val racersState by viewModel.state.collectAsState()
    val racerName by viewModel.racerName.collectAsState()
    val racerAge by viewModel.racerAge.collectAsState()
    val teamId by viewModel.teamId.collectAsState()
    val countryId by viewModel.countryId.collectAsState()
    var expandedCountry by remember { mutableStateOf(false) }
    var expandedTeam by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("ImagePicker", "Selected image URI: $it")
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, flag)
            imageUri = it
            viewModel.updateRacerImage(it.toString())
        }
    }

    var countries = emptyList<Country>()
    var teams = emptyList<Team>()

    if (racersState is RacerState.Success) {
        countries = (racersState as RacerState.Success).countries
        teams = (racersState as RacerState.Success).teams
    } else if (racersState is RacerState.RacerDetail){
        countries = (racersState as RacerState.RacerDetail).countries
        teams = (racersState as RacerState.RacerDetail).teams
    }

    val selectedCountry = countries.find { it.id == countryId } ?: Country(0, "Select a country", "")
    val selectedTeam = teams.find { it.id == teamId } ?: Team(0, "Select a team", "", selectedCountry)

    val isButtonEnabled by remember(racerName, racerAge, countryId, teamId, imageUri) {
        derivedStateOf { racerName.isNotBlank() && racerAge.toIntOrNull() != null && selectedCountry.id.toInt() != 0 && selectedTeam.id.toInt() != 0}
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .systemBarsPadding(),
    ) {
        val coroutineScope = rememberCoroutineScope()


        when (racersState) {
            is RacerState.Error -> FailureComposable()
            RacerState.Loading -> LoadingComposable()
            is RacerState.Success -> {
                Text(
                    text = "Create a new racer",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp)
                )

                OutlinedTextField(
                    value = racerName,
                    onValueChange = { viewModel.updateRacerName(it) },
                    label = { Text("Racer name", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = racerAge,
                    onValueChange = { viewModel.updateRacerAge(it) },
                    label = { Text("Racer age", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box {
                    OutlinedTextField(
                        value = selectedCountry.name,
                        onValueChange = {},
                        label = { Text("Country", style = MaterialTheme.typography.headlineSmall) },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expandedCountry = !expandedCountry }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "options",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        },
                        leadingIcon = {
                            selectedCountry.image.takeIf { it.isNotBlank() }?.let { imageUrl ->
                                FlagImage(
                                    flagPath = imageUrl,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expandedCountry,
                        onDismissRequest = { expandedCountry = false },
                    ) {
                        countries.forEach { country ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        FlagImage(
                                            flagPath = country.image,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(country.name, style = MaterialTheme.typography.headlineMedium)
                                    }
                                },
                                onClick = {
                                    viewModel.updateCountryId(country.id)
                                    expandedCountry = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Box {
                    OutlinedTextField(
                        value = selectedTeam.name,
                        onValueChange = {},
                        label = { Text("Team", style = MaterialTheme.typography.headlineSmall) },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expandedTeam = !expandedTeam }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "options",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        },
                        leadingIcon = {
                            selectedTeam.image.takeIf { it.isNotBlank() }?.let { imageUrl ->
                                FlagImage(
                                    flagPath = imageUrl,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expandedTeam,
                        onDismissRequest = { expandedTeam = false },
                    ) {
                        teams.forEach { team ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        FlagImage(
                                            flagPath = team.image,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(team.name, style = MaterialTheme.typography.headlineMedium)
                                    }
                                },
                                onClick = {
                                    viewModel.updateTeamId(team.id)
                                    expandedTeam = false
                                }
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { imagePickerLauncher.launch("image/*") },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add image",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Add Image",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                imageUri?.let { uri ->
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(12.dp))

                            .background(Color.LightGray)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.addRacer()
                            navController.popBackStack()
                        }
                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                ) {
                    Text("Add Racer", style = MaterialTheme.typography.headlineMedium)
                }
            }
            is RacerState.RacerDetail -> {
                val imageFromRacer = (racersState as RacerState.RacerDetail).racer.image

                Text(
                    text = "Edit racer",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp)
                )

                OutlinedTextField(
                    value = racerName,
                    onValueChange = { viewModel.updateRacerName(it) },
                    label = { Text("Racer name", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = racerAge,
                    onValueChange = { viewModel.updateRacerAge(it) },
                    label = { Text("Racer age", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box {
                    OutlinedTextField(
                        value = selectedCountry.name,
                        onValueChange = {},
                        label = { Text("Country", style = MaterialTheme.typography.headlineSmall) },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expandedCountry = !expandedCountry }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "options",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        },
                        leadingIcon = {
                            selectedCountry.image.takeIf { it.isNotBlank() }?.let { imageUrl ->
                                FlagImage(
                                    flagPath = imageUrl,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expandedCountry,
                        onDismissRequest = { expandedCountry = false },
                    ) {
                        countries.forEach { country ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        FlagImage(
                                            flagPath = country.image,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(country.name, style = MaterialTheme.typography.headlineMedium)
                                    }
                                },
                                onClick = {
                                    viewModel.updateCountryId(country.id)
                                    expandedCountry = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Box {
                    OutlinedTextField(
                        value = selectedTeam.name,
                        onValueChange = {},
                        label = { Text("Team", style = MaterialTheme.typography.headlineSmall) },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expandedTeam = !expandedTeam }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "options",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        },
                        leadingIcon = {
                            selectedTeam.image.takeIf { it.isNotBlank() }?.let { imageUrl ->
                                FlagImage(
                                    flagPath = imageUrl,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expandedTeam,
                        onDismissRequest = { expandedTeam = false },
                    ) {
                        teams.forEach { team ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        FlagImage(
                                            flagPath = team.image,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(team.name, style = MaterialTheme.typography.headlineMedium)
                                    }
                                },
                                onClick = {
                                    viewModel.updateTeamId(team.id)
                                    expandedTeam = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                imageFromRacer.let { uri ->
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(12.dp))

                            .background(Color.LightGray)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateRacer(racerId!!)
                            navController.popBackStack()
                        }
                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                ) {
                    Text("Update Racer", style = MaterialTheme.typography.headlineMedium)
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
