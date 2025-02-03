package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.ParticipationEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.RacerEntity

data class RacerWithParticipations(
    @Embedded val racerEntity: RacerEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "racer_id"
    )
    val participationEntities: List<ParticipationEntity>
)