package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation

interface ParticipationRepository {
    suspend fun insertParticipation(participation: Participation)
}