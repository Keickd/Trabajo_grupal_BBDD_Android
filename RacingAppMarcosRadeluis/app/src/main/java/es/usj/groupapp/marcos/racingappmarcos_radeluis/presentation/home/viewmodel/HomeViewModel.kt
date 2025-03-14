package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.InsertAndLoadCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.GetAllRacersUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetAllTeamsUsecase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetAllTracksUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

open class HomeViewModel(
    private val insertAndLoadCountriesUseCase: InsertAndLoadCountriesUseCase,
    private val getAllTeamsUseCase: GetAllTeamsUsecase,
    private val getAllRacersUseCase: GetAllRacersUseCase,
    private val getAllTracksUseCase: GetAllTracksUseCase
) : ViewModel() {

    private val homeDataMutableStateFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    open val homeDataStateFlow: StateFlow<HomeState> = homeDataMutableStateFlow

    init {
        insertAndGetCountries()
        collectData()
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

    private fun collectData() {
        viewModelScope.launch {
            combine(
                getAllRacersUseCase.getAllRacers(),
                getAllTeamsUseCase.getAllTeams(),
                getAllTracksUseCase.getAllTracks()
            ) { racers, teams, tracks ->
                HomeState.Success(
                    racers = racers,
                    teams = teams,
                    tracks = tracks
                )
            }.collect { homeState ->
                homeDataMutableStateFlow.value = homeState
            }
        }
    }
}