package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.ParticipationEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.RaceEntity

data class RaceWithParticipations(
    @Embedded val raceEntity: RaceEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "race_id"
    )
    val participationEntities: List<ParticipationEntity>
)
