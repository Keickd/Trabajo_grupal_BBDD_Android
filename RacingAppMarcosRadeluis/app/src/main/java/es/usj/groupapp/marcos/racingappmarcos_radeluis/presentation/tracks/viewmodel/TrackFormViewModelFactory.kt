package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.CountryLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TrackLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TrackMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database.RacingAppDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.CountryRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TrackRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetCountryByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetTrackByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.InsertTrackUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.UpdateTrackUseCase


class TrackFormViewModelFactory(
    private val context: Context,
    private val database: RacingAppDatabase,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    private val countryMapper = CountryMapper()
    private val trackMapper = TrackMapper(countryMapper)

    private val countryDao = database.countryDao()
    private val trackDao = database.trackDao()

    private val countryLocalDataSource = CountryLocalDataSource(context, countryDao, countryMapper)
    private val trackLocalDatasource = TrackLocalDatasource(countryDao, trackDao, trackMapper)

    private val countryRepositoryImpl = CountryRepositoryImpl(countryLocalDataSource)
    private val trackRepositoryImpl = TrackRepositoryImpl(trackLocalDatasource)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackFormViewModel::class.java)) {
            return TrackFormViewModel(
                getAllCountriesUseCase = GetAllCountriesUseCase(countryRepositoryImpl),
                getCountryByIdUseCase = GetCountryByIdUseCase(countryRepositoryImpl),
                getTrackByIdUseCase = GetTrackByIdUseCase(trackRepositoryImpl),
                insertTrackUseCase = InsertTrackUseCase(trackRepositoryImpl),
                updateTrackUseCase = UpdateTrackUseCase(trackRepositoryImpl),
                savedStateHandle = savedStateHandle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
