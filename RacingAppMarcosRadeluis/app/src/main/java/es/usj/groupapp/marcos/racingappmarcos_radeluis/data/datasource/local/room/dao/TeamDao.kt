package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertTeam(teamEntity: TeamEntity)

    @Query("Select * FROM teams")
    fun getAllTeams(): Flow<List<TeamEntity>>

    @Query("Select * FROM teams where id = :id")
    fun getTeamById(id: Long): Flow<TeamEntity>

    @Update(onConflict = IGNORE)
    suspend fun updateTeam(teamEntity: TeamEntity)
}