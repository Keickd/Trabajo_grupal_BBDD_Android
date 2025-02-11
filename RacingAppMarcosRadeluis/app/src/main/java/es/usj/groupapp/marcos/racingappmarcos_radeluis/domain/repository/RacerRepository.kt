package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import kotlinx.coroutines.flow.Flow

interface RacerRepository {
    fun getAllRacers(): Flow<List<Racer>>
}
