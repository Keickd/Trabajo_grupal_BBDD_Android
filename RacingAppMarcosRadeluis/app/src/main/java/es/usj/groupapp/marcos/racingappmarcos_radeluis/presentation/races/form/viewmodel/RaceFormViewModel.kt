package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.form.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.participation.InsertParticipationUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.race.GetRaceByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.race.InsertRaceUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.racer.GetAllRacersUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.track.GetAllTracksUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.races.list.view.RaceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class RaceFormViewModel(
    private val insertRaceUseCase: InsertRaceUseCase,
    private val getTracksUseCase: GetAllTracksUseCase,
    private val getRacersUseCase: GetAllRacersUseCase,
    private val getRaceByIdUseCase: GetRaceByIdUseCase,
    private val insertParticipationUseCase: InsertParticipationUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<RaceState>(RaceState.Loading)
    val state: MutableStateFlow<RaceState> = _state

    private val _tracks: MutableStateFlow<List<Track>> = MutableStateFlow(emptyList())
    val tracks = _tracks

    private val _racers: MutableStateFlow<List<Racer>> = MutableStateFlow(emptyList())
    val racers = _racers

    private val _participations: MutableStateFlow<List<Participation>> = MutableStateFlow(emptyList())
    val participations = _participations

    private val _race: MutableStateFlow<Race?> = MutableStateFlow(null)
    val race = _race

    private val _selectedDateTime: MutableStateFlow<String> = MutableStateFlow("")
    val selectedDateTime = _selectedDateTime as StateFlow<String>

    private val racerId: Long? = savedStateHandle.get<Long?>("raceId")


    init {
        getAllTracks()
        getAllRacers()
        if (racerId != null) {
            Log.d("RaceFormViewModel", "Loading race with ID: $racerId")
            loadRace(racerId)
        }else{
            Log.d("RaceFormViewModel", "No race ID provided")
            _state.value = RaceState.Success(null)
        }
    }

    fun updateDate(date: String) {
        _selectedDateTime.value = date
    }

    fun loadRace(raceId: Long) {
        viewModelScope.launch {
            try {
                val raceToEdit = getRaceByIdUseCase.getRaceById(raceId)
                _race.value = raceToEdit

                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val formattedDate = dateFormat.format(raceToEdit?.date ?: "")
                _selectedDateTime.value = formattedDate

                _participations.value = raceToEdit?.participations ?: emptyList()

                _state.value = RaceState.Success(null)
                } catch (e: Exception) {
                _state.value = RaceState.Error(e.message ?: "${e.message}")
            }
        }
    }

    private fun getAllTracks() {
        viewModelScope.launch {
            try {
                getTracksUseCase.getAllTracks().collect{ tracks ->
                    _tracks.value = tracks
                    _state.value = RaceState.Success(null)
                }
            } catch (e: Exception) {
                _state.value = RaceState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun getAllRacers(){
        viewModelScope.launch {
            try {
                getRacersUseCase.getAllRacers().collect{ racers ->
                    _racers.value = racers
                }
            } catch (e: Exception) {
                _state.value = RaceState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addParticipationToList(participation: Participation) {
        val currentList = _participations.value.toMutableList()
        currentList.add(participation)
        _participations.value = currentList
    }


    suspend fun insertRace(race: Race) {
        try {
            _state.value = RaceState.Loading
            val insertedRaceId = insertRaceUseCase(race)
            participations.value.forEach{
                val participation = Participation(
                    race_id = insertedRaceId,
                    racer_id = it.racer_id,
                    ranking = it.ranking,
                    best_time = it.best_time
                )
                insertParticipationUseCase.insertParticipation(participation)
            }
            _state.value = RaceState.Success(null)
            } catch (e: Exception) {
            _state.value = RaceState.Error(e.message ?: "Unknown error")
        }
    }

}