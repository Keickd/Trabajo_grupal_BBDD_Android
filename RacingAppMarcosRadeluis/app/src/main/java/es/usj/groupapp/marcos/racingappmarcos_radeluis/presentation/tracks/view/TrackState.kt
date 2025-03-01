package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.view

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import kotlinx.serialization.Serializable

sealed class TrackState {
    data object Loading : TrackState()
    data class Success(val countries: List<Country>) : TrackState()
    data class Error(val message: String) : TrackState()
}

@Serializable
data class NewTrack(
    val name: String = "",
    val country_id: Long? = null
)