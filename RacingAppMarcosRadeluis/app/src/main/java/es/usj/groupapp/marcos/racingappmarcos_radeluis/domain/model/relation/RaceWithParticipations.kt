package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race

data class RaceWithParticipations(
    @Embedded val race: Race,
    @Relation(
        parentColumn = "id",
        entityColumn = "race_id"
    )
    val participations: List<Participation>
)
