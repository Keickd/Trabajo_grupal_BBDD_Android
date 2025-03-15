package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.RaceLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RaceRepository
import kotlinx.coroutines.flow.Flow

class RaceRepositoryImpl(private val raceLocalDataSource: RaceLocalDataSource): RaceRepository {
    override suspend fun insertRace(race: Race) {
        TODO("Not yet implemented")
    }

    override suspend fun getRaceById(id: Long): Race {
        TODO("Not yet implemented")
    }

    override suspend fun updateRace(race: Race) {
        TODO("Not yet implemented")
    }

    override suspend fun getRacesWithTrack(): Flow<List<Race>> {
        return raceLocalDataSource.getRacesWithTrack()
    }
}