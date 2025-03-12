package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TeamMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.TeamDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow


class TeamLocalDatasource(
    private val teamDao: TeamDao,
    private val countryDao: CountryDao,
    private val teamMapper: TeamMapper
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllTeamsFlow(): Flow<List<Team>> {
        return teamDao.getAllTeams().flatMapLatest { teamEntities ->
            flow {
                val teams = teamEntities.map { teamEntity ->
                    val countryEntity = countryDao.getCountryById(teamEntity.country_id).firstOrNull()
                    teamMapper.mapToDomain(teamEntity, countryEntity!!)
                }
                emit(teams)
            }
        }
    }


    suspend fun insertTeam(team: Team) {
        teamDao.insertTeam(teamMapper.mapToEntity(team))
    }

    fun getTeamById(id: Long): Flow<Team> {
        return teamDao.getTeamById(id).flatMapLatest { teamEntity ->
            flow {
                val countryEntity = countryDao.getCountryById(teamEntity.country_id).firstOrNull()
                emit(teamMapper.mapToDomain(teamEntity, countryEntity!!))
            }
        }
    }


    suspend fun updateTeam(team: Team){
        teamDao.updateTeam(teamMapper.mapToEntity(team))
    }
}
