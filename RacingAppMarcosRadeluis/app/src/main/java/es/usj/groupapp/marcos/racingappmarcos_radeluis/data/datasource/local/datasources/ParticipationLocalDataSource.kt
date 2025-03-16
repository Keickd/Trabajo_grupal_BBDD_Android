package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.ParticipationMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.ParticipationDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation

class ParticipationLocalDataSource(
    private val participationDao: ParticipationDao,
    private val participationMapper: ParticipationMapper
) {
    suspend fun insertParticipation(participation: Participation) {
        participationDao.insertParticipation(
            participationMapper.mapToEntity(participation)
        )
    }
}