package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team

data class TeamWithRacers (
    @Embedded val team: Team,
    @Relation(
        parentColumn = "id",
        entityColumn = "team_id"
    )
    val racers: List<Racer>
)