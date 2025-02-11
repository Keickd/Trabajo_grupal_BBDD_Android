package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.RacerEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.TeamEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer


class RacerMapper(private val countryMapper: CountryMapper, private val teamMapper: TeamMapper) {

    fun mapToEntity(racer: Racer): RacerEntity {
        return RacerEntity(
            id = racer.id,
            name = racer.name,
            age =  racer.age,
            country_id = racer.country.id,
            team_id = racer.team.id,
        )
    }

    fun mapToDomain(racerEntity: RacerEntity, countryEntity: CountryEntity, teamEntity: TeamEntity): Racer {
        return Racer(
            id = racerEntity.id,
            name = racerEntity.name,
            age = racerEntity.age,
            country = countryMapper.mapToDomain(countryEntity),
            team = teamMapper.mapToDomain(teamEntity, countryEntity),
        )
    }
}
