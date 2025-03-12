package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel

import android.content.Context;
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.CountryLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TeamLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TeamMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database.RacingAppDatabase;
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.CountryRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TeamRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetCountryByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetTeamByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.InsertTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.UpdateTeamUseCase

class TeamFormViewModelFactory(
    private val context: Context,
    private val database: RacingAppDatabase,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    private val countryMapper = CountryMapper()
    private val teamMapper = TeamMapper(countryMapper)

    private val countryDao = database.countryDao()
    private val teamDao = database.teamDao()

    private val countryLocalDataSource = CountryLocalDataSource(context, countryDao, countryMapper)
    private val teamLocalDataSource = TeamLocalDatasource(teamDao, countryDao, teamMapper)

    private val countryRepositoryImpl = CountryRepositoryImpl(countryLocalDataSource)
    private val teamRepositoryImpl = TeamRepositoryImpl(teamLocalDataSource)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamFormViewModel::class.java)) {
            return TeamFormViewModel(
                getAllCountriesUseCase = GetAllCountriesUseCase(countryRepositoryImpl),
                getCountryByIdUseCase = GetCountryByIdUseCase(countryRepositoryImpl),
                getTeamByIdUseCase = GetTeamByIdUseCase(teamRepositoryImpl),
                updateTeamUseCase = UpdateTeamUseCase(teamRepositoryImpl),
                insertTeamUseCase = InsertTeamUseCase(teamRepositoryImpl),
                savedStateHandle = savedStateHandle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
