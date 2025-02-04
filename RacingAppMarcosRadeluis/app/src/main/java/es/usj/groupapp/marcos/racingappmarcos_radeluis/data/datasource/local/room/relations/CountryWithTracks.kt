package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.TrackEntity

data class CountryWithTracks(
    @Embedded val countryEntity: CountryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "country_id"
    )
    val trackEntities: List<TrackEntity>
)