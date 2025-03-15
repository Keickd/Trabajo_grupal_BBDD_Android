package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.race.GetAllRacesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.view.RaceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RacesViewModel(
    private val getAllRacesUseCase: GetAllRacesUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow<RaceState>(RaceState.Loading)
    val state: StateFlow<RaceState> = _state

    init {
        getData()
    }

    fun getData(){
        viewModelScope.launch {
            getAllRacesUseCase.getAllRaces().collect { races ->
                _state.value = if (races.isNotEmpty()) {
                    RaceState.Success(races)
                } else {
                    RaceState.Error("No se encontraron carreras")
                }
            }
        }
    }

}