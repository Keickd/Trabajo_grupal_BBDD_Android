package es.usj.groupapp.marcos.racingappmarcos_radeluis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.CountryLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TeamLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TrackLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TeamMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TrackMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database.RacingAppDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.CountryRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TeamRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TrackRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetCountryByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.DeleteTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetTeamByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.InsertTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.UpdateTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.DeleteTrackUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetTrackByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.InsertTrackUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.UpdateTrackUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel.TeamFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.viewmodel.TrackFormViewModel

object DependencyProvider {

    //ViewModel Factory TEAMS
    val teamViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

            val context = extras[APPLICATION_KEY]?.applicationContext!!

            val database = RacingAppDatabase.provideDatabase(context)

            val countryMapper = CountryMapper()
            val teamMapper = TeamMapper(countryMapper)

            val countryDao = database.countryDao()
            val teamDao = database.teamDao()

            val countryLocalDataSource = CountryLocalDataSource(context, countryDao, countryMapper)
            val teamLocalDataSource = TeamLocalDatasource(teamDao, countryDao, teamMapper)

            val countryRepositoryImpl = CountryRepositoryImpl(countryLocalDataSource)
            val teamRepositoryImpl = TeamRepositoryImpl(teamLocalDataSource)

            val getAllCountriesUseCase = GetAllCountriesUseCase(countryRepositoryImpl)
            val getCountryByIdUseCase = GetCountryByIdUseCase(countryRepositoryImpl)
            val getTeamByIdUseCase = GetTeamByIdUseCase(teamRepositoryImpl)
            val updateTeamUseCase = UpdateTeamUseCase(teamRepositoryImpl)
            val insertTeamUseCase = InsertTeamUseCase(teamRepositoryImpl)
            val deleteTeamUseCase = DeleteTeamUseCase(teamRepositoryImpl)

            val savedStateHandle = extras.createSavedStateHandle()

            return TeamFormViewModel(
                getAllCountriesUseCase = getAllCountriesUseCase,
                getCountryByIdUseCase =  getCountryByIdUseCase,
                getTeamByIdUseCase = getTeamByIdUseCase,
                updateTeamUseCase =  updateTeamUseCase,
                insertTeamUseCase = insertTeamUseCase,
                deleteTeamUseCase = deleteTeamUseCase,
                savedStateHandle = savedStateHandle) as T
        }
    }

    //ViewModel Factory TRACKS
    val trackViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

            val context = extras[APPLICATION_KEY]?.applicationContext!!

            val database = RacingAppDatabase.provideDatabase(context)

            val countryMapper = CountryMapper()
            val trackMapper = TrackMapper(countryMapper)

            val countryDao = database.countryDao()
            val trackDao = database.trackDao()

            val countryLocalDataSource = CountryLocalDataSource(context, countryDao, countryMapper)
            val trackLocalDatasource = TrackLocalDatasource(countryDao, trackDao, trackMapper)

            val countryRepositoryImpl = CountryRepositoryImpl(countryLocalDataSource)
            val trackRepositoryImpl = TrackRepositoryImpl(trackLocalDatasource)

            val getAllCountriesUseCase = GetAllCountriesUseCase(countryRepositoryImpl)
            val getCountryByIdUseCase = GetCountryByIdUseCase(countryRepositoryImpl)
            val getTrackByIdUseCase = GetTrackByIdUseCase(trackRepositoryImpl)
            val updateTrackUseCase = UpdateTrackUseCase(trackRepositoryImpl)
            val insertTrackUseCase = InsertTrackUseCase(trackRepositoryImpl)
            val deleteTrackUseCase = DeleteTrackUseCase(trackRepositoryImpl)

            val savedStateHandle = extras.createSavedStateHandle()

            return TrackFormViewModel(
                getAllCountriesUseCase = getAllCountriesUseCase,
                getCountryByIdUseCase =  getCountryByIdUseCase,
                getTrackByIdUseCase = getTrackByIdUseCase,
                updateTrackUseCase = updateTrackUseCase,
                insertTrackUseCase = insertTrackUseCase,
                deleteTrackUseCase = deleteTrackUseCase,
                savedStateHandle = savedStateHandle) as T
        }
    }


}