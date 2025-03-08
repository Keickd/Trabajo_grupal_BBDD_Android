package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.settings.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(isDarkMode: MutableState<Boolean>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Dark Mode",
                    style = MaterialTheme.typography.titleLarge
                )

                Switch(
                    checked = isDarkMode.value,
                    onCheckedChange = {
                        isDarkMode.value = !isDarkMode.value
                    })

            }
        }
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
    val isDarkMode = remember { mutableStateOf(false) }
    SettingScreen(isDarkMode)
}