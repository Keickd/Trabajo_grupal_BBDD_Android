package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.ParticipationEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation

class ParticipationMapper() {
    fun mapToEntity(participation: Participation): ParticipationEntity {
        return ParticipationEntity(
            id = participation.id,
            race_id = participation.race_id,
            racer_id = participation.racer_id,
            ranking = participation.ranking,
            best_time = participation.best_time
        )
    }

    fun mapToDomain(participationEntity: ParticipationEntity): Participation {
        return Participation(
            id = participationEntity.id,
            race_id = participationEntity.race_id,
            racer_id = participationEntity.racer_id,
            ranking = participationEntity.ranking,
            best_time = participationEntity.best_time
        )

    }


}