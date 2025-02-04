package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.RacerEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.TeamEntity

data class TeamWithRacers (
    @Embedded val teamEntity: TeamEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "team_id"
    )
    val racerEntities: List<RacerEntity>
)