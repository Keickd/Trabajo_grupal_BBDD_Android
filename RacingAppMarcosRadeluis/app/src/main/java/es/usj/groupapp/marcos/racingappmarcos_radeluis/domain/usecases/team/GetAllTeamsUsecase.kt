package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow

class GetAllTeamsUsecase(private val teamRepository: TeamRepository) {

    fun getAllTeams(): Flow<List<Team>> {
        return teamRepository.getAllTeams()
    }
}