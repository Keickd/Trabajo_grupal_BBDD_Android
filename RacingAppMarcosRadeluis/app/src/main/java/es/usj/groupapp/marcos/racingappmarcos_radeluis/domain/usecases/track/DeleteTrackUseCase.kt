package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TrackRepository

class DeleteTrackUseCase(private val trackRepository: TrackRepository) {

    suspend fun deleteTrack(id: Long) {
        return trackRepository.deleteTrack(id)
    }
}