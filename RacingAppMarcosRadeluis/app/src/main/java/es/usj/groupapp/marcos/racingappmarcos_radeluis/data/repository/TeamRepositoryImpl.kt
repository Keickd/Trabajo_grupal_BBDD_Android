package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources.TeamLocalDatasource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow

class TeamRepositoryImpl(private val teamLocalDatasource: TeamLocalDatasource): TeamRepository {
    override fun getAllTeams(): Flow<List<Team>> {
        return teamLocalDatasource.getAllTeamsFlow()
    }

    override suspend fun insertTeam(team: Team) {
        teamLocalDatasource.insertTeam(team)
    }

    override suspend fun getTeamById(id: Long): Flow<Team> {
        return teamLocalDatasource.getTeamById(id)
    }

    override suspend fun updateTeam(team: Team) {
        teamLocalDatasource.updateTeam(team)
    }

    override suspend fun deleteTeam(id: Long) {
       teamLocalDatasource.deleteTeam(id)
    }
}