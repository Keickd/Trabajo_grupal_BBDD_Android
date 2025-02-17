package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.InsertTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.NewTeam
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

class TeamFormViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val insertTeamUseCase: InsertTeamUseCase,
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
    private var _countryId = mutableStateOf(savedState.toRoute<NewTeam>().country_id)

    val teamName: String
        get() = _teamName.value

    fun updateTeamName(newName: String) {
        _teamName.value = newName
    }

    val countryId: Long?
        get() = _countryId.value

    fun updateCountryId(countryId: Long?) {
        _countryId.value = countryId
    }

    suspend fun addTeam() {
        withContext(Dispatchers.IO) {
            try {
                val team =
                    Team(
                        name = teamName,
                        country = TODO(),
                        id = TODO(),
                        image = TODO()
                    )

                insertTeamUseCase.insertTeam(team)
            } catch (e: Exception) {
                _state.value = TeamState.Error(e.message ?: "Unknown error")
            }
        }

    }

}

