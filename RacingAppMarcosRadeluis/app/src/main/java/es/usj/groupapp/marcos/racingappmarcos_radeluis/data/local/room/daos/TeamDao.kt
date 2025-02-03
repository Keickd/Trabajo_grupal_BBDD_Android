package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertTeam(team: Team)

    @Query("Select * FROM teams")
    fun getAllTeams(): Flow<List<Team>>

    @Query("Select * FROM racers where id = :id")
    suspend fun getTeamById(id: Long): Team

    @Update(onConflict = IGNORE)
    suspend fun updateTeam(team: Team)
}