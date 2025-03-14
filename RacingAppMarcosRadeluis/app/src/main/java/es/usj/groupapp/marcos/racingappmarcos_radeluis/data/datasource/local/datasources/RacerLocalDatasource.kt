package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.RacerMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.RacerDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.TeamDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
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
                    val countryEntity = countryDao.getCountryById(racerEntity.country_id).firstOrNull()
                    val teamEntity = teamDao.getTeamById(racerEntity.team_id).firstOrNull()
                    racerMapper.mapToDomain(racerEntity, countryEntity!!, teamEntity!!)
                }
                emit(racers)
            }
        }
    }

    suspend fun insertRacer(racer: Racer) {
        racerDao.insertRacer(racerMapper.mapToEntity(racer))
    }

    suspend fun getRacerById(id: Long): Flow<Racer> {
        return racerDao.getRacerById(id).flatMapLatest { racerEntity ->
            flow {
                val countryEntity = countryDao.getCountryById(racerEntity.country_id).firstOrNull()
                val teamEntity = teamDao.getTeamById(racerEntity.team_id).firstOrNull()

                if (countryEntity == null || teamEntity == null) {
                    throw Exception("Country or Team not found")
                }

                emit(racerMapper.mapToDomain(racerEntity, countryEntity, teamEntity))
            }
        }
    }

    suspend fun updateRacer(racer: Racer){
        racerDao.updateRacer(racerMapper.mapToEntity(racer))
    }

    suspend fun deleteRacer(id: Long) {
        racerDao.deleteRacer(id);
    }
}
