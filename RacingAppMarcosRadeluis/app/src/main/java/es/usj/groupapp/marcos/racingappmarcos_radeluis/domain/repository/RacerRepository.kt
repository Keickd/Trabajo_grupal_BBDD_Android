package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer import kotlinx.coroutines.flow.Flow

interface RacerRepository {
    fun getAllRacers(): Flow<List<Racer>>
    suspend fun insertRacer(racer: Racer)
    suspend fun getRacerById(id: Long): Flow<Racer>
    suspend fun updateRacer(racer: Racer)
}
