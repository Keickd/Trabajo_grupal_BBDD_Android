package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import kotlinx.coroutines.flow.Flow

@Dao
interface RacerDao {

    @Insert(onConflict = IGNORE)
    suspend fun insertRacer(racer: Racer)

    @Query("Select * FROM racers")
    fun getAllRacers(): Flow<List<Racer>>

    @Query("Select * FROM racers where id = :id")
    suspend fun getRacerById(id: Long): Racer

    @Update(onConflict = IGNORE)
    suspend fun updateRacer(racer: Racer)

}