package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.TeamEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team

class TeamMapper(private val countryMapper: CountryMapper) {

    fun mapToEntity(team: Team): TeamEntity {
        return TeamEntity(
            id = team.id,
            name = team.name,
            country_id = team.country.id,
            image = team.image,
        )
    }

    fun mapToDomain(teamEntity: TeamEntity, countryEntity: CountryEntity): Team {
        return Team(
            id = teamEntity.id,
            name = teamEntity.name,
            country = countryMapper.mapToDomain(countryEntity),
            image = teamEntity.image,
        )
    }
}
