package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country

interface CountryRepository {
    suspend fun insertAndGetCountries(): List<Country>
}

