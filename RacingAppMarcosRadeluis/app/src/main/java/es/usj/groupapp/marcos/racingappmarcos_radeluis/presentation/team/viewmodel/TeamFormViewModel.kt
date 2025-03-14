package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetCountryByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.DeleteTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetTeamByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.InsertTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.UpdateTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TeamFormViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getCountryByIdUseCase: GetCountryByIdUseCase,
    private val insertTeamUseCase: InsertTeamUseCase,
    private val getTeamByIdUseCase: GetTeamByIdUseCase,
    private val updateTeamUseCase: UpdateTeamUseCase,
    private val deleteTeamUseCase: DeleteTeamUseCase,
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

    private val teamId: Long? = savedStateHandle.get<Long?>("teamId")

    init {
        if (teamId != null) {
            if (_state.value != TeamState.Deleted) {
                loadTeam(teamId)
            }
        } else {
            loadCountries()
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
            val finalImage = teamImage.value.ifBlank {
                "android.resource://es.usj.groupapp.marcos.racingappmarcos_radeluis/${R.drawable.team}"
            }

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
                    image = finalImage
                )

                insertTeamUseCase.insertTeam(team)
            } catch (e: Exception) {
                _state.value = TeamState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadTeam(teamId: Long) {
        viewModelScope.launch {
            try {
                val teamDeferred = async { getTeamByIdUseCase.getTeamById(teamId).firstOrNull() }
                val countriesDeferred = async { getAllCountriesUseCase.getAllCountries().firstOrNull() }

                val team = teamDeferred.await()
                val countries = countriesDeferred.await()

                if (team != null && countries != null) {
                    _teamName.value = team.name
                    _countryId.value = team.country.id
                    _teamImageUri.value = team.image

                    _state.value = TeamState.TeamDetail(team, countries)
                } else {
                    _state.value = TeamState.Error("No se encontraron datos")
                }

            } catch (e: Exception) {
                _state.value = TeamState.Error("Error al cargar los datos: ${e.message}")
            }
        }
    }

    fun loadCountries() {
        viewModelScope.launch {
            getAllCountriesUseCase.getAllCountries().collect { countries ->
                _state.value = TeamState.Success(countries ?: emptyList())
            }
        }
    }

    fun updateTeam(teamId: Long) {
        viewModelScope.launch {
            _countryId.value?.let { countryId ->
                getCountryByIdUseCase.getCountryById(countryId).collect { country ->
                    val teamUpdated = Team(
                        id = teamId,
                        name = _teamName.value,
                        country = country,
                        image = _teamImageUri.value
                    )

                    updateTeamUseCase.updateTeam(teamUpdated)
                }
            }
        }
    }

    fun deleteTeam(teamId: Long){
        viewModelScope.launch {
            deleteTeamUseCase.deleteTeam(teamId)
            _state.value = TeamState.Deleted
        }
    }
}
