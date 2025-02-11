package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity

class CountryMapper {

    fun mapToEntity(country: Country): CountryEntity {
        return CountryEntity(
            id = country.id,
            name = country.name,
            image = country.image
        )
    }

    fun mapToDomain(countryEntity: CountryEntity): Country {
        return Country(
            id = countryEntity.id,
            name = countryEntity.name,
            image = countryEntity.image,
        )
    }
}
