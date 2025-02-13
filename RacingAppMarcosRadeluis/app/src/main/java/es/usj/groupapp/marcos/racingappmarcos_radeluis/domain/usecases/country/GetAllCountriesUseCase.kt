package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow

class GetAllCountriesUseCase(private val countryRepository: CountryRepository) {

    suspend fun getAllCountries(): Flow <List<Country>> {
        return countryRepository.getAllCountries()
    }
}