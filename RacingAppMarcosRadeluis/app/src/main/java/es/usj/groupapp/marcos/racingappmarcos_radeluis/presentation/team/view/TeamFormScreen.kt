package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils.FlagImage
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel.TeamFormViewModel
import kotlinx.coroutines.launch

@Composable
fun TeamFormScreen(viewModel: TeamFormViewModel, navController: NavController) {
    val teamsState by viewModel.state.collectAsState()
    val teamName by viewModel.teamName.collectAsState()
    val countryId by viewModel.countryId.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("ImagePicker", "Selected image URI: $it")
            imageUri = it
            viewModel.updateTeamImage(it.toString())
        }
    }

    val countries = if (teamsState is TeamState.Success) {
        (teamsState as TeamState.Success).countries
    } else {
        emptyList()
    }

    val selectedOption = countries.find { it.id == countryId } ?: Country(0, "Select a country", "")

    val isButtonEnabled by remember(teamName, countryId, imageUri) {
        derivedStateOf { teamName.isNotBlank() && selectedOption.id.toInt() != 0 && imageUri != null }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .systemBarsPadding(),
    ) {
        val coroutineScope = rememberCoroutineScope()

        when (teamsState) {
            is TeamState.Error -> FailureComposable()
            TeamState.Loading -> LoadingComposable()
            is TeamState.Success -> {
                Text(
                    text = "Create a new team",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp)
                )

                OutlinedTextField(
                    value = teamName,
                    onValueChange = { viewModel.updateTeamName(it) },
                    label = { Text("Team name", style = MaterialTheme.typography.headlineSmall) },
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
                            viewModel.addTeam()
                            navController.popBackStack()
                        }
                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                ) {
                    Text("Add Team", style = MaterialTheme.typography.headlineMedium)
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
/*
@Composable
@Preview(showBackground = true)
fun PreviewTeamFormScreen() {

    val fakeViewModel = object : TeamFormViewModel(GetAllCountriesUseCase()) {
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
