package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.ParticipationEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.RaceEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.TrackEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race

class RaceMapper(private val trackMapper: TrackMapper,private val participationMapper: ParticipationMapper) {
    fun mapToEntity(race: Race): RaceEntity {
        return RaceEntity(
            id = race.id,
            date = race.date,
            track_id = race.track_id
        )
    }

    fun mapToDomain(raceEntity: RaceEntity, trackEntity: TrackEntity, countryEntity: CountryEntity, participationEntities: List<ParticipationEntity>): Race {
        return Race(
            id = raceEntity.id,
            date = raceEntity.date,
            track_id = raceEntity.track_id,
            track = trackMapper.mapToDomain(trackEntity, countryEntity),
            participations = participationEntities.map { participationMapper.mapToDomain(it) }
        )
    }
}