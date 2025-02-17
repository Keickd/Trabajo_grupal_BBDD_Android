package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TeamRepository

class InsertTeamUseCase(val teamRepository: TeamRepository) {
    suspend fun insertTeam(team: Team) {
        teamRepository.insertTeam(team)
    }
}