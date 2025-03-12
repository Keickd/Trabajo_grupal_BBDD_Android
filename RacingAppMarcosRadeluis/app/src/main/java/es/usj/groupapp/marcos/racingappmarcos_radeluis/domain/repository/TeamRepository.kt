package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getAllTeams(): Flow<List<Team>>
    suspend fun insertTeam(team: Team)
    suspend fun getTeamById(id: Long): Flow<Team>
    suspend fun updateTeam(team: Team)
}
