package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.InsertAndLoadCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.GetAllRacersUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetAllTeamsUsecase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetAllTracksUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeListState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class HomeViewModel(private val insertAndLoadCountriesUseCase: InsertAndLoadCountriesUseCase,
                         private val getAllTeamsUseCase: GetAllTeamsUsecase,
                         private val getAllRacersUseCase: GetAllRacersUseCase,
                         private val getAllTracksUseCase: GetAllTracksUseCase): ViewModel() {

    private val homeDataMutableStateFlow = MutableStateFlow<HomeState>(
        HomeState.Data(
            racers = HomeListState.Loading,
            tracks = HomeListState.Loading,
            teams = HomeListState.Loading
        )
    )

    open val homeDataStateFlow: StateFlow<HomeState> = homeDataMutableStateFlow
    private lateinit var countryList: List<Country>

    init {
        insertAndGetCountries()
        getData()
    }

    private fun insertAndGetCountries() {
        viewModelScope.launch {
            try {
                countryList = insertAndLoadCountriesUseCase.insertAndLoadCountries()
            } catch (e: Exception) {
                print(e.message)
                homeDataMutableStateFlow.value = HomeState.Failure(Throwable("Failed to load countries"))
            }
        }
    }

    private fun updateState(update: (HomeState.Data) -> HomeState.Data) {
        val currentState = homeDataMutableStateFlow.value
        if (currentState is HomeState.Data) {
            homeDataMutableStateFlow.value = update(currentState)
        }
    }


    private fun getData() {
        viewModelScope.launch {
            launch {
                getAllRacersUseCase.getAllRacers().collect { racers ->
                    updateState { it.update(racers = HomeListState.Success(racers)) }
                }
            }

            launch {
                getAllTeamsUseCase.getAllTeams().collect { teams ->
                    updateState { it.update(teams = HomeListState.Success(teams)) }
                }
            }

            launch {
                getAllTracksUseCase.getAllTracks().collect { tracks ->
                    updateState { it.update(tracks = HomeListState.Success(tracks)) }
                }
            }
        }
    }


}