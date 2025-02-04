package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.CountryLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.mapper.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.database.RacingAppDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.CountryRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.CountryRepository
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.InsertAndLoadCountriesUseCase

class HomeViewModelFactory(
    private val context: Context,
    private val database: RacingAppDatabase
) : ViewModelProvider.Factory {

    private val countryMapper = CountryMapper()
    private val countryDao = database.countryDao()
    private val countryLocalDataSource = CountryLocalDataSource(context, countryDao, countryMapper)
    private val countryRepositoryImpl = CountryRepositoryImpl(countryLocalDataSource)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(InsertAndLoadCountriesUseCase(countryRepositoryImpl)) as T
    }
}
