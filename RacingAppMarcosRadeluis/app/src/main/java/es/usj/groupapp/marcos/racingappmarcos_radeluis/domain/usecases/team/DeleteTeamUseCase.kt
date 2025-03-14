package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.team

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TeamRepository

class DeleteTeamUseCase(private val teamRepository: TeamRepository) {

    suspend fun deleteTeam(id: Long) {
        return teamRepository.deleteTeam(id)
    }
}