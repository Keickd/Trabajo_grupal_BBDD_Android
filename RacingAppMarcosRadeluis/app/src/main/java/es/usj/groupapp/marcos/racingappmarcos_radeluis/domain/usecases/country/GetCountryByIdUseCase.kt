package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow


class GetCountryByIdUseCase(private val countryRepository: CountryRepository) {

    suspend fun getCountryById(id: Long): Flow<Country> {
        return countryRepository.getCountryById(id)
    }
}