package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RacerRepository
import kotlinx.coroutines.flow.Flow

class GetAllRacersUseCase(private val racerRepository: RacerRepository) {

    fun getAllRacers(): Flow<List<Racer>> {
        return racerRepository.getAllRacers()
    }
}