package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track

sealed class HomeState {
    object Loading: HomeState()
    data class Data(
        val racers: HomeListState<List<Racer>>,
        val tracks: HomeListState<List<Track>>,
        val teams: HomeListState<List<Team>>): HomeState()
    data class Failure(val exception: Throwable): HomeState()
}

sealed class HomeListState<out T> {
    object Loading: HomeListState<Nothing>()
    data class Success<T>(val data: T): HomeListState<T>()
    data class Failure(val exception: Throwable): HomeListState<Nothing>()
}