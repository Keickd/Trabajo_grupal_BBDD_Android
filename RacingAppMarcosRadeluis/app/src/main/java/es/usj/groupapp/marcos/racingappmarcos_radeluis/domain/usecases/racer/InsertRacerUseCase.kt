package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RacerRepository


class InsertRacerUseCase(val racerRepository: RacerRepository) {
    suspend fun insertRacer(racer: Racer) {
        racerRepository.insertRacer(racer)
    }
}