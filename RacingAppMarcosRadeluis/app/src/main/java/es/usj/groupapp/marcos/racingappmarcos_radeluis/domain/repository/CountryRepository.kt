package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import android.content.Context
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.mapper.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country

interface CountryRepository {
    suspend fun insertAndGetCountries(): List<Country>
}

