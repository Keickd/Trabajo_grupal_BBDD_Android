package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.ParticipationLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.ParticipationRepository

class ParticipationRepositoryImpl(
    private val participationLocalDataSource: ParticipationLocalDataSource
): ParticipationRepository {
    override suspend fun insertParticipation(participation: Participation) {
        participationLocalDataSource.insertParticipation(participation)
    }
}