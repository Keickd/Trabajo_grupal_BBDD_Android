package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources

import android.util.Log
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.RaceMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.dao.RaceDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class RaceLocalDataSource(
    private val raceDao: RaceDao,
    private val countryDao: CountryDao,
    private val raceMapper: RaceMapper
) {
    suspend fun insertRace(race: Race): Long {
        return raceDao.insertRace(raceMapper.mapToEntity(race))
    }
    suspend fun getRaceById(id: Long): Race? {
        val raceFullDataEntity = raceDao.getRaceById(id)
        if (raceFullDataEntity == null) {
            return null
        }
        val raceEntity = raceFullDataEntity.raceEntity
        val trackEntity = raceFullDataEntity.trackEntity
        val countryEntity = countryDao.getCountryById(trackEntity.country_id).firstOrNull()
            ?: CountryEntity(0, "", "")
        val participationEntities = raceFullDataEntity.participationEntities

        return raceMapper.mapToDomain(raceEntity, trackEntity, countryEntity, participationEntities)
    }

    fun updateRace(race: Race) {
        raceDao.updateRace(raceMapper.mapToEntity(race))
    }

    suspend fun getRacesWithTrack(): Flow<List<Race>> {
        return raceDao.getRacesWithTrack().map { raceWithTrackList ->
            raceWithTrackList.map { raceWithTrack ->
                val raceEntity = raceWithTrack.raceEntity
                val trackEntity = raceWithTrack.trackEntity
                val participationEntities = raceWithTrack.participationEntities
                val countryEntity = countryDao.getCountryById(trackEntity.country_id).firstOrNull()

                return@map raceMapper.mapToDomain(raceEntity, trackEntity, countryEntity!!, participationEntities)
            }
        }
    }
}