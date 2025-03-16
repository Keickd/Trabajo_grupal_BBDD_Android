package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.participation

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.ParticipationRepository

class InsertParticipationUseCase(
    private val participationRepository: ParticipationRepository
) {
    suspend fun insertParticipation(participation: Participation) {
        participationRepository.insertParticipation(participation)
    }

}