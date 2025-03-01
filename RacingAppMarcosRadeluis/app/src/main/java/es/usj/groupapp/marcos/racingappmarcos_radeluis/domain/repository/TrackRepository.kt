package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun getAllTracks(): Flow<List<Track>>
    suspend fun insertTrack(track: Track)
}
