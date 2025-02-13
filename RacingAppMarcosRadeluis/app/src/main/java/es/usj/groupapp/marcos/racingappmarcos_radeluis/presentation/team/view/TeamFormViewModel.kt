package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class TeamFormViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    savedState: SavedStateHandle = SavedStateHandle(),
) : ViewModel() {
    private val _state: MutableStateFlow<TeamState> = MutableStateFlow(TeamState.Loading)

    val state: StateFlow<TeamState> = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(),
        TeamState.Loading
    )

    init {
        viewModelScope.launch {
            runCatching {
                getAllCountriesUseCase.getAllCountries()
                    .collect {
                        _state.value = TeamState.Success(it)
                    }
            }.onFailure {
                Log.e("TeamFormViewModel", it.message ?: "Unknown error")
                _state.value = TeamState.Error(it.message ?: "Unknown error")
            }
        }
    }

    private var _teamName = mutableStateOf(savedState.toRoute<NewTeam>().name)
    private var _CountryId = mutableStateOf(savedState.toRoute<NewTeam>().country_id)

    val teamName: String
        get() = _teamName.value

    fun updateTeamName(newName: String) {
        _teamName.value = newName
    }

    val countryId: Long?
        get() = _CountryId.value

    fun updateCountryId(newId: Long?) {
        _CountryId.value = newId
    }
}

sealed class TeamState {
    data object Loading : TeamState()
    data class Success(val countries: List<Country>) : TeamState()
    data class Error(val message: String) : TeamState()
}

@Serializable
data class NewTeam(
    val name: String = "",
    val country_id: Long? = null
)