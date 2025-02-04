package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.RaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRace(raceEntity: RaceEntity)

    @Query("Select * From races")
    fun getAllRaces() : Flow<List<RaceEntity>>

    @Query("Select * From races Where id = :id")
    fun getRaceById(id: Long) : RaceEntity

    @Update
    fun updateRace(raceEntity: RaceEntity)
}