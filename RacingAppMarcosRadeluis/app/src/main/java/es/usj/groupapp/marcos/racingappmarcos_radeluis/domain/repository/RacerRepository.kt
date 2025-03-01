package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface RacerRepository {
    fun getAllRacers(): Flow<List<Racer>>
    suspend fun insertRacer(racer: Racer)
}
