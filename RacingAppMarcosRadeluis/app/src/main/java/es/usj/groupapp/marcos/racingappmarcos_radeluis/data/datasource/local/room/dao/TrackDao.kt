package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertTrack(trackEntity: TrackEntity)

    @Query("Select * FROM tracks")
    fun getAllTracks(): Flow<List<TrackEntity>>

    @Query("Select * FROM tracks where id = :id")
    fun getTrackById(id: Long): Flow<TrackEntity>

    @Update(onConflict = IGNORE)
    suspend fun updateTrack(trackEntity: TrackEntity)

    @Query("DELETE FROM tracks WHERE id = :id")
    suspend fun deleteTrack(id: Long)
}