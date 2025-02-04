package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.mapper

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity

class CountryMapper {

    fun mapToEntity(country: Country): CountryEntity {
        return CountryEntity(
            name = country.name,
            image = country.image
        )
    }

    fun mapToDomain(countryEntity: CountryEntity): Country {
        return Country(
            name = countryEntity.name,
            image = countryEntity.image,

        )
    }
}
