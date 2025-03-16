package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.form.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.race.InsertRaceUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetAllTracksUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.list.view.RaceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RaceFormViewModel(
    private val insertRaceUseCase: InsertRaceUseCase,
    private val getTracksUseCase: GetAllTracksUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<RaceState>(RaceState.Loading)
    val state: MutableStateFlow<RaceState> = _state

    private val _tracks: MutableStateFlow<List<Track>> = MutableStateFlow(emptyList())
    val tracks = _tracks


//    private val _raceName = MutableStateFlow("")
//    val raceName: MutableStateFlow<String> = _raceName

    init {
        getAllTracks()
        _state.value = RaceState.Success(null)
    }

    private fun getAllTracks() {
        viewModelScope.launch {
            try {
                getTracksUseCase.getAllTracks().collect{ tracks ->
                    Log.d("TrackViewModel", tracks.toString())
                    _tracks.value = tracks
                    _state.value = RaceState.Success(null)
                }
            } catch (e: Exception) {
                _state.value = RaceState.Error(e.message ?: "Unknown error")
            }
        }
    }


    suspend fun insertRace(race: Race) {
        try {
            _state.value = RaceState.Loading
            insertRaceUseCase(race)
            _state.value = RaceState.Success(null)
            } catch (e: Exception) {
            _state.value = RaceState.Error(e.message ?: "Unknown error")
        }
    }

}