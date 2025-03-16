package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.RaceEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.relations.RaceFullData
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRace(raceEntity: RaceEntity): Long

    @Query("Select * From races")
    fun getAllRaces() : Flow<List<RaceEntity>>

    @Transaction
    @Query("Select * From races Where id = :id")
    suspend fun getRaceById(id: Long) : RaceFullData?

    @Update
    fun updateRace(raceEntity: RaceEntity)

    @Transaction
    @Query("Select * From races")
    fun getRacesWithTrack() : Flow<List<RaceFullData>>
}