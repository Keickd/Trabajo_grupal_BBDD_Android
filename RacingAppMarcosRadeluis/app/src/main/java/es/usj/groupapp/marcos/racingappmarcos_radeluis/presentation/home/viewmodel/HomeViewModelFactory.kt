package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.CountryLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.RacerLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TeamLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TrackLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.RacerMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TeamMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TrackMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database.RacingAppDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.CountryRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.RacerRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TeamRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TrackRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.InsertAndLoadCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.GetAllRacersUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetAllTeamsUsecase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetAllTracksUseCase

class HomeViewModelFactory(
    private val context: Context,
    private val database: RacingAppDatabase
) : ViewModelProvider.Factory {

    //Mappers
    private val countryMapper = CountryMapper()
    private val teamMapper = TeamMapper(countryMapper)
    private val racerMapper = RacerMapper(countryMapper, teamMapper)
    private val trackMapper = TrackMapper(countryMapper)

    //DAOs
    private val countryDao = database.countryDao()
    private val teamDao = database.teamDao()
    private val racerDao = database.racerDao()
    private val trackDao = database.trackDao()

    //Datasources
    private val countryLocalDataSource = CountryLocalDataSource(context, countryDao, countryMapper)
    private val teamLocalDataSource = TeamLocalDatasource(teamDao, countryDao, teamMapper)
    private val racerLocalDatasource = RacerLocalDatasource(racerDao, teamDao, countryDao, racerMapper)
    private val trackLocalDatasource = TrackLocalDatasource(countryDao, trackDao, trackMapper)

    //Impl
    private val countryRepositoryImpl = CountryRepositoryImpl(countryLocalDataSource)
    private val teamRepositoryImpl = TeamRepositoryImpl(teamLocalDataSource)
    private val racerRepositoryImpl = RacerRepositoryImpl(racerLocalDatasource)
    private val trackRepositoryImpl = TrackRepositoryImpl(trackLocalDatasource)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(InsertAndLoadCountriesUseCase(countryRepositoryImpl),
            GetAllTeamsUsecase(teamRepositoryImpl),
            GetAllRacersUseCase(racerRepositoryImpl),
            GetAllTracksUseCase(trackRepositoryImpl)
        ) as T
    }
}
