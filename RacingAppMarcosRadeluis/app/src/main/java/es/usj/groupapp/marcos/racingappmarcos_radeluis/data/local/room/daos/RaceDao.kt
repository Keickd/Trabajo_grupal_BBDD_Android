package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRace(race: Race)

    @Query("Select * From races")
    fun getAllRaces() : Flow<List<Race>>

    @Query("Select * From races Where id = :id")
    fun getRaceById(id: Long) : Race

    @Update
    fun updateRace(race: Race)
}