package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.view

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import es.usj.groupapp.marcos.racingappmarcos_radeluis.DependencyProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel.TeamFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.viewmodel.TrackFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils.FlagImage
import kotlinx.coroutines.launch


@Composable
fun TrackFormScreen(viewModel: TrackFormViewModel = viewModel(factory = DependencyProvider.trackViewModelFactory), navController: NavController, trackId: Long?) {
    val tracksState by viewModel.state.collectAsState()
    val trackName by viewModel.trackName.collectAsState()
    val trackDistance by viewModel.trackDistance.collectAsState()
    val countryId by viewModel.countryId.collectAsState()
    var expanded by remember { mutableStateOf(false) }
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
            viewModel.updateTrackImage(it.toString())
        }
    }

    var countries = emptyList<Country>()

    if (tracksState is TrackState.Success) {
        countries = (tracksState as TrackState.Success).countries
    } else if (tracksState is TrackState.TrackDetail){
        countries = (tracksState as TrackState.TrackDetail).countries
    }

    val selectedOption = countries.find { it.id == countryId } ?: Country(0, "Select a country", "")

    val isButtonEnabled by remember(trackName, trackDistance, countryId, imageUri) {
        derivedStateOf { trackName.isNotBlank() && trackDistance.toDoubleOrNull() != null && selectedOption.id.toInt() != 0 }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .systemBarsPadding(),
    ) {
        val coroutineScope = rememberCoroutineScope()

        when (tracksState) {
            is TrackState.Error -> FailureComposable()
            TrackState.Loading -> LoadingComposable()
            is TrackState.Success -> {
                Text(
                    text = "Create a new track",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp)
                )

                OutlinedTextField(
                    value = trackName,
                    onValueChange = { viewModel.updateTrackName(it) },
                    label = { Text("track name", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = trackDistance,
                    onValueChange = { viewModel.updateTrackDistance(it) },
                    label = { Text("track distance", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box {
                    OutlinedTextField(
                        value = selectedOption.name,
                        onValueChange = {},
                        label = { Text("Country", style = MaterialTheme.typography.headlineSmall) },
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
                                    expanded = false
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
                            viewModel.addTrack()
                            navController.popBackStack()
                        }
                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                ) {
                    Text("Add track", style = MaterialTheme.typography.headlineMedium)
                }
            }
            is TrackState.TrackDetail -> {
                val imageFromTrack = (tracksState as TrackState.TrackDetail).track.image

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edit track",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = {
                        viewModel.deleteTrack(trackId!!)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Borrar pista",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = trackName,
                    onValueChange = { viewModel.updateTrackName(it) },
                    label = { Text("track name", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = trackDistance,
                    onValueChange = { viewModel.updateTrackDistance(it) },
                    label = { Text("track distance", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box {
                    OutlinedTextField(
                        value = selectedOption.name,
                        onValueChange = {},
                        label = { Text("Country", style = MaterialTheme.typography.headlineSmall) },
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
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                imageFromTrack?.let { uri ->
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
                            viewModel.updateTrack(trackId!!)
                            navController.popBackStack()
                        }
                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                ) {
                    Text("Update track", style = MaterialTheme.typography.headlineMedium)
                }
            }
            is TrackState.Deleted ->{
                navController.popBackStack()
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
/*
@Composable
@Preview(showBackground = true)
fun PreviewtrackFormScreen() {

    val fakeViewModel = object : TrackFormViewModel(GetAllCountriesUseCase()) {
        override val state: StateFlow<trackState> = MutableStateFlow(
            trackState.Success(
                countries = listOf(
                    Country(1, "Spain", ""),
                    Country(2, "France", ""),
                    Country(3, "Germany", "")
                )
            )
        )
    }
    trackFormScreen(viewModel = fakeViewModel)
}
*/

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
