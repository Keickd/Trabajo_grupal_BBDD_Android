package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.RacerMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.RacerDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.TeamDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class RacerLocalDatasource(
    private val racerDao: RacerDao,
    private val teamDao: TeamDao,
    private val countryDao: CountryDao,
    private val racerMapper: RacerMapper
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllRacersFlow(): Flow<List<Racer>> {
        return racerDao.getAllRacers().flatMapLatest { racerEntities ->
            flow {
                val racers = racerEntities.map { racerEntity ->
                    val countryEntity = countryDao.getCountryById(racerEntity.country_id)
                    val teamEntity = teamDao.getTeamById(racerEntity.team_id)
                    racerMapper.mapToDomain(racerEntity, countryEntity, teamEntity)
                }
                emit(racers)
            }
        }
    }
}
