package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    @Query("Select * FROM countries")
    fun getAllCountries(): Flow<List<Country>>

    @Query("Select * FROM countries where id = :id")
    suspend fun getCountryById(id: Long): Country

    @Update
    suspend fun updateCountry(country: Country)
}