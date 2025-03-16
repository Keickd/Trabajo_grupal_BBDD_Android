package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.participations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer

@Composable
fun ParticipationFormDialog(
    racers: List<Racer>,
    onDismiss: () -> Unit,
    onSave: (Participation) -> Unit
) {
    var racer = remember { mutableStateOf<Racer?>(null) }
    var ranking = remember { mutableStateOf("") }
    var bestTime = remember { mutableStateOf("") }
    var isExpanded = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Add Participation",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                SearchableDropdown(
                    items = racers,
                    selectedItem = racer.value,
                    onItemSelected = { racer.value = it },
                    isExpanded = isExpanded.value,
                    onExpandChange = { isExpanded.value = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ranking.value,
                    onValueChange = { newText ->
                        if (newText.all { it.isDigit() }) { // Ensure only digits
                            ranking.value = newText
                        }
                    },
                    label = { Text("Ranking") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = bestTime.value,
                    onValueChange = { newText ->
                        if (newText.all { it.isDigit() }) { // Ensure only digits
                            bestTime.value = newText
                        }
                    },
                    label = { Text("Best time") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        if (racer.value != null) {
                            val newParticipation = Participation(
                                racer = racer.value,
                                ranking = ranking.value.toIntOrNull(),
                                best_time = bestTime.value.toLongOrNull(),
                                race_id = 0,
                                racer_id = racer.value?.id ?: 0
                            )
                            onSave(newParticipation)
                        }
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}