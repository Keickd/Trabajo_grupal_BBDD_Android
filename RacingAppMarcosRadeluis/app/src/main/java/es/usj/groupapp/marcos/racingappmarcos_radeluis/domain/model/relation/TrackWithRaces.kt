package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track

data class TrackWithRaces(
    @Embedded val track: Track,
    @Relation(
        parentColumn = "id",
        entityColumn = "track_id"
    )
    val races: List<Race>
)