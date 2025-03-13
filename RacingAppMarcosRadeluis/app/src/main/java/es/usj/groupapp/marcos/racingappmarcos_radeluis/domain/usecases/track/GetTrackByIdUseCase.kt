package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow

class GetTrackByIdUseCase(private val trackRepository: TrackRepository) {

    suspend fun getTrackById(id: Long): Flow<Track> {
        return trackRepository.getTrackById(id)
    }
}