package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.view

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team

sealed class RacerState {
    data object Loading : RacerState()
    data class Success(val countries: List<Country>, val teams: List<Team>) : RacerState()
    data class Error(val message: String) : RacerState()
    data class RacerDetail(val racer: Racer, val countries: List<Country>, val teams: List<Team>) : RacerState()
}
