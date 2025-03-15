package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.race

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RaceRepository

class InsertRaceUseCase(
    private val raceRepository: RaceRepository
) {
    suspend operator fun invoke(race: Race) {
        raceRepository.insertRace(race)
    }
}