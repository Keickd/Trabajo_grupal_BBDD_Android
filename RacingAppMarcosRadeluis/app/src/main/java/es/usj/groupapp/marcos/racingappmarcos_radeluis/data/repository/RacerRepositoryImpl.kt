package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.RacerLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RacerRepository
import kotlinx.coroutines.flow.Flow

class RacerRepositoryImpl(private val racerLocalDatasource: RacerLocalDatasource): RacerRepository {
    override fun getAllRacers(): Flow<List<Racer>> {
        return racerLocalDatasource.getAllRacersFlow()
    }

    override suspend fun insertRacer(racer: Racer) {
        racerLocalDatasource.insertRacer(racer)
    }

    override suspend fun getRacerById(id: Long): Flow<Racer> {
        return racerLocalDatasource.getRacerById(id)
    }

    override suspend fun updateRacer(racer: Racer) {
        racerLocalDatasource.updateRacer(racer)
    }
}