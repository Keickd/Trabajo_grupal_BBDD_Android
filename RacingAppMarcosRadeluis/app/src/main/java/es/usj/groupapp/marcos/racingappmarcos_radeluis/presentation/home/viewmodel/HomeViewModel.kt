package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.InsertAndLoadCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.GetAllRacersUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetAllTeamsUsecase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetAllTracksUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class HomeViewModel(
    private val insertAndLoadCountriesUseCase: InsertAndLoadCountriesUseCase,
    private val getAllTeamsUseCase: GetAllTeamsUsecase,
    private val getAllRacersUseCase: GetAllRacersUseCase,
    private val getAllTracksUseCase: GetAllTracksUseCase
) : ViewModel() {

    private val homeDataMutableStateFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    open val homeDataStateFlow: StateFlow<HomeState> = homeDataMutableStateFlow

    private var racersList: List<Racer>? = null
    private var teamsList: List<Team>? = null
    private var tracksList: List<Track>? = null
    private var dataLoadedCounter = 0

    init {
        insertAndGetCountries()
        getData()
    }

    private fun insertAndGetCountries() {
        viewModelScope.launch {
            try {
                insertAndLoadCountriesUseCase.insertAndLoadCountries()
            } catch (e: Exception) {
                homeDataMutableStateFlow.value = HomeState.Failure(Throwable("Failed to load countries"))
            }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            val racersJob = async { loadRacers() }
            val teamsJob = async { loadTeams() }
            val tracksJob = async { loadTracks() }

            racersJob.await()
            teamsJob.await()
            tracksJob.await()

            checkAllDataLoaded()
        }
    }

    private suspend fun loadRacers() {
        getAllRacersUseCase.getAllRacers().collect { racers ->
            racersList = racers
            onDataLoaded()
        }
    }

    private suspend fun loadTeams() {
        getAllTeamsUseCase.getAllTeams().collect { teams ->
            teamsList = teams
            onDataLoaded()
        }
    }

    private suspend fun loadTracks() {
        getAllTracksUseCase.getAllTracks().collect { tracks ->
            tracksList = tracks
            onDataLoaded()
        }
    }

    private fun onDataLoaded() {
        dataLoadedCounter++

        if (dataLoadedCounter == 3) {
            checkAllDataLoaded()
        }
    }

    private fun checkAllDataLoaded() {
        if (racersList != null && teamsList != null && tracksList != null) {
            homeDataMutableStateFlow.value = HomeState.Success(
                racers = racersList!!,
                tracks = tracksList!!,
                teams = teamsList!!
            )
        }
    }
}
