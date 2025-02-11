package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.TrackEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track

class TrackMapper(private val countryMapper: CountryMapper) {

    fun mapToEntity(track: Track): TrackEntity {
        return TrackEntity(
            id = track.id,
            name = track.name,
            distance = track.distance,
            country_id = track.country.id,
        )
    }

    fun mapToDomain(trackEntity: TrackEntity, countryEntity: CountryEntity): Track {
        return Track(
            id = trackEntity.id,
            name = trackEntity.name,
            distance = trackEntity.distance,
            country = countryMapper.mapToDomain(countryEntity)
        )
    }
}
