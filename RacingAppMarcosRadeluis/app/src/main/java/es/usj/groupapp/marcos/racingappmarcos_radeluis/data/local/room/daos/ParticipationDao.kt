package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParticipation(participation: Participation)

    @Query("Select * FROM participations")
    fun getAllParticipations(): Flow<List<Participation>>

    @Query("Select * FROM participations where id = :id")
    suspend fun getParticipationById(id: Long): Participation

    @Update(onConflict = IGNORE)
    suspend fun updateParticipation(participation: Participation)
}