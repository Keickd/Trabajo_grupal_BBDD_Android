package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TrackRepository

class UpdateTrackUseCase(private val trackRepository: TrackRepository) {
    suspend fun updateTrack(track: Track) {
        return trackRepository.updateTrack(track)
    }
}