package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.RacerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RacerDao {

    @Insert(onConflict = IGNORE)
    suspend fun insertRacer(racerEntity: RacerEntity)

    @Query("Select * FROM racers")
    fun getAllRacers(): Flow<List<RacerEntity>>

    @Query("Select * FROM racers where id = :id")
    fun getRacerById(id: Long): Flow<RacerEntity>

    @Update(onConflict = IGNORE)
    suspend fun updateRacer(racerEntity: RacerEntity)

    @Query("DELETE FROM racers WHERE id = :id")
    suspend fun deleteRacer(id: Long)
}