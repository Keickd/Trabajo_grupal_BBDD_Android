package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.RacerRepository
import kotlinx.coroutines.flow.Flow

class GetRacerByIdUseCase(private val racerRepository: RacerRepository) {

    suspend fun getRacerById(id: Long): Flow<Racer> {
        return racerRepository.getRacerById(id)
    }
}