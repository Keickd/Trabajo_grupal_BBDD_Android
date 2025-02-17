package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.CountryLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow

class CountryRepositoryImpl(private val countryLocalDataSource: CountryLocalDataSource): CountryRepository {

    override suspend fun insertAndGetCountries(): List<Country> {
        return countryLocalDataSource.insertAndGetCountriesFromJSON()
    }

    override suspend fun getAllCountries(): Flow<List<Country>> {
        return countryLocalDataSource.getAllCountries()
    }
}