package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun insertAndGetCountries(): List<Country>
    suspend fun getAllCountries(): Flow<List<Country>>
    suspend fun getCountryById(id: Long): Flow<Country>
}

