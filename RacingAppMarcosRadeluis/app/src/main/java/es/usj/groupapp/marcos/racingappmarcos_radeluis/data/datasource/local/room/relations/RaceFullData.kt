package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.ParticipationEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.RaceEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.TrackEntity

data class RaceFullData(
    @Embedded val raceEntity: RaceEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "race_id"
    )
    val participationEntities: List<ParticipationEntity>,
    @Relation(
        parentColumn = "track_id",
        entityColumn = "id"
    )
    val trackEntity: TrackEntity,
)
