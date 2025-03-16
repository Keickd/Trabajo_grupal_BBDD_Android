package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.participations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer

@Composable
fun SearchableDropdown(
    items: List<Racer>,
    selectedItem: Racer?,
    onItemSelected: (Racer) -> Unit,
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    var searchText = rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val filteredItems = items.filter { it.name.contains(searchText.value, ignoreCase = true) }

    Column {
        OutlinedTextField(
            value = searchText.value,
            onValueChange = {
                searchText.value = it

                if(!isExpanded && it.isNotBlank()){
                    onExpandChange(true)
                }

            },
            label = { Text("Racer") },
            trailingIcon = {
                IconButton(onClick = { onExpandChange(!isExpanded) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester)
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandChange(false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.name) },
                    onClick = {
                        searchText.value = item.name
                        onExpandChange(false)
                        onItemSelected(item)
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}
