package es.usj.groupapp.marcos.racingappmarcos_radeluis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.firestore.FirebaseFirestore
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.CountryLocalDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.RacerLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TeamLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TrackLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.RacerMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TeamMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TrackMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database.RacingAppDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.remote.datasources.NewsDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.CountryRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.NewsRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.RacerRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TeamRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.TrackRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetCountryByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.InsertAndLoadCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.AddNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.DeleteNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.GetNewsByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.GetNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.UpdateNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.DeleteRacerUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.GetAllRacersUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.GetRacerByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.InsertRacerUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.UpdateRacerUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.DeleteTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetAllTeamsUsecase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.GetTeamByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.InsertTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team.UpdateTeamUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.DeleteTrackUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetAllTracksUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetTrackByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.InsertTrackUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.UpdateTrackUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel.HomeViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.viewmodel.NewsFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.list.viewmodel.NewsViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.racers.viewmodel.RacerFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.viewmodel.TeamFormViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.viewmodel.TrackFormViewModel

object DependencyProvider {

    //ViewModel Factory HOME
    val homeViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

            val context = extras[APPLICATION_KEY]?.applicationContext!!

            val database = RacingAppDatabase.provideDatabase(context)

            val countryMapper = CountryMapper()
            val teamMapper = TeamMapper(countryMapper)
            val racerMapper = RacerMapper(countryMapper, teamMapper)
            val trackMapper = TrackMapper(countryMapper)

            val countryDao = database.countryDao()
            val teamDao = database.teamDao()
            val racerDao = database.racerDao()
            val trackDao = database.trackDao()

            val countryLocalDataSource = CountryLocalDataSource(context, countryDao, countryMapper)
            val teamLocalDataSource = TeamLocalDatasource(teamDao, countryDao, teamMapper)
            val racerLocalDatasource = RacerLocalDatasource(racerDao, teamDao, countryDao, racerMapper)
            val trackLocalDatasource = TrackLocalDatasource(countryDao, trackDao, trackMapper)

            val countryRepositoryImpl = CountryRepositoryImpl(countryLocalDataSource)
            val teamRepositoryImpl = TeamRepositoryImpl(teamLocalDataSource)
            val racerRepositoryImpl = RacerRepositoryImpl(racerLocalDatasource)
            val trackRepositoryImpl = TrackRepositoryImpl(trackLocalDatasource)

            val insertAndLoadCountriesUseCase = InsertAndLoadCountriesUseCase(countryRepositoryImpl)
            val getAllTeamsUseCase = GetAllTeamsUsecase(teamRepositoryImpl)
            val getAllRacersUseCase = GetAllRacersUseCase(racerRepositoryImpl)
            val getAllTracksUseCase = GetAllTracksUseCase(trackRepositoryImpl)

            return HomeViewModel(
                insertAndLoadCountriesUseCase = insertAndLoadCountriesUseCase,
                getAllTeamsUseCase = getAllTeamsUseCase,
                getAllRacersUseCase = getAllRacersUseCase,
                getAllTracksUseCase = getAllTracksUseCase,
                ) as T
        }
    }

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

    //ViewModel Factory RACERS
    val racersViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

            val context = extras[APPLICATION_KEY]?.applicationContext!!

            val database = RacingAppDatabase.provideDatabase(context)

            val countryMapper = CountryMapper()
            val teamMapper = TeamMapper(countryMapper)
            val racerMapper = RacerMapper(countryMapper, teamMapper)

            val countryDao = database.countryDao()
            val teamDao = database.teamDao()
            val racerDao = database.racerDao()

            val countryLocalDataSource = CountryLocalDataSource(context, countryDao, countryMapper)
            val teamLocalDataSource = TeamLocalDatasource(teamDao, countryDao, teamMapper)
            val racerLocalDatasource = RacerLocalDatasource(racerDao, teamDao, countryDao, racerMapper)

            val countryRepositoryImpl = CountryRepositoryImpl(countryLocalDataSource)
            val teamRepositoryImpl = TeamRepositoryImpl(teamLocalDataSource)
            val racerRepositoryImpl = RacerRepositoryImpl(racerLocalDatasource)

            val getAllCountriesUseCase = GetAllCountriesUseCase(countryRepositoryImpl)
            val getCountryByIdUseCase = GetCountryByIdUseCase(countryRepositoryImpl)
            val getAllTeamsUseCase = GetAllTeamsUsecase(teamRepositoryImpl)
            val getTeamByIdUseCase = GetTeamByIdUseCase(teamRepositoryImpl)
            val getRacerByIdUseCase = GetRacerByIdUseCase(racerRepositoryImpl)
            val updateRacerByIdUseCase = UpdateRacerUseCase(racerRepositoryImpl)
            val insertRacerUseCase = InsertRacerUseCase(racerRepositoryImpl)
            val deleteRacerUseCase = DeleteRacerUseCase(racerRepositoryImpl)

            val savedStateHandle = extras.createSavedStateHandle()

            return RacerFormViewModel(
                getAllCountriesUseCase = getAllCountriesUseCase,
                getCountryByIdUseCase =  getCountryByIdUseCase,
                getAllTeamsUsecase = getAllTeamsUseCase,
                getTeamByIdUseCase = getTeamByIdUseCase,
                getRacerByIdUseCase = getRacerByIdUseCase,
                insertRacerUseCase = insertRacerUseCase,
                updateRacerUseCase = updateRacerByIdUseCase,
                deleteRacerUseCase = deleteRacerUseCase,
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

    //ViewModel Factory NEWS
    val newsViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

            val db = FirebaseFirestore.getInstance()

            val newsDatasource = NewsDataSource(db)

            val newsRepositoryImpl = NewsRepositoryImpl(newsDatasource)

            val getNewsUseCase = GetNewsUseCase(newsRepositoryImpl)

            return NewsViewModel(
                getNewsUseCase = getNewsUseCase) as T
        }
    }

    //ViewModel Factory NEWS FORM
    val newsFormViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

            val db = FirebaseFirestore.getInstance()

            val newsDatasource = NewsDataSource(db)

            val newsRepositoryImpl = NewsRepositoryImpl(newsDatasource)

            val addNewsUseCase = AddNewsUseCase(newsRepositoryImpl)
            val getNewsByIdUseCase = GetNewsByIdUseCase(newsRepositoryImpl)

            val updateNewsUseCase = UpdateNewsUseCase(newsRepositoryImpl)
            val deleteNewsUseCase = DeleteNewsUseCase(newsRepositoryImpl)

            val savedStateHandle = extras.createSavedStateHandle()

            return NewsFormViewModel(
                addNewsUseCase = addNewsUseCase,
                getNewsByIdUseCase = getNewsByIdUseCase,
                updateNewsUseCase = updateNewsUseCase,
                deleteNewsUseCase = deleteNewsUseCase,
                savedStateHandle = savedStateHandle
                ) as T
        }
    }
}