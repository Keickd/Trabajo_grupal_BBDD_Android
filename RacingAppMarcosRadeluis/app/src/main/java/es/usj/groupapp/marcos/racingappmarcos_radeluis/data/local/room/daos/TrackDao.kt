package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertTrack(track: Track)

    @Query("Select * FROM tracks")
    fun getAllTracks(): Flow<List<Track>>

    @Query("Select * FROM tracks where id = :id")
    suspend fun getTeamById(id: Long): Track

    @Update(onConflict = IGNORE)
    suspend fun updateTrack(track: Track)
}