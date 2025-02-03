package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel: ViewModel() {

    private val homeDataMutableStateFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    val homeDataStateFlow: StateFlow<HomeState> = homeDataMutableStateFlow

}