package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.view

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.usj.groupapp.marcos.racingappmarcos_radeluis.DependencyProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.viewmodel.NewsFormViewModel
import kotlinx.coroutines.launch

@Composable
fun NewsFormScreen(viewModel: NewsFormViewModel = viewModel(factory = DependencyProvider.newsFormViewModelFactory), navController: NavController, newsId: String?) {
    val newsFormState by viewModel.state.collectAsState()
    val newsName by viewModel.newsName.collectAsState()
    val newsDescription by viewModel.newsDescription.collectAsState()

    val isButtonEnabled by remember(newsName, newsDescription) {
        derivedStateOf { newsName.isNotBlank() && newsDescription.isNotBlank() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .systemBarsPadding(),
    ) {
        val coroutineScope = rememberCoroutineScope()

        when (newsFormState) {
            is NewsFormState.Error -> FailureComposable()
            is NewsFormState.Loading -> LoadingComposable()
            is NewsFormState.Success -> {
                Text(
                    text = "Create news",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp)
                )

                OutlinedTextField(
                    value = newsName,
                    onValueChange = { viewModel.updateNewsName(it) },
                    label = { Text("News name", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = newsDescription,
                    onValueChange = { viewModel.updateNewsDescription(it) },
                    label = { Text("News description", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.addNews()
                            navController.popBackStack()
                        }
                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                ) {
                    Text("Add News", style = MaterialTheme.typography.headlineMedium)
                }
            }
            is NewsFormState.NewsDetail -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edit News",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = {
                        viewModel.deleteNews(newsId!!.toString())
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Borrar equipo",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = newsName,
                    onValueChange = { viewModel.updateNewsName(it) },
                    label = { Text("News name", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = newsDescription,
                    onValueChange = { viewModel.updateNewsDescription(it) },
                    label = { Text("News description", style = MaterialTheme.typography.headlineSmall) },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateNews(newsId!!)
                            navController.popBackStack()
                        }
                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                ) {
                    Text("Edit News", style = MaterialTheme.typography.headlineMedium)
                }
            }
            is NewsFormState.Deleted ->{
                navController.popBackStack()
            }
        }
    }
}

////////////////////////////////////////// GENERAL//////////////////////////////////////////

@Composable
fun FailureComposable() {
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "An error occurred", modifier = Modifier.align(Alignment.Center))
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