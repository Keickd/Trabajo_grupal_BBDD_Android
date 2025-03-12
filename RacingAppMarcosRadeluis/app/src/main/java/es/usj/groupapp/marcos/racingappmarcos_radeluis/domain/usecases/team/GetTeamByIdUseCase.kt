package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow

class GetTeamByIdUseCase(private val teamRepository: TeamRepository) {

    suspend fun getTeamById(id: Long): Flow<Team> {
        return teamRepository.getTeamById(id)
    }
}