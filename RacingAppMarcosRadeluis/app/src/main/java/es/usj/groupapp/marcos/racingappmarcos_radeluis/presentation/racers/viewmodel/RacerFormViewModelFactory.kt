package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.CountryLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.RacerLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TeamLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.RacerMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TeamMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database.RacingAppDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.CountryRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.RacerRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TeamRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetCountryByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.GetRacerByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.InsertRacerUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.UpdateRacerUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetAllTeamsUsecase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetTeamByIdUseCase


class RacerFormViewModelFactory(
    private val context: Context,
    private val database: RacingAppDatabase,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    private val countryMapper = CountryMapper()
    private val teamMapper = TeamMapper(countryMapper)
    private val racerMapper = RacerMapper(countryMapper, teamMapper)

    private val countryDao = database.countryDao()
    private val teamDao = database.teamDao()
    private val racerDao = database.racerDao()

    private val countryLocalDataSource = CountryLocalDataSource(context, countryDao, countryMapper)
    private val teamLocalDataSource = TeamLocalDatasource(teamDao, countryDao, teamMapper)
    private val racerLocalDatasource = RacerLocalDatasource(racerDao, teamDao, countryDao, racerMapper)

    private val countryRepositoryImpl = CountryRepositoryImpl(countryLocalDataSource)
    private val teamRepositoryImpl = TeamRepositoryImpl(teamLocalDataSource)
    private val racerRepositoryImpl = RacerRepositoryImpl(racerLocalDatasource)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RacerFormViewModel::class.java)) {
            return RacerFormViewModel(
                getAllCountriesUseCase = GetAllCountriesUseCase(countryRepositoryImpl),
                getCountryByIdUseCase = GetCountryByIdUseCase(countryRepositoryImpl),
                getAllTeamsUsecase = GetAllTeamsUsecase(teamRepositoryImpl),
                getTeamByIdUseCase = GetTeamByIdUseCase(teamRepositoryImpl),
                getRacerByIdUseCase = GetRacerByIdUseCase(racerRepositoryImpl),
                insertRacerUseCase = InsertRacerUseCase(racerRepositoryImpl),
                updateRacerUseCase = UpdateRacerUseCase(racerRepositoryImpl),
                savedStateHandle = savedStateHandle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
