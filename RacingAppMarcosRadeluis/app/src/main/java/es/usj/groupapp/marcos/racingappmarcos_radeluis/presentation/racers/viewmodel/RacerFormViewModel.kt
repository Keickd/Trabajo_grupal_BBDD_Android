package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetCountryByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.DeleteRacerUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.GetRacerByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.InsertRacerUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.UpdateRacerUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetAllTeamsUsecase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetTeamByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.view.RacerState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class RacerFormViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getCountryByIdUseCase: GetCountryByIdUseCase,
    private val getAllTeamsUsecase: GetAllTeamsUsecase,
    private val getTeamByIdUseCase: GetTeamByIdUseCase,
    private val insertRacerUseCase: InsertRacerUseCase,
    private val getRacerByIdUseCase: GetRacerByIdUseCase,
    private val updateRacerUseCase: UpdateRacerUseCase,
    private val deleteRacerUseCase: DeleteRacerUseCase,
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

    private val racerId: Long? = savedStateHandle.get<Long?>("racerId")

    init {
        if (racerId != null) {
            loadRacer(racerId)
        } else {
            loadCountriesAndTeams()
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

    fun loadRacer(racerId: Long) {
        viewModelScope.launch {
            runCatching {
                val racer = getRacerByIdUseCase.getRacerById(racerId).firstOrNull()
                    ?: throw Exception("Racer not found")

                // Evitamos cargar los países y equipos si ya están cargados
                if (_state.value !is RacerState.Success) {
                    val countriesDeferred = async { getAllCountriesUseCase.getAllCountries().firstOrNull() }
                    val teamsDeferred = async { getAllTeamsUsecase.getAllTeams().firstOrNull() }

                    val countries = countriesDeferred.await() ?: throw Exception("Countries not found")
                    val teams = teamsDeferred.await() ?: throw Exception("Teams not found")

                    _state.value = RacerState.RacerDetail(racer, countries, teams)
                } else {
                    // Solo actualizamos el racer si ya se cargaron países y equipos
                    val currentState = _state.value as RacerState.Success
                    _state.value = RacerState.RacerDetail(racer, currentState.countries, currentState.teams)
                }

                _racerName.value = racer.name
                _racerAge.value = racer.age.toString()
                _racerImageUri.value = racer.image
                _countryId.value = racer.country.id
                _teamId.value = racer.team.id
            }.onFailure {
                _state.value = RacerState.Error(it.message ?: "Unknown error")
            }
        }
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

    fun loadCountriesAndTeams() {
        if (_state.value is RacerState.Success) return // Si ya está cargado, no hacemos nada

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

    fun updateRacer(racerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentRacer = getRacerByIdUseCase.getRacerById(racerId).firstOrNull()

                if (currentRacer == null) {
                    _state.value = RacerState.Error("Racer not found")
                    return@launch
                }

                val country = _countryId.value?.let { getCountryByIdUseCase.getCountryById(it).firstOrNull() }
                val team = _teamId.value?.let { getTeamByIdUseCase.getTeamById(it).firstOrNull() }

                if (country == null) {
                    _state.value = RacerState.Error("Invalid country selected")
                    return@launch
                }

                if (team == null) {
                    _state.value = RacerState.Error("Invalid team selected")
                    return@launch
                }

                val updatedRacer = currentRacer.copy(
                    name = _racerName.value,
                    age = _racerAge.value.toInt(),
                    image = _racerImageUri.value.ifBlank { currentRacer.image },
                    country = country,
                    team = team
                )

                updateRacerUseCase.updateRacer(updatedRacer)
            } catch (e: Exception) {
                _state.value = RacerState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteRacer(racerId: Long){
        viewModelScope.launch {
            deleteRacerUseCase.deleteRacer(racerId)
            _state.value = RacerState.Deleted
        }
    }
}
