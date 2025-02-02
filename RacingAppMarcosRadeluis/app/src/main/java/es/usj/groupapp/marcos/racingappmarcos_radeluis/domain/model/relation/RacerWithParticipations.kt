package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer

data class RacerWithParticipations(
    @Embedded val racer: Racer,
    @Relation(
        parentColumn = "id",
        entityColumn = "racer_id"
    )
    val participations: List<Participation>
)