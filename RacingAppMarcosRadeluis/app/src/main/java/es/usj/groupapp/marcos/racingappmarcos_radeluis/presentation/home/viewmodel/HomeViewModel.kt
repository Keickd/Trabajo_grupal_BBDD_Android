package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.InsertAndLoadCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeListState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val insertAndLoadCountriesUseCase: InsertAndLoadCountriesUseCase): ViewModel() {

    private val homeDataMutableStateFlow = MutableStateFlow<HomeState>(HomeState.Loading)
    val homeDataStateFlow: StateFlow<HomeState> = homeDataMutableStateFlow
    private lateinit var countryList: List<Country>

    init {
        insertAndGetCountries()
    }

    private fun insertAndGetCountries() {
        viewModelScope.launch {
            try {
                countryList = insertAndLoadCountriesUseCase.insertAndLoadCountries()
            } catch (e: Exception) {
                homeDataMutableStateFlow.value = HomeState.Failure(Throwable("Failed to load countries"))
            }
        }
    }

}