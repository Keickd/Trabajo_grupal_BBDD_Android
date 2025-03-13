package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.R
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetAllCountriesUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.country.GetCountryByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetTrackByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.InsertTrackUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.UpdateTrackUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.tracks.view.TrackState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TrackFormViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getCountryByIdUseCase: GetCountryByIdUseCase,
    private val getTrackByIdUseCase: GetTrackByIdUseCase,
    private val insertTrackUseCase: InsertTrackUseCase,
    private val updateTrackUseCase: UpdateTrackUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<TrackState> = MutableStateFlow(TrackState.Loading)
    val state: StateFlow<TrackState> = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), TrackState.Loading
    )

    private val _trackName = MutableStateFlow(savedStateHandle.get<String>("track_name") ?: "")
    val trackName: StateFlow<String> = _trackName

    private val _trackDistance = MutableStateFlow(savedStateHandle.get<String>("track_distance") ?: "")
    val trackDistance: StateFlow<String> = _trackDistance

    private val _countryId = MutableStateFlow(savedStateHandle.get<Long?>("country_id"))
    val countryId: StateFlow<Long?> = _countryId

    private val _trackImageUri = MutableStateFlow(savedStateHandle.get<String>("track_image") ?: "")
    val trackImage: StateFlow<String> = _trackImageUri

    private val trackId: Long? = savedStateHandle.get<Long?>("trackId")

    init {
        if (trackId != null) {
            loadTrack(trackId)
        } else {
            loadCountries()
        }
    }


   /* init {
        viewModelScope.launch {
            runCatching {
                getAllCountriesUseCase.getAllCountries().collect {
                    _state.value = TrackState.Success(it)
                }
            }.onFailure {
                _state.value = TrackState.Error(it.message ?: "Unknown error")
            }
        }
    }*/

    fun updateTrackName(newName: String) {
        _trackName.value = newName
    }

    fun updateTrackDistance(newDistancee: String) {
        _trackDistance.value = newDistancee
    }

    fun updateCountryId(newCountryId: Long?) {
        _countryId.value = newCountryId
    }

    fun updateTrackImage(imageUri: String) {
        _trackImageUri.value = imageUri
    }


    fun addTrack() {
        viewModelScope.launch(Dispatchers.IO) {
            val finalImage = trackImage.value.ifBlank {
                "android.resource://es.usj.groupapp.marcos.racingappmarcos_radeluis/${R.drawable.track}"
            }

            try {
                val country = _state.value.let { state ->
                    if (state is TrackState.Success) {
                        state.countries.find { it.id == _countryId.value }
                    } else null
                }

                if (country == null) {
                    _state.value = TrackState.Error("Invalid country selected")
                    return@launch
                }

                val Track = Track(
                    name = _trackName.value,
                    distance = _trackDistance.value.toDouble(),
                    country = country,
                    id = 0,
                    image = finalImage
                )

                insertTrackUseCase.insertTrack(Track)
            } catch (e: Exception) {
                _state.value = TrackState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadTrack(trackId: Long) {
        viewModelScope.launch {
            try {
                val trackDeferred = async { getTrackByIdUseCase.getTrackById(trackId).firstOrNull() }
                val countriesDeferred = async { getAllCountriesUseCase.getAllCountries().firstOrNull() }

                val track = trackDeferred.await()
                val countries = countriesDeferred.await()

                if (track != null && countries != null) {
                    _trackName.value = track.name
                    _countryId.value = track.country.id
                    _trackDistance.value = track.distance.toString()
                    _trackImageUri.value = track.image

                    _state.value = TrackState.TrackDetail(track, countries)
                } else {
                    _state.value = TrackState.Error("No se encontraron datos")
                }

            } catch (e: Exception) {
                _state.value = TrackState.Error("Error al cargar los datos: ${e.message}")
            }
        }
    }

    fun loadCountries() {
        viewModelScope.launch {
            getAllCountriesUseCase.getAllCountries().collect {
                _state.value = TrackState.Success(it)
            }
        }
    }

    fun updateTrack(trackId: Long) {
        viewModelScope.launch {
            _countryId.value?.let { countryId ->
                getCountryByIdUseCase.getCountryById(countryId).collect { country ->
                    val trackUpdated = Track(
                        id = trackId,
                        name = _trackName.value,
                        country = country,
                        distance = _trackDistance.value.toDouble(),
                        image = _trackImageUri.value
                    )

                    updateTrackUseCase.updateTrack(trackUpdated)
                }
            }
        }
    }
}
