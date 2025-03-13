package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TrackMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.TrackDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class TrackLocalDatasource(
    private val countryDao: CountryDao,
    private val trackDao: TrackDao,
    private val trackMapper: TrackMapper
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllTracksFlow(): Flow<List<Track>> {
        return trackDao.getAllTracks().flatMapLatest { trackEntities ->
            flow {
                val tracks = trackEntities.map { trackEntity ->
                    val countryEntity = countryDao.getCountryById(trackEntity.country_id).firstOrNull()
                        trackMapper.mapToDomain(trackEntity, countryEntity!!)
                }
                emit(tracks)
            }
        }
    }

    suspend fun insertTrack(track: Track) {
        trackDao.insertTrack(trackMapper.mapToEntity(track))
    }

    suspend fun updateTrack(track: Track){
        trackDao.updateTrack(trackMapper.mapToEntity(track))
    }
}
