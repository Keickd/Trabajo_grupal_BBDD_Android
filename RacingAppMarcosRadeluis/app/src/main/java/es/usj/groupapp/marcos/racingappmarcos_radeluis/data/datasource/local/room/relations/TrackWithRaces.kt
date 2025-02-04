package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.RaceEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.TrackEntity

data class TrackWithRaces(
    @Embedded val trackEntity: TrackEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "track_id"
    )
    val raceEntities: List<RaceEntity>
)