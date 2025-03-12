package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TeamRepository

class UpdateTeamUseCase(private val teamRepository: TeamRepository) {

    suspend fun updateTeam(team: Team) {
        return teamRepository.updateTeam(team)
    }
}