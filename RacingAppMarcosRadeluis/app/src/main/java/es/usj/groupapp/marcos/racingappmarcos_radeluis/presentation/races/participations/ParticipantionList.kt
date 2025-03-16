package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.participations

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipationList(participations: List<Participation>, racers: List<Racer>, onSave: (Participation) -> Unit) {
    var showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Participations",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = {
                showDialog.value = true
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add participation",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        participations.forEach { participation ->
            Log.d("ParticipationList", "Participation: $participation")
            ParticipationItem(participation = participation)
        }
    }

    if (showDialog.value) {
        ParticipationFormDialog(
            racers = racers,
            onDismiss = { showDialog.value = false },
            onSave = { newParticipation ->
                onSave(newParticipation)
                showDialog.value = false
            }
        )
    }
}