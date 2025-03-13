package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.view

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track

sealed class TrackState {
    data object Loading : TrackState()
    data class Success(val countries: List<Country>) : TrackState()
    data class Error(val message: String) : TrackState()
    data class TrackDetail(val track: Track, val countries: List<Country>) : TrackState()

}
