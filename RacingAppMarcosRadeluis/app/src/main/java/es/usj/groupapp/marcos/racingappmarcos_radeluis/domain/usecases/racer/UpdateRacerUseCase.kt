package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RacerRepository

class UpdateRacerUseCase(private val racerRepository: RacerRepository) {
    suspend fun updateRacer(racer: Racer) {
        return racerRepository.updateRacer(racer)
    }
}