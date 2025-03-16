package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.race

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RaceRepository

class GetRaceByIdUseCase(private val raceRepository: RaceRepository) {
    suspend fun getRaceById(raceId: Long): Race? {
        return raceRepository.getRaceById(raceId)
    }

}