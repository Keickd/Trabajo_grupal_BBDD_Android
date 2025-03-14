package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RacerRepository

class DeleteRacerUseCase(private val racerRepository: RacerRepository) {

    suspend fun deleteRacer(id: Long) {
        return racerRepository.deleteRacer(id)
    }
}