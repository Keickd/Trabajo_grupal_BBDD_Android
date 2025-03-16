package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import kotlinx.coroutines.flow.Flow

interface RaceRepository {
    suspend fun insertRace(race: Race): Long
    suspend fun getRaceById(id: Long): Race
    suspend fun updateRace(race: Race)
    suspend fun getRacesWithTrack(): Flow<List<Race>>
}