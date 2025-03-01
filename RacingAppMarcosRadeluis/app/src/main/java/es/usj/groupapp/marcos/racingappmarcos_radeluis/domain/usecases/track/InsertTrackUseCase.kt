package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TrackRepository

class InsertTrackUseCase(val trackRepository: TrackRepository) {
    suspend fun insertTrack(track: Track) {
        trackRepository.insertTrack(track)
    }
}