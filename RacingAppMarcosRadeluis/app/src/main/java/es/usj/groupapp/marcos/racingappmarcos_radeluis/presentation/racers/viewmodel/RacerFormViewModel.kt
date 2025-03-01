package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.InsertRacerUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetAllTeamsUsecase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.view.RacerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class RacerFormViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getAllTeamsUsecase: GetAllTeamsUsecase,
    private val insertRacerUseCase: InsertRacerUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<RacerState> = MutableStateFlow(RacerState.Loading)
    val state: StateFlow<RacerState> = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), RacerState.Loading
    )

    private val _racerName = MutableStateFlow(savedStateHandle.get<String>("racer_name") ?: "")
    val racerName: StateFlow<String> = _racerName

    private val _racerAge = MutableStateFlow(savedStateHandle.get<String>("racer_age") ?: "")
    val racerAge: StateFlow<String> = _racerAge

    private val _countryId = MutableStateFlow(savedStateHandle.get<Long?>("country_id"))
    val countryId: StateFlow<Long?> = _countryId

    private val _teamId = MutableStateFlow(savedStateHandle.get<Long?>("team_id"))
    val teamId: StateFlow<Long?> = _teamId

    private val _racerImageUri = MutableStateFlow(savedStateHandle.get<String>("racer_image") ?: "")
    val racerImage: StateFlow<String> = _racerImageUri


    init {
        viewModelScope.launch {
            runCatching {
                val countriesDeferred = async { getAllCountriesUseCase.getAllCountries().first() }
                val teamsDeferred = async { getAllTeamsUsecase.getAllTeams().first() }

                val countries = countriesDeferred.await()
                val teams = teamsDeferred.await()

                _state.value = RacerState.Success(countries, teams)
            }.onFailure {
                _state.value = RacerState.Error(it.message ?: "Unknown error")
            }
        }
    }


    fun updateRacerName(newName: String) {
        _racerName.value = newName
    }

    fun updateRacerAge(newAge: String) {
        _racerAge.value = newAge
    }

    fun updateCountryId(newCountryId: Long?) {
        _countryId.value = newCountryId
    }

    fun updateTeamId(newTeamId: Long?) {
        _teamId.value = newTeamId
    }

    fun updateRacerImage(imageUri: String) {
        _racerImageUri.value = imageUri
    }


    fun addRacer() {
        viewModelScope.launch(Dispatchers.IO) {
            val finalImage = racerImage.value.ifBlank {
                "android.resource://es.usj.groupapp.marcos.racingappmarcos_radeluis/${R.drawable.pilot}"
            }

            try {
                val country = _state.value.let { state ->
                    if (state is RacerState.Success) {
                        state.countries.find { it.id == _countryId.value }
                    } else null
                }

                val team = _state.value.let { state ->
                    if (state is RacerState.Success) {
                        state.teams.find { it.id == _teamId.value }
                    } else null
                }

                if (country == null) {
                    _state.value = RacerState.Error("Invalid country selected")
                    return@launch
                }

                if (team == null) {
                    _state.value = RacerState.Error("Invalid team selected")
                    return@launch
                }

                val racer = Racer(
                    name = _racerName.value,
                    country = country,
                    id = 0,
                    image = finalImage, team = team,
                    age =_racerAge.value.toInt(),
                )

                insertRacerUseCase.insertRacer(racer)
            } catch (e: Exception) {
                _state.value = RacerState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
