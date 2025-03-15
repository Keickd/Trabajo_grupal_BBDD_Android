package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.race

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RaceRepository
import kotlinx.coroutines.flow.Flow

class GetAllRacesUseCase(private val repository: RaceRepository) {
    suspend fun getAllRaces(): Flow<List<Race>>{
        return repository.getRacesWithTrack()
    }
}