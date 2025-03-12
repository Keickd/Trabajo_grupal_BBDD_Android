package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team

sealed class TeamState {
    data object Loading : TeamState()
    data class Success(val countries: List<Country>) : TeamState()
    data class Error(val message: String) : TeamState()
    data class TeamDetail(val team: Team, val countries: List<Country>) : TeamState()

}
