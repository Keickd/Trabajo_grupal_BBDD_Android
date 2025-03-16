package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.list.view

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race

sealed class RaceState {
    data object Loading : RaceState()
    data class Success(val races: List<Race>?) : RaceState()
    data class Error(val message: String) : RaceState()
}