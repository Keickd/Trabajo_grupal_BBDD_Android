package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.CountryRepository

class InsertAndLoadCountriesUseCase(private val countryRepository: CountryRepository) {

    suspend fun insertAndLoadCountries(): List<Country> {
        return countryRepository.insertAndGetCountries()
    }
}