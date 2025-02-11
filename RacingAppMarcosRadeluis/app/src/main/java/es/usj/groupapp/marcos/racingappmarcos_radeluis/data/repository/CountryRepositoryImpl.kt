package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.CountryLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.CountryRepository

class CountryRepositoryImpl(private val countryLocalDataSource: CountryLocalDataSource): CountryRepository {

    override suspend fun insertAndGetCountries(): List<Country> {
        return countryLocalDataSource.insertAndGetCountriesFromJSON()
    }
}