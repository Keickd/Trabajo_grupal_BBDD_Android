package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.ParticipationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipation(participationEntity: ParticipationEntity)

    @Query("Select * FROM participations")
    fun getAllParticipations(): Flow<List<ParticipationEntity>>

    @Query("Select * FROM participations where id = :id")
    suspend fun getParticipationById(id: Long): ParticipationEntity

    @Update(onConflict = IGNORE)
    suspend fun updateParticipation(participationEntity: ParticipationEntity)
}