package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.RaceEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race

class RaceMapper {
    fun mapToEntity(race: Race): RaceEntity {
        return RaceEntity(
            id = race.id,
            date = race.date,
            track_id = race.track_id
        )
    }

    fun mapToDomain(raceEntity: RaceEntity): Race {
        return Race(
            id = raceEntity.id,
            date = raceEntity.date,
            track_id = raceEntity.track_id
        )
    }
}