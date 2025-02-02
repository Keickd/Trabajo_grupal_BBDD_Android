package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track

data class CountryWithTracks(
    @Embedded val country: Country,
    @Relation(
        parentColumn = "id",
        entityColumn = "country_id"
    )
    val tracks: List<Track>
)