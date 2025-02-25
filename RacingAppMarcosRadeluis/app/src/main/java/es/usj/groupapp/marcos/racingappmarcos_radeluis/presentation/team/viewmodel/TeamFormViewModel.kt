package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.InsertTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils.saveImageToInternalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TeamFormViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val insertTeamUseCase: InsertTeamUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<TeamState> = MutableStateFlow(TeamState.Loading)
    val state: StateFlow<TeamState> = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), TeamState.Loading
    )

    private val _teamName = MutableStateFlow(savedStateHandle.get<String>("team_name") ?: "")
    val teamName: StateFlow<String> = _teamName

    private val _countryId = MutableStateFlow(savedStateHandle.get<Long?>("country_id"))
    val countryId: StateFlow<Long?> = _countryId

    private val _teamImageUri = MutableStateFlow(savedStateHandle.get<String>("team_image") ?: "")
    val teamImage: StateFlow<String> = _teamImageUri


    init {
        viewModelScope.launch {
            runCatching {
                getAllCountriesUseCase.getAllCountries().collect {
                    _state.value = TeamState.Success(it)
                }
            }.onFailure {
                _state.value = TeamState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun updateTeamName(newName: String) {
        _teamName.value = newName
    }

    fun updateCountryId(newCountryId: Long?) {
        _countryId.value = newCountryId
    }

    fun updateTeamImage(imageUri: String) {
        _teamImageUri.value = imageUri
    }



    fun addTeam() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val country = _state.value.let { state ->
                    if (state is TeamState.Success) {
                        state.countries.find { it.id == _countryId.value }
                    } else null
                }

                if (country == null) {
                    _state.value = TeamState.Error("Invalid country selected")
                    return@launch
                }

                val team = Team(
                    name = _teamName.value,
                    country = country,
                    id = 0,
                    image = _teamImageUri.value
                )

                insertTeamUseCase.insertTeam(team)
            } catch (e: Exception) {
                _state.value = TeamState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
