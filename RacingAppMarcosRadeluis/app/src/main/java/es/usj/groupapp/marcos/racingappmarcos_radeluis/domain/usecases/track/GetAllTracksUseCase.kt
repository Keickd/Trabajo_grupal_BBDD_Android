package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow

class GetAllTracksUseCase(private val trackRepository: TrackRepository) {

    fun getAllTracks(): Flow<List<Track>> {
        return trackRepository.getAllTracks()
    }
}