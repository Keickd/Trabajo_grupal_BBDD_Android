package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import kotlinx.serialization.Serializable

sealed class TeamState {
    data object Loading : TeamState()
    data class Success(val countries: List<Country>) : TeamState()
    data class Error(val message: String) : TeamState()
}

@Serializable
data class NewTeam(
    val name: String = "",
    val country_id: Long? = null
)