package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TrackLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow

class TrackRepositoryImpl(private val trackLocalDatasource: TrackLocalDatasource): TrackRepository {

    override fun getAllTracks(): Flow<List<Track>> {
        return trackLocalDatasource.getAllTracksFlow()
    }

    override suspend fun insertTrack(track: Track) {
        trackLocalDatasource.insertTrack(track)
    }

    override suspend fun updateTrack(track: Track) {
        trackLocalDatasource.updateTrack(track)
    }

    override suspend fun getTrackById(id: Long): Flow<Track> {
        return trackLocalDatasource.getTrackById(id)
    }
}

